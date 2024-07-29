package it.unical.IoTOnChain.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unical.IoTOnChain.data.dto.ProductionStepTOChainDTO;
import it.unical.IoTOnChain.data.mapper.GenericMapper;
import it.unical.IoTOnChain.data.model.ChainTransaction;
import it.unical.IoTOnChain.data.model.Document;
import it.unical.IoTOnChain.data.model.Notarize;
import it.unical.IoTOnChain.data.model.ProductionStep;
import it.unical.IoTOnChain.repository.DocumentRepository;
import it.unical.IoTOnChain.repository.NotarizeRepository;
import it.unical.IoTOnChain.repository.ProductionStepRepository;
import it.unical.IoTOnChain.service.ChainService;
import it.unical.IoTOnChain.service.NotarizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotarizeServiceImpl implements NotarizeService {
  private final NotarizeRepository notarizeRepository;
  private final ChainService chainService;
  private final DocumentRepository documentRepository;
  private final ProductionStepRepository productionStepRepository;
  private final ObjectMapper objectMapper;
  private final GenericMapper genericMapper;
  
  private final String ALGORITHM = "SHA3-256";
  


  private static String bytesToHex(byte[] hash) {
    StringBuilder hexString = new StringBuilder(2 * hash.length);
    for (int i = 0; i < hash.length; i++) {
      String hex = Integer.toHexString(0xff & hash[i]);
      if(hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }
  
  @Override
  @Async
  @Transactional
  public void notarize(Document doc) throws NoSuchAlgorithmException, IOException, TransactionException, ExecutionException, InterruptedException {
    File file = new File(doc.getPath());
    InputStream inputStream = new FileInputStream(file);
    MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
    byte[] encodedhash = digest.digest(inputStream.readAllBytes());
    String sha3Hex = bytesToHex(encodedhash);
    log.debug("Notarizzazione di {} - hash: {}", doc.getPath(), sha3Hex);
    chainService.signString(encodedhash, (String txHash, String error) -> {
      if (error != null) {
        log.debug("[notarize(Document doc)-int] Hash tx {} {} {}", txHash, doc.getPath(), error);
        return "Ciao";
      }
      Notarize nx = Notarize.builder().notarizedAt(LocalDateTime.now()).document(doc).hash(sha3Hex).txTransactionList(Collections.singletonList(ChainTransaction.builder().txId(txHash).build())).build();
      notarizeRepository.save(nx);
      doc.setNotarize(nx);
      documentRepository.save(doc);
      log.debug("[notarize(Document doc)-ext] Hash tx {} - {}", txHash, doc.getPath());
      return "Ciao";
    });
  }
  
  @Override
  @Async
  public void notarize(String doc) throws Exception {
    MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
    byte[] encodedHash = digest.digest(doc.getBytes(StandardCharsets.UTF_8));
    String sha3Hex = bytesToHex(encodedHash);
    log.debug("Notarizzazione di {} - hash: {}", doc, sha3Hex);
    chainService.signString(encodedHash, (String txHash, String error) -> {
      if (error != null) {
        log.debug("[notarize(String doc)-int] Hash tx {} {} {}", txHash, doc, error);
        return "Ciao";
      }
      Notarize nx = Notarize.builder().notarizedAt(LocalDateTime.now()).data(doc).hash(sha3Hex).txTransactionList(Collections.singletonList(ChainTransaction.builder().txId(txHash).build())).build();
      notarizeRepository.save(nx);
      log.debug("[notarize(String doc)-ext] Hash tx {} - {}", txHash, doc);
      return "ciao";
    });
  }
  
  
  private String toBase64(String s) {
    return Base64.getEncoder().encodeToString(s.getBytes());
  }
  
  private String fromBase64(String s) {
    return new String(Base64.getDecoder().decode(s));
  }
  
  
  @Override
  @Async
  public void notarize(ProductionStep ps) throws NoSuchAlgorithmException, IOException, TransactionException {
    ps.setDate(LocalDateTime.now());
    String doc = objectMapper.writeValueAsString(genericMapper.map(ps));
    MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
    byte[] encodedHash = digest.digest(doc.getBytes(StandardCharsets.UTF_8));
    String sha3Hex = bytesToHex(encodedHash);
    log.debug("\nNotarizzazione di {}\nbyteArr {}\nhash {}", doc, encodedHash, sha3Hex);
    chainService.signString(encodedHash, (String txHash, String error) -> {
      if (error != null) {
        log.debug("[notarize(ProductionStep ps)-int] Hash tx {} {} {}", txHash, doc, error);
        return "Ciao";
      }
      Notarize nx = Notarize.builder().notarizedAt(LocalDateTime.now()).data(doc).hash(sha3Hex).txTransactionList(Collections.singletonList(ChainTransaction.builder().txId(txHash).build())).build();
      notarizeRepository.save(nx);
      ps.setNotarize(nx);
      productionStepRepository.save(ps);
      log.debug("[notarize(ProductionStep ps)-ext]  Hash tx {} - {}", txHash, doc);
      return "ciao";
    });
  }
}
