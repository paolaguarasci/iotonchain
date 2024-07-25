package it.unical.IoTOnChain.service;

import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Future;

public interface ChainService {
  public Future<String> signString(byte[] str) throws IOException, TransactionException;
  public Future<String> testAsync();
}
