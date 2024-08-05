package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.chain.Hash;
import it.unical.IoTOnChain.repository.NotarizeRepository;
import it.unical.IoTOnChain.service.ChainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Transactional
@Service
@Slf4j
public class ChainServiceImpl implements ChainService {
  private final ApplicationContext applicationContext;
  private final TransactionManager transactionManager;
  private Web3j web3j;
  private Map<String, Object> registrationContract = new HashMap<>();
  
  
  public ChainServiceImpl(NotarizeRepository notarizeRepository, Web3j web3j, ApplicationContext applicationContext) {
    this.web3j = web3j;
    this.applicationContext = applicationContext;
    this.web3j = applicationContext.getBean(Web3j.class);
    this.registrationContract = (Map<String, Object>) applicationContext.getBean("registrationContracts");
    this.transactionManager = (TransactionManager) applicationContext.getBean("getTransactionManager");
  }
  
  
  @Async
  public void signString(byte[] str, java.util.function.BiFunction<String, String, String> func) throws IOException, TransactionException {
    Hash contract = (Hash) this.registrationContract.get("hash");
    TransactionReceipt txReceipt = null;
    Function function = new Function("signHash",
      Arrays.asList(new Bytes32(str), new Utf8String(Arrays.toString(str))),
      Collections.emptyList());
    String txData = FunctionEncoder.encode(function);
    EthSendTransaction txHash = this.transactionManager.sendTransaction(DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT, contract.getContractAddress(), txData, BigInteger.ZERO);
    TransactionReceiptProcessor receiptProcessor = new PollingTransactionReceiptProcessor(this.web3j, TransactionManager.DEFAULT_POLLING_FREQUENCY, TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);
    String error = null;
    String msg = null;
    try {
      txReceipt = receiptProcessor.waitForTransactionReceipt(txHash.getTransactionHash());
      msg = txReceipt.getTransactionHash();
    } catch (TransactionException e) {
      error = e.getMessage();
    }
    func.apply(msg, error);
  }
  
  
  @Override
  @Async
  public void testAsync() {
//    System.out.println("Execute method asynchronously - "
//      + Thread.currentThread().getName());
//    try {
//      Thread.sleep(15000);
//
//    }
  }
  // <i class="fa-solid fa-tractor"></i>
  // <i class="fa-solid fa-wheat-awn"></i>
  //
  
}
