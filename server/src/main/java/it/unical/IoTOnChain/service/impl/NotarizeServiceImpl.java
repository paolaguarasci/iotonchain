package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.ChainTransaction;
import it.unical.IoTOnChain.data.model.Document;
import it.unical.IoTOnChain.data.model.Notarize;
import it.unical.IoTOnChain.repository.DocumentRepository;
import it.unical.IoTOnChain.repository.NotarizeRepository;
import it.unical.IoTOnChain.service.ChainService;
import it.unical.IoTOnChain.service.NotarizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotarizeServiceImpl implements NotarizeService {
  private final NotarizeRepository notarizeRepository;
  private final ChainService chainService;
  private final DocumentRepository documentRepository;
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
  public void notarize(Document doc) throws NoSuchAlgorithmException, IOException, TransactionException, ExecutionException, InterruptedException {
    File file = new File(doc.getPath());
    InputStream inputStream = new FileInputStream(file);
    MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
    byte[] encodedhash = digest.digest(inputStream.readAllBytes());
    String sha3Hex = bytesToHex(encodedhash);
    log.debug("Notarizzazione di {} - hash: {}", doc.getPath(), sha3Hex);
    chainService.signString(encodedhash, (String txHash, String error) -> {
      if (error != null) {
        log.debug("Hash tx {} {} {}", txHash, doc.getPath(), error);
        return "Ciao";
      }
      Notarize nx = Notarize.builder().notarizedAt(LocalDateTime.now()).document(doc).hash(sha3Hex).txTransactionList(Collections.singletonList(ChainTransaction.builder().txId(txHash).build())).build();
      notarizeRepository.save(nx);
      doc.setNotarize(nx);
      documentRepository.save(doc);
      log.debug("Hash tx {} - {}", txHash, doc.getPath());
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
        log.debug("Hash tx {} {} {}", txHash, doc, error);
        return "Ciao";
      }
      Notarize nx = Notarize.builder().notarizedAt(LocalDateTime.now()).data(doc).hash(sha3Hex).txTransactionList(Collections.singletonList(ChainTransaction.builder().txId(txHash).build())).build();
      notarizeRepository.save(nx);
      log.debug("Hash tx {} - {}", txHash, doc);
      return "ciao";
    });
  }
}
