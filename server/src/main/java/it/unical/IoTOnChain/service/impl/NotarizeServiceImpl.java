package it.unical.IoTOnChain.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unical.IoTOnChain.data.mapper.GenericMapper;
import it.unical.IoTOnChain.data.model.*;
import it.unical.IoTOnChain.repository.DocumentRepository;
import it.unical.IoTOnChain.repository.NotarizeRepository;
import it.unical.IoTOnChain.repository.ProductionStepBatchRepository;
import it.unical.IoTOnChain.repository.TransferRepository;
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
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotarizeServiceImpl implements NotarizeService {
  private final NotarizeRepository notarizeRepository;
  private final ChainService chainService;
  private final DocumentRepository documentRepository;
  private final ProductionStepBatchRepository productionStepBatchRepository;
  private final ObjectMapper objectMapper;
  private final GenericMapper genericMapper;
  private final TransferRepository transferRepository;
  
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
  public void notarize(Company company, Document doc) throws NoSuchAlgorithmException, IOException, TransactionException, ExecutionException, InterruptedException {
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
      Notarize nx = Notarize.builder().company(company).notarizedAt(LocalDateTime.now()).document(doc).hash(sha3Hex).txTransactionList(Collections.singletonList(ChainTransaction.builder().txId(txHash).build())).build();
      notarizeRepository.save(nx);
      doc.setNotarize(nx);
      documentRepository.save(doc);
      log.debug("[notarize(Document doc)-ext] Hash tx {} - {}", txHash, doc.getPath());
      return "Ciao";
    });
  }
  
  @Override
  @Async
  public void notarize(Company company, String doc) throws Exception {
    MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
    byte[] encodedHash = digest.digest(doc.getBytes(StandardCharsets.UTF_8));
    String sha3Hex = bytesToHex(encodedHash);
    log.debug("Notarizzazione di {} - hash: {}", doc, sha3Hex);
    chainService.signString(encodedHash, (String txHash, String error) -> {
      if (error != null) {
        log.debug("[notarize(String doc)-int] Hash tx {} {} {}", txHash, doc, error);
        return "Ciao";
      }
      Notarize nx = Notarize.builder().company(company).notarizedAt(LocalDateTime.now()).data(doc).hash(sha3Hex).txTransactionList(Collections.singletonList(ChainTransaction.builder().txId(txHash).build())).build();
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
  public void notarize(Company company, ProductionStepBatch ps) throws NoSuchAlgorithmException, IOException, TransactionException {
    // ProductionStepBatch pp = batch.getLocalProcessProduction().getSteps().stream().filter(productionStep -> productionStep.getId().equals(ps.getId())).collect(Collectors.toList()).getFirst();
    // pp.setDate(LocalDateTime.now());
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
      Notarize nx = Notarize.builder().company(company).notarizedAt(LocalDateTime.now()).data(doc).hash(sha3Hex).txTransactionList(Collections.singletonList(ChainTransaction.builder().txId(txHash).build())).build();
      notarizeRepository.save(nx);
      ps.setNotarize(nx);
      productionStepBatchRepository.save(ps);
      log.debug("[notarize(ProductionStep ps)-ext]  Hash tx {} - {}", txHash, doc);
      return "ciao";
    });
  }
  
  @Override
  public List<Notarize> getAll(Company company) {
    return notarizeRepository.findAllByCompanyOrderByNotarizedAtDesc(company);
  }
  
  @Override
  public void notarize(Company company, Transfer transfer) throws NoSuchAlgorithmException, IOException, TransactionException {
    String doc = objectMapper.writeValueAsString(genericMapper.map(transfer));
    MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
    byte[] encodedHash = digest.digest(doc.getBytes(StandardCharsets.UTF_8));
    String sha3Hex = bytesToHex(encodedHash);
    log.debug("\nNotarizzazione di {}\nbyteArr {}\nhash {}", doc, encodedHash, sha3Hex);
    chainService.signString(encodedHash, (String txHash, String error) -> {
      if (error != null) {
        log.debug("[notarize(Transfer transfer)-int] Hash tx {} {} {}", txHash, doc, error);
        return "Ciao";
      }
      if (transfer.getTxTransactionList() == null) {
        transfer.setTxTransactionList(List.of(ChainTransaction.builder().txId(txHash).build()));
      } else {
        transfer.getTxTransactionList().add(ChainTransaction.builder().txId(txHash).build());
      }
      transferRepository.save(transfer);
      log.debug("[notarize(Transfer transfer)-ext]  Hash tx {} - {}", txHash, doc);
      return "ciao";
    });
  }
}
