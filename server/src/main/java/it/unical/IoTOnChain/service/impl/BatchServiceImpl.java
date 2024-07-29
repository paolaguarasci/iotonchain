package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.*;
import it.unical.IoTOnChain.exception.MoveIsNotPossibleException;
import it.unical.IoTOnChain.exception.NoEnoughRawMaterialsException;
import it.unical.IoTOnChain.repository.BatchRepository;
import it.unical.IoTOnChain.repository.CompanyRepository;
import it.unical.IoTOnChain.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class BatchServiceImpl implements BatchService {
  private final BatchRepository batchRepository;
  private final ProductTypeService productTypeService;
  private final CompanyService companyService;
  private final CompanyRepository companyRepository;
  private final DocumentService documentService;
  private final RecipeService recipeService;
  private final ProductionProcessService productionProcessService;
  
  @Override
  public List<Batch> getAllProductByCompanyLogged(String companyLogged) {
    if (companyLogged == null || companyLogged.isEmpty()) {
      return List.of();
    }
    Optional<Company> company = companyRepository.findByName(companyLogged);
    if (company.isPresent()) {
      return batchRepository.findAllByCompanyOwner(company.get());
    }
    return List.of();
  }
  
  private Map<String, Object> checkQuantityOfType(Company company, ProductType type, Long quantity) {
    List<Batch> batches = batchRepository.findAllByCompanyOwnerAndProductType(company, type);
    log.debug("La compagnia {} ha {} lotti di tipo {}", company.getName(), batches.size(), type.getName());
    Map<String, Object> map = new HashMap<>();
    Set<Batch> newBatches = new HashSet<>();
    int k = 0;
    for (Batch batch : batches) {
      k += batch.getQuantity();
      newBatches.add(batch);
      log.debug("Aggiungo il lotto {}", batch.getBatchId());
      if (k >= quantity) {
        break;
      }
    }
    map.put("k", k);
    map.put("batch", newBatches);
    return map;
  }
  
  @Override
  public Batch produce(Company company, ProductType type, int quantity, String batchId, List<String> documents, List<String> ingredients, List<Map<String, String>> steps) throws NoEnoughRawMaterialsException {
    boolean checkMaterial = true;
    Set<Batch> rawMaterials = new HashSet<>();
    log.debug("company {}", company);
    log.debug("ptype {}", type);
    log.debug("quantity {}", quantity);
    log.debug("batchid {}", batchId);
    log.debug("ingredients {}", ingredients);
    log.debug("steps {}", steps);
    
    if (type.getRecipe() != null) {
      for (RecipeRow recipeRow : type.getRecipe().getRecipeRow()) {
        // FIXME potenziali side effect quando si mischiano diverse unita' di misura!
        Map<String, Object> checkQuantityOfType1 = new HashMap<>();
        checkQuantityOfType1 = checkQuantityOfType(company, recipeRow.getProduct(), recipeRow.getQuantity());
        
        log.debug("Controllo {}", recipeRow.getProduct().getName());
        if (Long.parseLong(String.valueOf(checkQuantityOfType1.get("k"))) < ((quantity / 100.00) * recipeRow.getQuantity())) {
          checkMaterial = false;
          break;
        }
        rawMaterials.addAll((Set<Batch>) checkQuantityOfType1.get("batch"));
      }
    }
    
    log.debug("raw materials {}", rawMaterials);
    log.debug("check materials {}", checkMaterial);
    
    // TODO DEVO ELIMINARE DAI LOTTI!
    
    
    if (type.getRecipe() == null || checkMaterial) {
      // salva sulla chain!
      
      Batch newProd = Batch.builder()
        .companyOwner(company)
        .companyProducer(company)
        .batchId(batchId)
        .productType(type)
        .rawMaterials(rawMaterials)
        .productionDate(LocalDateTime.now())
        .quantity(quantity)
        .build();
      
      if (documents != null && !documents.isEmpty()) {
        List<Document> docFromDB = documentService.getByIdList(documents);
        newProd.setDocuments(new HashSet<>(docFromDB));
      }
      
      if (ingredients != null && !ingredients.isEmpty()) {
        List<RecipeRow> rowFromDb = recipeService.getRecipeRowsByIdList(ingredients);
        Recipe localRecipe = recipeService.createOneByCloneAndMaterialize("recipe_" + batchId, quantity, rowFromDb);
        newProd.setLocalRecipe(localRecipe);
      }
      
      if (steps != null && !steps.isEmpty()) {
        List<ProductionStep> stepFromDb = productionProcessService.getProcessStepsByIdList(steps);
        ProductionProcess localProductionProcess = productionProcessService.createOneByClone("process_" + batchId, stepFromDb);
        newProd.setLocalProcessProduction(localProductionProcess);
      }
      
      
      return batchRepository.save(newProd);
    }
    
    log.debug("ciao");
    throw new NoEnoughRawMaterialsException("");
  }
  
  @Override
  public Batch move(Company owner, Batch batch, Company company, int quantity) throws MoveIsNotPossibleException, NoEnoughRawMaterialsException {
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
      productTypeService.createProductTypeForCompany(company, batch.getProductType().getName(), batch.getProductType().getUnity(), batch.getProductType().getRecipe(), batch.getProductType().getProductionProcess());
      Batch newBatch = this.produceByMovement(company, batch.getProductType(), quantity, batch.getBatchId() + "_X", batch);
      newBatch.setCompanyProducer(owner);
      batchRepository.save(newBatch);
      return newBatch;
    } else {
      // Il trasferimento non è fattibile
      log.debug("(companyService.companyExist(owner) {} \n" +
        "      companyService.companyExist(company) {} \n" +
        "      batch.getCompanyOwner().equals(owner) {} \n" +
        "      batch.getQuantity() >= quantity) {} ", companyService.companyExist(owner), companyService.companyExist(company), batch.getCompanyOwner().equals(owner), batch.getQuantity() >= quantity);
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
  
  @Override
  public Batch produceByMovement(Company company, ProductType productType, int quantity, String s, Batch old) {
    // salva sulla chain!
    return batchRepository.save(Batch.builder()
      .companyOwner(company)
      .companyProducer(company)
      .batchId(s)
      .productType(productType)
      .rawMaterials(old.getRawMaterials())
      .productionDate(LocalDateTime.now())
      .quantity(quantity)
      .build());
  }
  
  @Override
  public Map<String, Object> trackInfo(Company companyLogged, String batchId) {
    
    Map<String, Object> info = new HashMap<>();
    Optional<Batch> batchOptional = batchRepository.findById(UUID.fromString(batchId));
    
    if (batchOptional.isEmpty()) {
      return null;
    }
    Batch batch = batchOptional.get();
    if (!companyService.companyExist(companyLogged) || !batch.getCompanyOwner().equals(companyLogged)) {
      return null;
    }
    
    ProductionProcess productionProcessGlobal = ProductionProcess.builder().build();
    ProductionProcess productionProcessParent = batch.getProductType().getProductionProcess();
    
    // mi serve la data e va sortato per data
    // mi servono le transazioni per certificare il processo
    
    batch.getRawMaterials().forEach(material -> {
      
      productionProcessGlobal.getSteps().addAll(material.getProductType().getProductionProcess().getSteps());
      List i = new ArrayList();
      material.getProductType().getProductionProcess().getSteps().stream()
        .sorted((a, b) -> a.getPosition() > b.getPosition() ? -1 : 1)
        .forEach((step) -> {
          Map<String, Object> rawMaterial = new HashMap<>();
          rawMaterial.put("batch_id", material.getBatchId());
          rawMaterial.put("step_id", step.getId());
          rawMaterial.put("name", step.getName());
          rawMaterial.put("description", step.getDescription());
          rawMaterial.put("company", material.getCompanyProducer().getName());
          
          if(step.getDate() != null) {
            rawMaterial.put("date", step.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
          }
          rawMaterial.put("notarize", step.getNotarize());
          if(material.getProductionLocation() != null) {
            rawMaterial.put("location", material.getProductionLocation().getAddress());
          }
          i.add(rawMaterial);
        });
      
      info.put(material.getBatchId(), i);
    });
    
    productionProcessGlobal.getSteps().addAll(productionProcessParent.getSteps());
    
    // info.put(batch.getBatchId(), productionProcessParent);
    List y = new ArrayList();
    batch.getProductType().getProductionProcess().getSteps().stream().forEach((step) -> {
      Map<String, Object> ppParent = new HashMap<>();
      ppParent.put("batch_id", batch.getBatchId());
      ppParent.put("step_id", step.getId());
      ppParent.put("name", step.getName());
      ppParent.put("description", step.getDescription());
      ppParent.put("company", batch.getCompanyProducer().getName());

      ppParent.put("notarize", step.getNotarize());
      if(batch.getProductionLocation() != null) {
        ppParent.put("location", batch.getProductionLocation().getAddress());
      }
      if(step.getDate() != null) {
        ppParent.put("date", step.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      }
      y.add(ppParent);
    });
    info.put(batch.getBatchId(), y);
    info.put("global", productionProcessGlobal);
    
    
    return info;
  }
}
