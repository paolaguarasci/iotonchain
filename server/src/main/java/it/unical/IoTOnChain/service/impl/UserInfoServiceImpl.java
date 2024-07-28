package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.user.UserInfo;
import it.unical.IoTOnChain.repository.UserInfoRepository;
import it.unical.IoTOnChain.service.KeyCloakService;
import it.unical.IoTOnChain.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
  private final UserInfoRepository userInfoRepository;
  private final KeyCloakService keyCloakService;
  
  @Override
  public UserInfo newUser(Company company, String username) {
    Optional<UserInfo> user = userInfoRepository.findByUsername(username);
    if(user.isPresent()) {
      return user.get();
    }
    keyCloakService.addCompanyNameToOptions(company);
    UserInfo newUser = userInfoRepository.save(UserInfo.builder().company(company).username(username).build());
    String kid = keyCloakService.createUser(newUser);
    newUser.setKeycloakId(kid);
    return userInfoRepository.save(newUser);
  }
}
