package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.chain.Hash;
import it.unical.IoTOnChain.data.model.ChainTransaction;
import it.unical.IoTOnChain.data.model.Document;
import it.unical.IoTOnChain.data.model.Notarize;
import it.unical.IoTOnChain.repository.NotarizeRepository;
import it.unical.IoTOnChain.service.ChainService;
import it.unical.IoTOnChain.service.NotarizeService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotarizeServiceImpl implements NotarizeService {
  private final NotarizeRepository notarizeRepository;
  private final ChainService chainService;
  private String ALGORITHM = "SHA3-256";
  


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
  public Notarize notarize(Document doc) throws NoSuchAlgorithmException, IOException, TransactionException, ExecutionException, InterruptedException {
    File file = new File(doc.getPath());
    InputStream inputStream = new FileInputStream(file);
    MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
    byte[] encodedhash = digest.digest(inputStream.readAllBytes());
    String sha3Hex = bytesToHex(encodedhash);
    log.debug("Notarizzazione di {} - hash: {}", doc.getPath(), sha3Hex);
    Future<String> txHash = chainService.signString(encodedhash);
    ChainTransaction chainTransaction = ChainTransaction.builder().txId(txHash.get()).build();
//    ChainTransaction chainTransaction = ChainTransaction.builder().txId("ciao").build();
    Notarize notarize = Notarize.builder().notarizedAt(LocalDateTime.now()).document(doc).txTransactionList(Collections.singletonList(chainTransaction)).build();
    return notarizeRepository.save(notarize);
  }
  
  @Override
  public Notarize notarize(String doc) throws Exception {
    
    MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
    byte[] encodedHash = digest.digest(doc.getBytes(StandardCharsets.UTF_8));
    String sha3Hex = bytesToHex(encodedHash);
    log.debug("Notarizzazione di {} - hash: {}", doc, sha3Hex);
    Future<String> txHash = chainService.signString(encodedHash);
    ChainTransaction chainTransaction = ChainTransaction.builder().txId(txHash.get()).build();
    Notarize notarize = Notarize.builder().notarizedAt(LocalDateTime.now()).data(sha3Hex).txTransactionList(Collections.singletonList(chainTransaction)).build();
    return notarizeRepository.save(notarize);
  }
  

  

}
