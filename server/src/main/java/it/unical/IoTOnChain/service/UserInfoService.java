package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.user.UserInfo;

public interface UserInfoService {
  UserInfo newUser(Company company, String username);
  
}
