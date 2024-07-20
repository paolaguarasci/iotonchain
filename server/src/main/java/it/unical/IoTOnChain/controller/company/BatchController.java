package it.unical.IoTOnChain.controller.company;

import it.unical.IoTOnChain.data.dto.BatchToOwner;
import it.unical.IoTOnChain.data.mapper.GenericMapper;
import it.unical.IoTOnChain.service.BatchService;
import it.unical.IoTOnChain.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/company/batch")
public class BatchController {
  private final BatchService batchService;
  // private final CompanyService companyService;
  private final GenericMapper mapper;
  @GetMapping
  public ResponseEntity<List<BatchToOwner>> getAllProductsByCompanyLogged(@AuthenticationPrincipal Jwt principal) {
    log.debug("Get all product for company logged");
    if (principal != null) {
      String companyLogged = principal.getClaimAsString("given_name");
      return ResponseEntity.ok(mapper.mapForProductOwner(batchService.getAllProductByCompanyLogged(companyLogged)));
    } else {
      String companyLogged = "barilla";
      return ResponseEntity.ok(mapper.mapForProductOwner(batchService.getAllProductByCompanyLogged(companyLogged)));
      // return ResponseEntity.ok(List.of());
    }
  }
}
