package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.*;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface NotarizeService {
  void notarize(Document doc) throws NoSuchAlgorithmException, IOException, TransactionException, ExecutionException, InterruptedException;
  
  void notarize(String doc) throws Exception;
  
  void notarize(Batch batch, ProductionStepBatch ps) throws NoSuchAlgorithmException, IOException, TransactionException;
  
  List<Notarize> getAll(Company company);
}
