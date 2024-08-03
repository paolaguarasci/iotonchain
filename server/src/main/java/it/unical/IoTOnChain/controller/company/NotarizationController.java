package it.unical.IoTOnChain.controller.company;

import it.unical.IoTOnChain.data.dto.NotarizeToOwnerDTO;
import it.unical.IoTOnChain.data.mapper.GenericMapper;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.ProductionStepBatch;
import it.unical.IoTOnChain.service.CompanyService;
import it.unical.IoTOnChain.service.NotarizeService;
import it.unical.IoTOnChain.service.ProductionProcessBatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/company/notarization")
public class NotarizationController {
  
  private final CompanyService companyService;
  private final NotarizeService notarizeService;
  private final GenericMapper genericMapper;
  private final ProductionProcessBatchService productionProcessBatchService;
  
  @GetMapping
  public ResponseEntity<List<NotarizeToOwnerDTO>> getAllNotarizationByCompanyLogged(@AuthenticationPrincipal Jwt principal) {
    
    String companyLogged = principal.getClaimAsString("company");
    Company company = companyService.getOneByName(companyLogged);
    if (company == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().body(genericMapper.mapNotarizeListToOwner(notarizeService.getAll(company)));
  }
  
  @PostMapping("/step/{step_id}")
  private ResponseEntity<?> notarizeOneStep(@PathVariable String step_id, @AuthenticationPrincipal Jwt principal, @RequestBody Map<String, String> body) throws TransactionException, NoSuchAlgorithmException, IOException {
    String companyLogged = principal.getClaimAsString("company");
    Company company = companyService.getOneByName(companyLogged);
    
    if (company == null) {
      return ResponseEntity.notFound().build();
    }
    ProductionStepBatch ps = productionProcessBatchService.getOneById(step_id);
    if (ps == null) {
      return ResponseEntity.notFound().build();
    }
    notarizeService.notarize(company, ps);
    return ResponseEntity.ok().build();
  }
}
