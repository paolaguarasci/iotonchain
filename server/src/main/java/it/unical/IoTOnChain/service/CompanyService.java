package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.Company;

import java.util.List;

public interface CompanyService {
  Company makeOne(String name);
  
  boolean companyExist(Company owner);
  
  Company getOneByName(String companyLogged);
  
  List<Company> getAllCompanyClient(Company company);
  
  Company getOneById(String companyRecipientID);
}
