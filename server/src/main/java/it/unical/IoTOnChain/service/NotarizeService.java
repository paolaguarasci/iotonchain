package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Document;
import it.unical.IoTOnChain.data.model.Notarize;
import it.unical.IoTOnChain.data.model.ProductionStepBatch;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface NotarizeService {
  void notarize(Company company, Document doc) throws NoSuchAlgorithmException, IOException, TransactionException, ExecutionException, InterruptedException;
  
  void notarize(Company company, String doc) throws Exception;
  
  void notarize(Company company, ProductionStepBatch ps) throws NoSuchAlgorithmException, IOException, TransactionException;
 
  List<Notarize> getAll(Company company);
}
