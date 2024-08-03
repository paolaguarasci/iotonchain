package it.unical.IoTOnChain.controller.company;

import it.unical.IoTOnChain.data.dto.NotarizeToOwnerDTO;
import it.unical.IoTOnChain.data.mapper.GenericMapper;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.service.CompanyService;
import it.unical.IoTOnChain.service.NotarizeService;
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
@RequestMapping("/api/v1/company/notarization")
public class NotarizationController {
  
  private final CompanyService companyService;
  private final NotarizeService notarizeService;
  private final GenericMapper genericMapper;
  
  @GetMapping
  public ResponseEntity<List<NotarizeToOwnerDTO>> getAllNotarizationByCompanyLogged(@AuthenticationPrincipal Jwt principal) {
    
    String companyLogged = principal.getClaimAsString("company");
    Company company = companyService.getOneByName(companyLogged);
    if (company == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().body(genericMapper.mapNotarizeListToOwner(notarizeService.getAll(company)));
  }
}
