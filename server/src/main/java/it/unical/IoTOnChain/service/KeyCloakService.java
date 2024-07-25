package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.user.UserInfo;

import java.util.List;

public interface KeyCloakService {
  void addCompanyNameToOptionsBatch(List<Company> companies);
  
  void addCompanyNameToOptions(Company company);
  
  String createUser(UserInfo user);
}
