package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Document;
import it.unical.IoTOnChain.repository.DocumentRepository;
import it.unical.IoTOnChain.service.DocumentService;
import it.unical.IoTOnChain.service.NotarizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {
  private final DocumentRepository documentRepository;
  
  private final NotarizeService notarizeService;
  
  private final Path rootLocation = Paths.get("/tmp");
  
  @Override
  public List<Document> getAllByCompanyLogged(Company company) {
    return documentRepository.findAllByOwner(company);
  }
  
  @Override
  public Document createOne(Company company, String name, String description, LocalDateTime localDateTime1, LocalDateTime localDateTime2, Path resolve) throws TransactionException, NoSuchAlgorithmException, IOException, ExecutionException, InterruptedException {
    Document doc = documentRepository.save(Document.builder().title(name).description(description).owner(company).path(String.valueOf(resolve)).build());
    notarizeService.notarize(company, doc);
    return doc;
  }
  
  @Override
  public Document check(String hash) {
    return null;
  }
  
  
  @Override
  public void notarize(Company company, String docId) throws TransactionException, NoSuchAlgorithmException, IOException, ExecutionException, InterruptedException {
    Optional<Document> documentOptional = documentRepository.findById(UUID.fromString(docId));
    if (documentOptional.isPresent() && documentOptional.get().getNotarize() == null) {
      notarizeService.notarize(company, documentOptional.get());
    }
  }
  
  
  @Override
  public Path load(String filename) {
    return rootLocation.resolve(filename);
  }
  
  @Override
  public Resource loadAsResource(String filename) throws FileNotFoundException {
    try {
      Path file = load(filename);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new FileNotFoundException(
          "Could not read file: " + filename);
      }
    } catch (MalformedURLException e) {
      throw new FileNotFoundException();
    }
  }
  
  @Override
  public Document getOneByCompanyLogged(Company company, String docId) {
    Optional<Document> documentOptional = documentRepository.findById(UUID.fromString(docId));
    if (documentOptional.isPresent() && documentOptional.get().getOwner().equals(company)) {
      return documentOptional.get();
    }
    return null;
  }
  
  @Override
  public List<Document> getByIdList(List<String> documents) {
    return documentRepository.findAllById(documents.stream().map(doc -> UUID.fromString(doc)).toList());
  }
  
}
