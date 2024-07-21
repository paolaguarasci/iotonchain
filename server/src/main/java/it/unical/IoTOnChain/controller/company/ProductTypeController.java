package it.unical.IoTOnChain.controller.company;

import it.unical.IoTOnChain.data.dto.CreateProductTypeDTOFromOwner;
import it.unical.IoTOnChain.data.dto.ProductTypeToOwner;
import it.unical.IoTOnChain.data.mapper.GenericMapper;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Recipe;
import it.unical.IoTOnChain.service.BatchService;
import it.unical.IoTOnChain.service.CompanyService;
import it.unical.IoTOnChain.service.ProductTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/company/product-type")
public class ProductTypeController {
  private final BatchService batchService;
  private final ProductTypeService productTypeService;
  private final CompanyService companyService;
  private final GenericMapper mapper;
  
  @GetMapping
  public ResponseEntity<List<ProductTypeToOwner>> getAllProductTypesByLoggedCompany(@AuthenticationPrincipal Jwt principal) {
    log.debug("Get all product type  for company logged");
    String companyLogged = principal.getClaimAsString("preferred_username");
    if (companyLogged == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(mapper.mapForProductTypeOwner(productTypeService.getAllProductTypesByLoggedCompany(companyLogged)));
  }
  
  
  @PostMapping
  public ResponseEntity<ProductTypeToOwner> createProductType(@AuthenticationPrincipal Jwt principal, @RequestBody CreateProductTypeDTOFromOwner dto) {
    log.debug("Create product type {}", dto);
    String companyLogged = principal.getClaimAsString("preferred_username");
    Company company = companyService.getOneByName(companyLogged);
    return ResponseEntity.ok(mapper.map(productTypeService.createProductTypeForCompany(company, dto.getName(), dto.getUnity(), mapper.map(dto.getRecipe()) )));
  }
}
