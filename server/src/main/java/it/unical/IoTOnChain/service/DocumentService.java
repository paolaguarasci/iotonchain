package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Document;
import org.springframework.core.io.Resource;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface DocumentService {
  
  List<Document> getAllByCompanyLogged(Company company);
  
  Document createOne(Company company, String name, String description, LocalDateTime localDateTime1, LocalDateTime localDateTime2, Path resolve) throws TransactionException, NoSuchAlgorithmException, IOException, ExecutionException, InterruptedException;
  
  
  Document check(String hash);
  
  void notarize(Company company, String docId) throws TransactionException, NoSuchAlgorithmException, IOException, ExecutionException, InterruptedException;
  
  Path load(String filename);
  
  Resource loadAsResource(String filename) throws FileNotFoundException;
  
  Document getOneByCompanyLogged(Company company, String docId);
  
  List<Document> getByIdList(List<String> documents);
}


// TODO gestire Fail with error 'Hash already signed'
