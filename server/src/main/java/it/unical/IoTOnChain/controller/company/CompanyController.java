package it.unical.IoTOnChain.controller.company;

import it.unical.IoTOnChain.data.dto.CompanyLite;
import it.unical.IoTOnChain.data.mapper.GenericMapper;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/company")
public class CompanyController {
  
  private final CompanyService companyService;
  private final GenericMapper mapper;
  
  @GetMapping("/client")
//  public ResponseEntity<List<CompanyLite>> getAllCompanies(@AuthenticationPrincipal Jwt principal) {
  public ResponseEntity<List<CompanyLite>> getAllCompanies() {
    log.debug("Get all client for company logged");
    String companyLogged = "barillaSPA";
//    if (principal != null) {
//      companyLogged = principal.getClaimAsString("given_name");
//    }
    Company company = companyService.getOneByName(companyLogged);
    return ResponseEntity.ok(mapper.mapCompanyLite(companyService.getAllCompanyClient(company)));
  }
  
}
