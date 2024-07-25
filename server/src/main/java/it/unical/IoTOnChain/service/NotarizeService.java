package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.Document;
import it.unical.IoTOnChain.data.model.Notarize;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

public interface NotarizeService {
  Notarize notarize(Document doc) throws NoSuchAlgorithmException, IOException, TransactionException, ExecutionException, InterruptedException;
  Notarize notarize(String doc) throws Exception;
}
