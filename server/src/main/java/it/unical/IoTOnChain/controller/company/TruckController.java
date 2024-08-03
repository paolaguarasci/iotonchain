package it.unical.IoTOnChain.controller.company;

import it.unical.IoTOnChain.data.dto.TruckToOwnerDTO;
import it.unical.IoTOnChain.data.mapper.GenericMapper;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.service.CompanyService;
import it.unical.IoTOnChain.service.TruckService;
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
@RequestMapping("/api/v1/company/transfer/truck")
public class TruckController {
  private final TruckService truckService;
  private final CompanyService companyService;
  private final GenericMapper genericMapper;
  
  @GetMapping
  public ResponseEntity<List<TruckToOwnerDTO>> getAllTrucks(@AuthenticationPrincipal Jwt principal) {
    String companyLogged = principal.getClaimAsString("company");
    Company company = companyService.getOneByName(companyLogged);
    if (company == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().body(genericMapper.mapTruckListToDTOOwner(truckService.getAllByCompany(company)));
  }
}
