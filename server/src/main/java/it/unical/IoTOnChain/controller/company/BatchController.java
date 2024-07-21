package it.unical.IoTOnChain.controller.company;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/v1/company/batch")
public class BatchController {
  private final BatchService batchService;
  private final CompanyService companyService;
  private final GenericMapper mapper;
  private final ProductTypeService productTypeService;
  
  @GetMapping
//  public ResponseEntity<List<BatchToOwner>> getAllProductsByCompanyLogged(@AuthenticationPrincipal Jwt principal) {
  public ResponseEntity<List<BatchToOwner>> getAllProductsByCompanyLogged() {
    log.debug("Get all product for company logged");
//    if (principal != null) {
//      String companyLogged = principal.getClaimAsString("given_name");
//      return ResponseEntity.ok(mapper.mapForProductOwner(batchService.getAllProductByCompanyLogged(companyLogged)));
//    } else {
      String companyLogged = "barillaSPA";
      return ResponseEntity.ok(mapper.mapForProductOwner(batchService.getAllProductByCompanyLogged(companyLogged)));
      // return ResponseEntity.ok(List.of());
//    }
  }
  

  @PostMapping
  public ResponseEntity<BatchToOwner> createBatch(@RequestBody CreateBatchDTOFromOwner dto) throws NoEnoughRawMaterialsException {
    log.debug("Create one batch of product type for company logged");
    String companyLogged = "barillaSPA";
//    if (principal != null) {
//      companyLogged = principal.getClaimAsString("given_name");
//    }
    Company company = companyService.getOneByName(companyLogged);
    ProductType productType = productTypeService.getOneById(dto.getProductTypeID());

    if(company == null || productType == null) {
      return ResponseEntity.badRequest().body(null);
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(batchService.produce(company, productType, dto.getQuantity(), dto.getBatchId())));
  }
}
