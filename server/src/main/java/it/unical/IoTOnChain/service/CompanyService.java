package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.Company;

public interface CompanyService {
  Company makeOne(String name);
  
  boolean companyExist(Company owner);
}
