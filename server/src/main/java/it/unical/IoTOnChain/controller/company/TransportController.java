package it.unical.IoTOnChain.controller.company;

import it.unical.IoTOnChain.data.dto.TransportToOwnerDTO;
import it.unical.IoTOnChain.data.dto.TruckToOwnerDTO;
import it.unical.IoTOnChain.data.mapper.GenericMapper;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.service.CompanyService;
import it.unical.IoTOnChain.service.TransportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/company/transport")
public class TransportController {
  public final CompanyService companyService;
  public final TransportService transportService;
  public final GenericMapper genericMapper;
  
  @GetMapping
  public ResponseEntity<List<TransportToOwnerDTO>> getAllTransports(@AuthenticationPrincipal Jwt principal) {
    log.debug("Get all transport for company logged");
    String companyLogged = principal.getClaimAsString("company");
    Company company = companyService.getOneByName(companyLogged);
    if (company == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().body(genericMapper.map(transportService.getAllByCompany(company)));
  }
  
  @GetMapping("/{batch_id}")
  public ResponseEntity<List<TransportToOwnerDTO>> getAllTransportsByBatchId(@AuthenticationPrincipal Jwt principal, @PathVariable String batch_id) {
    log.debug("Get all transport for company logged and batch id {}", batch_id);
    String companyLogged = principal.getClaimAsString("company");
    Company company = companyService.getOneByName(companyLogged);
    if (company == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().body(genericMapper.map(transportService.getOneByBatchID(batch_id)));
  }
  
  @GetMapping("/{transport_id}/truck")
  public ResponseEntity<TruckToOwnerDTO> getTruckByTransportId(@AuthenticationPrincipal Jwt principal, @PathVariable String transport_id) {
    String companyLogged = principal.getClaimAsString("company");
    Company company = companyService.getOneByName(companyLogged);
    if (company == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().body(genericMapper.map(transportService.getTruckByTransportId(transport_id)));
  }
  
  
}
