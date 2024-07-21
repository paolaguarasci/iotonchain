package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.repository.CompanyRepository;
import it.unical.IoTOnChain.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {
  private final CompanyRepository companyRepository;
  @Override
  public Company makeOne(String name) {
    return companyRepository.save(Company.builder().name(name).build());
  }
  
  @Override
  public boolean companyExist(Company owner) {
    return companyRepository.findById(owner.getId()).isPresent();
  }
  
  @Override
  public Company getOneByName(String companyLogged) {
    return companyRepository.findByName(companyLogged).orElse(null);
  }
  
  @Override
  public List<Company> getAllCompanyClient(Company company) {
    return companyRepository.findAll().stream().filter(company1 -> !company1.getId().equals(company.getId())).toList();
  }
  
  @Override
  public Company getOneById(String companyRecipientID) {
    log.debug("Find company by id {}", companyRecipientID);
    return companyRepository.findById(UUID.fromString(companyRecipientID)).orElseThrow();
  }
}
