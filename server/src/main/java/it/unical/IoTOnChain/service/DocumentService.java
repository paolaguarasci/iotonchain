package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Document;

import java.util.List;

public interface DocumentService {

  List<Document> getAllByCompanyLogged(Company company);
}
