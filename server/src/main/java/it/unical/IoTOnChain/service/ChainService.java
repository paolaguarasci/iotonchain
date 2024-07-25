package it.unical.IoTOnChain.service;

import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.util.function.Function;

public interface ChainService {
  void signString(byte[] str, Function<String, String> func) throws IOException, TransactionException;
  
  void testAsync();
}
