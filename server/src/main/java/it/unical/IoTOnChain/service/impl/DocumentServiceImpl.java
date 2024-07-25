package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Document;
import it.unical.IoTOnChain.repository.DocumentRepository;
import it.unical.IoTOnChain.service.DocumentService;
import it.unical.IoTOnChain.service.NotarizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {
  private final DocumentRepository documentRepository;
  private final NotarizeService notarizeService;
  @Override
  public List<Document> getAllByCompanyLogged(Company company) {
    return documentRepository.findAllByOwner(company);
  }
  
  @Override
  public Document createOne(Company company, Path resolve) throws TransactionException, NoSuchAlgorithmException, IOException, ExecutionException, InterruptedException {
    Document doc =  documentRepository.save(Document.builder().owner(company).path(String.valueOf(resolve)).build());
    notarizeService.notarize(doc);
    return doc;
  }
  
  @Override
  public Document check(String hash) {
    return null;
  }
  
  @Override
  public void notarize(Company company, String docId) throws TransactionException, NoSuchAlgorithmException, IOException, ExecutionException, InterruptedException {
    Optional<Document> documentOptional = documentRepository.findById(UUID.fromString(docId));
    if(documentOptional.get().getNotarize() == null) {
      notarizeService.notarize(documentOptional.get());
    }
  }
}
