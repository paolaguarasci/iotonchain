package it.unical.IoTOnChain.config;


import it.unical.IoTOnChain.chain.Box;
import it.unical.IoTOnChain.chain.Hash;
import it.unical.IoTOnChain.chain.Token;
import it.unical.IoTOnChain.chain.TokenV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class Web3jConfig {
  private final String polygonNet;
  private final String privateKey;
  
  private final String contractAddress_hash;
//  private final String contractAddress_box;
//  private final String contractAddress_tokenv1;
//  private final String contractAddress_tokenv2;
  
  private Web3j web3j;
  private TransactionManager transactionManager;
  
  Web3jConfig(
    @Value("${app.chain.polygon}") String polygonNet,
    @Value("${app.chain.pvtkey}") String privateKey,
    @Value("${app.chain.address.contract.hash}") String chash//,
//    @Value("${app.chain.address.contract.box}") String cbox,
//    @Value("${app.chain.address.contract.token.v1}") String ctokenv1,
//    @Value("${app.chain.address.contract.token.v2}") String ctokenv2
  ) {
    this.polygonNet = polygonNet;
    this.privateKey = privateKey;
    this.contractAddress_hash = chash;
//    this.contractAddress_box = cbox;
//    this.contractAddress_tokenv1 = ctokenv1;
//    this.contractAddress_tokenv2 = ctokenv2;
  }
  
  @Bean
  public Web3j web3j() throws IOException {
    this.web3j = Web3j.build(new HttpService(this.polygonNet));
    return this.web3j;
  }
  
  @Bean(name = "getTransactionManager")
  public TransactionManager getTransactionManager() throws IOException {
    Credentials credentials = Credentials.create(this.privateKey);
    String chainId = this.web3j.netVersion().send().getNetVersion();
    // String chainId = "820001";
    return new RawTransactionManager(this.web3j, credentials, Long.parseLong(chainId));
  }
  
  @Bean(name = "registrationContracts")
  public Map<String, Object> registrationContracts() {
    Credentials credentials = Credentials.create(this.privateKey);
    ContractGasProvider contractGasProvider = new DefaultGasProvider();
    
    Map<String, Object> contracts = new HashMap<>();
    
    contracts.put("hash", Hash.load(this.contractAddress_hash, this.web3j, credentials, contractGasProvider));
//    contracts.put("box", Box.load(this.contractAddress_box, this.web3j, credentials, contractGasProvider));
//    contracts.put("tokenv1", Token.load(this.contractAddress_tokenv1, this.web3j, credentials, contractGasProvider));
//    contracts.put("tokenv2", TokenV2.load(this.contractAddress_tokenv2, this.web3j, credentials, contractGasProvider));
    return contracts;
  }
  
}
