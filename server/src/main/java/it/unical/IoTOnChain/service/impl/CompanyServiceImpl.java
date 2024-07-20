package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.repository.CompanyRepository;
import it.unical.IoTOnChain.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
