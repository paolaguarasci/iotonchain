package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Document;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface DocumentService {
  
  List<Document> getAllByCompanyLogged(Company company);
  
  Document createOne(Company company, Path resolve) throws TransactionException, NoSuchAlgorithmException, IOException, ExecutionException, InterruptedException;
  
  
  Document check(String hash);
  
  void notarize(Company company, String docId) throws TransactionException, NoSuchAlgorithmException, IOException, ExecutionException, InterruptedException;
}
