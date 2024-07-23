package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Document;
import it.unical.IoTOnChain.repository.DocumentRepository;
import it.unical.IoTOnChain.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {
  private final DocumentRepository documentRepository;

  @Override
  public List<Document> getAllByCompanyLogged(Company company) {
    return documentRepository.findAllByOwner(company);
  }
}
