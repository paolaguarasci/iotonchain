package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.user.UserInfo;
import it.unical.IoTOnChain.service.KeyCloakService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.userprofile.config.UPConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@Transactional
public class KeyCloakServiceImpl implements KeyCloakService {
  private final Keycloak keycloak;
  
  KeyCloakServiceImpl() {
    this.keycloak = KeycloakBuilder.builder()
      .serverUrl("http://localhost:8881")
      .realm("master")
      .grantType(OAuth2Constants.PASSWORD)
      .clientId("admin-cli")
      .username("admin")
      .password("admin")
      .build();
  }
  
  @Override
  public void addCompanyNameToOptionsBatch(List<Company> companies) {
    Set newCompanyList = new HashSet();
    companies.forEach((company -> {
      newCompanyList.add(company.getName());
    }));
    UPConfig upConfig = this.keycloak.realm("iotchian").users().userProfile().getConfiguration();
    Map<String, Map<String, Object>> newValidation = new HashMap<>();
    Map<String, Object> newValidationOptions = new HashMap<>();
    newValidationOptions.put("options", newCompanyList);
    newValidation.put("options", newValidationOptions);
    upConfig.getAttribute("company").setValidations(newValidation);
    this.keycloak.realm("iotchain").users().userProfile().update(upConfig);
  }
  
  @Override
  public void addCompanyNameToOptions(Company company) {
    UPConfig upConfig = keycloak.realm("iotchain").users().userProfile().getConfiguration();
    Map<String, Map<String, Object>> oldValidation = upConfig.getAttribute("company").getValidations();
    Set newCompanyList = new HashSet();
    newCompanyList.addAll((List) oldValidation.get("options").get("options"));
    if (!newCompanyList.contains(company.getName())) {
      newCompanyList.add(company.getName());
      Map<String, Map<String, Object>> newValidation = new HashMap<>();
      Map<String, Object> newValidationOptions = new HashMap<>();
      newValidationOptions.put("options", newCompanyList);
      newValidation.put("options", newValidationOptions);
      upConfig.getAttribute("company").setValidations(newValidation);
      keycloak.realm("iotchain").users().userProfile().update(upConfig);
    }
  }
  
  @Override
  public String createUser(UserInfo user) {
    String normalizedCompanyName = user.getCompany().getName();
    String username = user.getUsername();
    UserRepresentation newUser = new UserRepresentation();
    CredentialRepresentation newPass = new CredentialRepresentation();
    
    Map<String, List<String>> attributes = new HashMap<>();
    attributes.put("company", Collections.singletonList(normalizedCompanyName));
    newUser.setAttributes(attributes);
    
    newPass.setType(CredentialRepresentation.PASSWORD);
    // newPass.setValue("changeme");
    // newPass.setTemporary(true);
    newPass.setValue(username);
    newUser.setUsername(username);
    newPass.setTemporary(false);
    newUser.setEnabled(true);
    newUser.setEmailVerified(true);
    newUser.setCredentials(List.of(newPass));
    this.keycloak.realm("iotchain").users().create(newUser);
    return normalizedCompanyName;
  }
}
