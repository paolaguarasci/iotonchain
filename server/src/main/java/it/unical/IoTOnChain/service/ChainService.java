package it.unical.IoTOnChain.service;

import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.util.function.BiFunction;

public interface ChainService {
  void signString(byte[] str, BiFunction<String, String, String> func) throws IOException, TransactionException;
  
  void testAsync();
}
