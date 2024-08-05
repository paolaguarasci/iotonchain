package it.unical.IoTOnChain.controller.company;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unical.IoTOnChain.data.dto.BatchToOwner;
import it.unical.IoTOnChain.data.dto.CreateBatchDTOFromOwner;
import it.unical.IoTOnChain.data.mapper.GenericMapper;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.ProductType;
import it.unical.IoTOnChain.exception.NoEnoughRawMaterialsException;
import it.unical.IoTOnChain.service.BatchService;
import it.unical.IoTOnChain.service.CompanyService;
import it.unical.IoTOnChain.service.ProductTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/company/batch")
public class BatchController {
  private final BatchService batchService;
  private final CompanyService companyService;
  private final GenericMapper mapper;
  private final ProductTypeService productTypeService;
  private final ObjectMapper objectMapper;
  
  @GetMapping
  public ResponseEntity<List<BatchToOwner>> getAllProductsByCompanyLogged(@AuthenticationPrincipal Jwt principal) {
    log.debug("Get all product for company logged {}", principal);
    String companyLogged = principal.getClaimAsString("company");
    return ResponseEntity.ok(mapper.mapForProductOwner(batchService.getAllProductByCompanyLogged(companyLogged)));
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<BatchToOwner> getOneProductByCompanyLogged(@AuthenticationPrincipal Jwt principal, @PathVariable String id) {
    log.debug("Get one product {} for company logged {}", id, principal);
    String companyLogged = principal.getClaimAsString("company");
    Company company = companyService.getOneByName(companyLogged);
    if (company == null || id == null) {
      return ResponseEntity.badRequest().body(null);
    }
    return ResponseEntity.ok(mapper.map(batchService.getOneByIdAndCompany(company, Jsoup.clean(id, Safelist.none()))));
  }
  
  @PostMapping
  public ResponseEntity<?> createBatch(@AuthenticationPrincipal Jwt principal, @RequestBody CreateBatchDTOFromOwner dto) throws NoEnoughRawMaterialsException, JsonProcessingException {
    log.debug("Create one batch of product type for company logged {}", dto.toString());
    String companyLogged = principal.getClaimAsString("company");
    Company company = companyService.getOneByName(companyLogged);
    ProductType productType = productTypeService.getOneById(dto.getProductTypeID());
    
    if (company == null || productType == null) {
      return ResponseEntity.badRequest().body(null);
    }
    
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(batchService.produce(company, productType, dto.getQuantity(), dto.getBatchId(), dto.getDocuments(), dto.getIngredients(), dto.getProductionSteps())));
    } catch (NoSuchElementException e) {
      Map<String, String> errors = new HashMap<>();
      errors.put("message", "Not enough materials");
      return ResponseEntity.badRequest().body(objectMapper.writeValueAsString(errors));
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
  
  @GetMapping("/{id}/track")
  public ResponseEntity<String> getTrackInfo(@AuthenticationPrincipal Jwt principal, @PathVariable String id) throws JsonProcessingException {
    log.debug("Get single product information for company logged {}", principal);
    String companyLogged = principal.getClaimAsString("company");
    Company company = companyService.getOneByName(companyLogged);
    if (company == null) {
      return ResponseEntity.badRequest().body(null);
    }
    return ResponseEntity.ok(objectMapper.writeValueAsString(batchService.trackInfo(company, Jsoup.clean(id, Safelist.none()))));
  }
  
  
}
