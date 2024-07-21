package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.ProductType;
import it.unical.IoTOnChain.data.model.RecipeRow;
import it.unical.IoTOnChain.exception.MoveIsNotPossibleException;
import it.unical.IoTOnChain.exception.NoEnoughRawMaterialsException;
import it.unical.IoTOnChain.repository.BatchRepository;
import it.unical.IoTOnChain.repository.CompanyRepository;
import it.unical.IoTOnChain.service.BatchService;
import it.unical.IoTOnChain.service.CompanyService;
import it.unical.IoTOnChain.service.ProductTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BatchServiceImpl implements BatchService {
  private final BatchRepository batchRepository;
  private final ProductTypeService productTypeService;
  private final CompanyService companyService;
  private final CompanyRepository companyRepository;
  
  @Override
  public List<Batch> getAllProductByCompanyLogged(String companyLogged) {
    if(companyLogged == null || companyLogged.isEmpty()) {
      return List.of();
    }
    Optional<Company> company = companyRepository.findByName(companyLogged);
    if(company.isPresent()) {
      return batchRepository.findAllByCompanyOwner(company.get());
    }
    return List.of();
  }
  
  private Map<String, Object> checkQuantityOfType(Company company, ProductType type, Long quantity) {
    List<Batch> batches = batchRepository.findAllByCompanyOwnerAndProductType(company, type);
    Map<String, Object> map = new HashMap<>();
    Set<Batch> newBatches = new HashSet<>();
    int k = 0;
    for (Batch batch : batches) {
      k += batch.getQuantity();
      newBatches.add(batch);
      if (k >= quantity) {
        break;
      }
    }
    log.debug("batches: {}", batches.size());
    log.debug("batches: {}", batches);
    log.debug("New batches: {}", newBatches.size());
    map.put("k", k);
    map.put("batch", newBatches);
    return map;
  }
  
  @Override
  public Batch produce(Company company, ProductType type, int quantity, String batchId) throws NoEnoughRawMaterialsException {
    boolean checkMaterial = true;
    Map<String, Object> checkQuantityOfType = new HashMap<>();
    log.debug("Sto provando a produrre {} ", batchId);
    if (type.getRecipe() != null) {
      for (RecipeRow recipeRow : type.getRecipe().getRecipeRow()) {
        // FIXME potenziali side effect quando si mischiano diverse unita' di misura!
        checkQuantityOfType = checkQuantityOfType(company, recipeRow.getProduct(), recipeRow.getQuantity());
        if (Long.parseLong(String.valueOf(checkQuantityOfType.get("k"))) < ((quantity / 100.00) * recipeRow.getQuantity())) {
          checkMaterial = false;
          break;
        }
      }
    }
    
    log.debug("Quantity k {}", checkQuantityOfType.get("k"));
    log.debug("Quantity batch {}", checkQuantityOfType.get("batch"));
    
    
    if (type.getRecipe() == null || checkMaterial) {
      // salva sulla chain!
      return batchRepository.save(Batch.builder()
        .companyOwner(company)
        .companyProducer(company)
        .batchId(batchId)
        .productType(type)
        .rawMaterialList((Set<Batch>) checkQuantityOfType.get("batch"))
        .productionDate(LocalDateTime.now())
        .quantity(quantity)
        .build());
    }
    
    log.debug("ciao");
    throw new NoEnoughRawMaterialsException("");
  }
  
  @Override
  public void move(Company owner, Batch batch, Company company, int quantity) throws MoveIsNotPossibleException, NoEnoughRawMaterialsException {
    if (companyService.companyExist(owner)
      && companyService.companyExist(company)
      && batch.getCompanyOwner().equals(owner)
      && batch.getQuantity() >= quantity) {
      // Il trasferimento è fattibile.
      batch.setQuantity(batch.getQuantity() - quantity);
      batchRepository.save(batch);
      // TODO trovare una soluzione per l'assegnazione dei lotti di produzione, fare inserire manualmente?
      log.debug("Move {} {}", batch.getBatchId(), batch.getQuantity());
      log.debug("Batch to move {}", batch);
      productTypeService.createProductTypeForCompany(company, batch.getProductType().getName(), batch.getProductType().getUnity(), batch.getProductType().getRecipe());
      Batch newBatch = this.produce(company, batch.getProductType(), quantity, batch.getBatchId() + "_X");
      newBatch.setCompanyProducer(owner);
      batchRepository.save(newBatch);
    } else {
      // Il trasferimento non è fattibile
      log.debug("(companyService.companyExist(owner) {} \n" +
        "      companyService.companyExist(company) {} \n" +
        "      batch.getCompanyOwner().equals(owner) {} \n" +
        "      batch.getQuantity() >= quantity) {} ", companyService.companyExist(owner),  companyService.companyExist(company), batch.getCompanyOwner().equals(owner), batch.getQuantity() >= quantity);
      throw new MoveIsNotPossibleException("Non si puo' fare!");
    }
    
  }
  
  @Override
  public Batch getOneByBatchIdAndCompany(Company companyOwner, String batchID) {
    List<Batch> batches = batchRepository.findAllByBatchIdAndCompanyOwner(batchID, companyOwner);
    if (batches.isEmpty()) {
      return null;
    }
    return batches.getFirst();
  }
}
