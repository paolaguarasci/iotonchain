package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.Document;
import it.unical.IoTOnChain.data.model.ProductionStep;
import it.unical.IoTOnChain.data.model.ProductionStepBatch;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

public interface NotarizeService {
  void notarize(Document doc) throws NoSuchAlgorithmException, IOException, TransactionException, ExecutionException, InterruptedException;
  
  void notarize(String doc) throws Exception;
  
  void notarize(Batch batch, ProductionStepBatch ps) throws NoSuchAlgorithmException, IOException, TransactionException;
}
