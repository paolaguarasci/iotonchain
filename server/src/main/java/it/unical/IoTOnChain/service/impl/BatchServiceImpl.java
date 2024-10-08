package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.config.InitDB;
import it.unical.IoTOnChain.data.model.*;
import it.unical.IoTOnChain.exception.MoveIsNotPossibleException;
import it.unical.IoTOnChain.exception.NoEnoughRawMaterialsException;
import it.unical.IoTOnChain.repository.BatchRepository;
import it.unical.IoTOnChain.repository.CompanyRepository;
import it.unical.IoTOnChain.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URISyntaxException;
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
  private final RecipeBatchService recipeBatchService;
  private final ProductionProcessService productionProcessService;
  private final ProductionProcessBatchService productionProcessBatchService;
  private final SolverService solverService;
  private final boolean USEASP = true;
//  private final boolean USEASP = false;
  
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
  public Batch produce(Company company, ProductType type, int quantity, String batchId, List<String> documents, List<String> ingredients, List<Map<String, String>> steps) throws NoEnoughRawMaterialsException, IOException, URISyntaxException {
    boolean checkMaterial = true;
    Set<Batch> rawMaterials = new HashSet<>();
    log.debug("company {}", company);
    log.debug("ptype {}", type);
    log.debug("quantity {}", quantity);
    log.debug("batchid {}", batchId);
    log.debug("ingredients {}", ingredients);
    log.debug("steps {}", steps);
    
    Map<Batch, Integer> mappaLottoIdQuantity = new HashMap<>();
    
    if (type.getRecipe() != null) {
      for (RecipeRow recipeRow : type.getRecipe().getRecipeRow()) {
        int q = 0;
        // FIXME potenziali side effect quando si mischiano diverse unita' di misura!
        if (!USEASP) {
          Map<String, Object> checkQuantityOfType1 = new HashMap<>();
          checkQuantityOfType1 = checkQuantityOfType(company, recipeRow.getProduct(), recipeRow.getQuantity());
          log.debug("Controllo {}", recipeRow.getProduct().getName());
          q = (int) Math.ceil((quantity / 100.00) * recipeRow.getQuantity());
          if (Long.parseLong(String.valueOf(checkQuantityOfType1.get("k"))) < q) {
            checkMaterial = false;
            break;
          }
          Set<Batch> batches = (Set<Batch>) checkQuantityOfType1.get("batch");
          rawMaterials.addAll(batches);
          mappaLottoIdQuantity.put(batches.stream().findFirst().get(), q);
        } else {
          List<String> checkQuantityOfType1 = new ArrayList<>();
          q = (int) Math.ceil((quantity / 100.00) * recipeRow.getQuantity());
          checkQuantityOfType1 = findBatchesByQuantityAndType(company.getName(), recipeRow.getProduct().getName(), q);
          Set<Batch> batches = batchRepository.findAllByBatchIdIsIn(checkQuantityOfType1);
          log.debug("Check Quantity type {}, {} ", checkQuantityOfType1.size(), checkQuantityOfType1);
          log.debug("Parametri {}, {}, {}, risultato BATCH TROVATI {} ", company.getName(), recipeRow.getProduct().getName(), q, batches.size());
          if (batches.isEmpty()) {
            checkMaterial = false;
            break;
          }
          mappaLottoIdQuantity.put(batches.stream().findFirst().get(), q);
          rawMaterials.addAll(batches);
        }
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
        // .rawMaterials(rawMaterials)
        .productionDate(LocalDateTime.now())
        .quantity(quantity)
        .build();
      
      if (documents != null && !documents.isEmpty()) {
        List<Document> docFromDB = documentService.getByIdList(documents);
        newProd.setDocuments(new HashSet<>(docFromDB));
      }
      
      if (ingredients != null && !ingredients.isEmpty()) {
        List<RecipeRow> rowFromDb = recipeService.getRecipeRowsByIdList(ingredients);
        RecipeBatch localRecipe = recipeBatchService.createOneByCloneAndMaterialize(mappaLottoIdQuantity);
        newProd.setLocalRecipe(localRecipe);
      }
      
      if (steps != null && !steps.isEmpty()) {
        List<ProductionStep> stepFromDb = productionProcessService.getProcessStepsByIdList(steps);
        log.debug("Ci sono {} step da salvare", stepFromDb.size());
        ProductionProcessBatch localProductionProcess = productionProcessBatchService.createOneByClone("process_" + batchId, stepFromDb);
        log.debug("Ho salvato il processo {}", localProductionProcess);
        newProd.setLocalProcessProduction(localProductionProcess);
      }
      
      log.debug("Nuovo batch creato {}", newProd);
      return batchRepository.save(newProd);
    }
    
    log.debug("ciao");
    throw new NoEnoughRawMaterialsException("");
  }
  
  @Override
  public Batch move(Company owner, Batch batch, Company company, int quantity, boolean is2step) throws MoveIsNotPossibleException, NoEnoughRawMaterialsException {
    if (companyService.companyExist(owner)
      && companyService.companyExist(company)
      && batch.getCompanyOwner().equals(owner)
      && (is2step || batch.getQuantity() >= quantity)) {
      // Il trasferimento è fattibile.
      if (!is2step) {
        batch.setQuantity(batch.getQuantity() - quantity);
        batchRepository.save(batch);
      }
      // TODO trovare una soluzione per l'assegnazione dei lotti di produzione, fare inserire manualmente?
      log.debug("Move {} {}", batch.getBatchId(), batch.getQuantity());
      log.debug("Batch to move {}", batch);
      productTypeService.createProductTypeForCompany(company, batch.getProductType().getName(), batch.getProductType().getUnity(), batch.getProductType().getRecipe(), batch.getProductType().getProductionProcess());
//      Batch newBatch = this.produceByMovement(company, batch.getProductType(), quantity, batch.getBatchId() + "_X", batch);
      Batch newBatch = this.produceByMovement(company, batch.getProductType(), quantity, InitDB.randomString("lotto", 10, ""), batch);
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
    Optional<Batch> batch = batchRepository.findByBatchIdAndCompanyOwner(batchID, companyOwner);
    log.debug("Batch found {}", batch.get().getBatchId());
    return batch.orElse(null);
  }
  
  @Override
  public Batch produceByMovement(Company company, ProductType productType, int quantity, String s, Batch old) {
    // salva sulla chain!
    return batchRepository.save(Batch.builder()
      .companyOwner(company)
      .companyProducer(old.getCompanyProducer())
      .batchId(s)
      .productType(productType)
      // .rawMaterials(old.getRawMaterials())
      .localProcessProduction(old.getLocalProcessProduction())
      .localRecipe(old.getLocalRecipe())
      .productionDate(LocalDateTime.now())
      // .documents(old.getDocuments())
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
    
    
    ProductionProcessBatch productionProcessGlobal = ProductionProcessBatch.builder().steps(new ArrayList<>()).build();
    ProductionProcessBatch productionProcessParent = batch.getLocalProcessProduction();
    
    // mi serve la data e va sortato per data
    // mi servono le transazioni per certificare il processo
    
    if (batch.getLocalRecipe() != null) {
      batch.getLocalRecipe().getRecipeRow().forEach(recipeRow -> {
        if (recipeRow.getProduct() != null) {
          if (recipeRow.getProduct().getLocalProcessProduction() != null) {
            
            recipeRow.getProduct().getLocalProcessProduction().getSteps().forEach(step -> {
              productionProcessGlobal.getSteps().add(step);
            });
            
          } else {
            log.debug("Il prodotto {} ha processo locale null", recipeRow.getProduct().getBatchId());
          }
        }
        List i = new ArrayList();
        
        if (recipeRow.getProduct() != null && recipeRow.getProduct().getLocalProcessProduction() != null) {
          recipeRow.getProduct().getLocalProcessProduction().getSteps().stream()
            .sorted((a, b) -> a.getPosition() > b.getPosition() ? -1 : 1)
            .forEach((step) -> {
              Map<String, Object> rawMaterial = new HashMap<>();
              rawMaterial.put("batch_id", recipeRow.getProduct().getBatchId());
              rawMaterial.put("step_id", step.getId());
              rawMaterial.put("name", step.getName());
              rawMaterial.put("description", step.getDescription());
              rawMaterial.put("company", recipeRow.getProduct().getCompanyProducer().getName());
              
              log.debug("COMPANY ------------------- {}", batch.getCompanyProducer().getName());
              if (step.getNotarize() != null) {
                rawMaterial.put("notarize", step.getNotarize());
              }
              
              if (step.getDate() != null) {
                rawMaterial.put("date", step.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
              }
              // rawMaterial.put("notarize", step.getNotarize());
              if (recipeRow.getProduct().getProductionLocation() != null) {
                rawMaterial.put("location", recipeRow.getProduct().getProductionLocation().getAddress());
              }
              i.add(rawMaterial);
            });
          info.put(recipeRow.getProduct().getBatchId(), i);
        }
        
      });
    }
    if (productionProcessParent != null) {
      productionProcessParent.getSteps().forEach(step -> {
        productionProcessGlobal.getSteps().add(step);
      });
    }
    // info.put(batch.getBatchId(), productionProcessParent);
    List y = new ArrayList();
    if (batch.getLocalProcessProduction() != null) {
      batch.getLocalProcessProduction().getSteps().stream().forEach((step) -> {
        Map<String, Object> ppParent = new HashMap<>();
        ppParent.put("batch_id", batch.getBatchId());
        ppParent.put("step_id", step.getId());
        ppParent.put("name", step.getName());
        ppParent.put("description", step.getDescription());
        ppParent.put("company", batch.getCompanyProducer().getName());
        log.debug("COMPANY ------------------- {}", batch.getCompanyProducer().getName());
        if (step.getNotarize() != null) {
          ppParent.put("notarize", step.getNotarize());
        }
        
        if (batch.getProductionLocation() != null) {
          ppParent.put("location", batch.getProductionLocation().getAddress());
        }
        if (step.getDate() != null) {
          ppParent.put("date", step.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        y.add(ppParent);
      });
    }
    info.put(batch.getBatchId(), y);
    info.put("global", productionProcessGlobal);
    return info;
  }
  
  @Override
  public void block(Company company, String batchID, int quantity) {
    List<Batch> batchOptional = batchRepository.findAllByBatchIdAndCompanyOwner(batchID, company);
    if (!batchOptional.isEmpty()) {
      Batch batch = batchOptional.getFirst();
      batch.setQuantity(batch.getQuantity() - quantity);
      batchRepository.save(batch);
    }
  }
  
  @Override
  public void refound(Company company, String batchID, int quantity) {
    List<Batch> batchOptional = batchRepository.findAllByBatchIdAndCompanyOwner(batchID, company);
    if (!batchOptional.isEmpty()) {
      Batch batch = batchOptional.getFirst();
      batch.setQuantity(batch.getQuantity() + quantity);
      batchRepository.save(batch);
    }
  }
  
  @Override
  public List<String> findBatchesByQuantityAndType(String companyName, String productType, int quantity) throws IOException, URISyntaxException {
    StringBuilder fatti = new StringBuilder();
    List<Triplet> batchTriplets = new ArrayList<>();
    List<Batch> batches = getAllProductByCompanyLogged(companyName);
    log.debug("La compagnia {} ha {} lotti in totale", companyName, batches.size());
    
    if (batches.isEmpty()) {
      return new ArrayList<>();
    }
    
    for (Batch batch : batches) {
      batchTriplets.add(new Triplet<>(batch.getBatchId(), batch.getProductType().getName(), batch.getQuantity()));
    }
    
    fatti
      .append(solverService.listToAtomArita3("batch", batchTriplets)).append(" ")
      .append(solverService.listToAtomArita1("amount", List.of(String.valueOf(quantity)))).append(" ")
      .append(solverService.listToAtomArita1("type", List.of(productType)));
    
    log.info(fatti.toString());
    
    log.debug("ciao ciao sono io");
    
    List<String> models = solverService.solveFileAndString("FindBatch.lp", fatti.toString());
    log.debug("ciao ciao sono i2");
    if (!models.isEmpty()) {
      List<String> newBatches = new ArrayList<>();
      List<Pair> results = solverService.atomArita2ToList(models.getFirst(), "scelto");
      results.forEach(r -> newBatches.add(r.getValue0().toString()));
      return newBatches;
    }
    return new ArrayList<>();
  }
  
  @Override
  public Batch getOneByIdAndCompany(Company company, String id) {
    Optional<Batch> batch = batchRepository.findByIdAndCompanyOwner(UUID.fromString(id), company);
    return batch.orElse(null);
  }
  
  @Override
  public Map<String, Object> trackInfoPublic(String companyName, String idCleaned) {
    
    // TODO
    // PER ADESSO E' IDENTITO A QUELLO TO OWNER - VANNO CAPITE QUALI SONO LE EVENTUALI INFO CHE NON SI VUOLE MANDARE A TUTTI!
    
    Map<String, Object> info = new HashMap<>();
    
    Company companyOwner = companyService.getOneByName(companyName);
    
    if (companyOwner == null) {
      return null;
    }
    
    Optional<Batch> batchOptional = batchRepository.findByBatchIdAndCompanyOwner(idCleaned, companyOwner);
    
    if (batchOptional.isEmpty()) {
      return null;
    }
    Batch batch = batchOptional.get();
    
    
    ProductionProcessBatch productionProcessGlobal = ProductionProcessBatch.builder().steps(new ArrayList<>()).build();
    ProductionProcessBatch productionProcessParent = batch.getLocalProcessProduction();
    
    // mi serve la data e va sortato per data
    // mi servono le transazioni per certificare il processo
    
    if (batch.getLocalRecipe() != null) {
      batch.getLocalRecipe().getRecipeRow().forEach(recipeRow -> {
        if (recipeRow.getProduct() != null) {
          if (recipeRow.getProduct().getLocalProcessProduction() != null) {
            
            recipeRow.getProduct().getLocalProcessProduction().getSteps().forEach(step -> {
              productionProcessGlobal.getSteps().add(step);
            });
            
          } else {
            log.debug("Il prodotto {} ha processo locale null", recipeRow.getProduct().getBatchId());
          }
        }
        List i = new ArrayList();
        
        if (recipeRow.getProduct() != null && recipeRow.getProduct().getLocalProcessProduction() != null) {
          recipeRow.getProduct().getLocalProcessProduction().getSteps().stream()
            .sorted((a, b) -> a.getPosition() > b.getPosition() ? -1 : 1)
            .forEach((step) -> {
              Map<String, Object> rawMaterial = new HashMap<>();
              rawMaterial.put("batch_id", recipeRow.getProduct().getBatchId());
              rawMaterial.put("step_id", step.getId());
              rawMaterial.put("name", step.getName());
              rawMaterial.put("description", step.getDescription());
              rawMaterial.put("company", recipeRow.getProduct().getCompanyProducer().getName());
              
              log.debug("COMPANY ------------------- {}", batch.getCompanyProducer().getName());
              if (step.getNotarize() != null) {
                rawMaterial.put("notarize", step.getNotarize());
              }
              
              if (step.getDate() != null) {
                rawMaterial.put("date", step.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
              }
              // rawMaterial.put("notarize", step.getNotarize());
              if (recipeRow.getProduct().getProductionLocation() != null) {
                rawMaterial.put("location", recipeRow.getProduct().getProductionLocation().getAddress());
              }
              i.add(rawMaterial);
            });
          info.put(recipeRow.getProduct().getBatchId(), i);
        }
        
      });
    }
    if (productionProcessParent != null) {
      productionProcessParent.getSteps().forEach(step -> {
        productionProcessGlobal.getSteps().add(step);
      });
    }
    // info.put(batch.getBatchId(), productionProcessParent);
    List y = new ArrayList();
    if (batch.getLocalProcessProduction() != null) {
      batch.getLocalProcessProduction().getSteps().stream().forEach((step) -> {
        Map<String, Object> ppParent = new HashMap<>();
        ppParent.put("batch_id", batch.getBatchId());
        ppParent.put("step_id", step.getId());
        ppParent.put("name", step.getName());
        ppParent.put("description", step.getDescription());
        ppParent.put("company", batch.getCompanyProducer().getName());
        log.debug("COMPANY ------------------- {}", batch.getCompanyProducer().getName());
        if (step.getNotarize() != null) {
          ppParent.put("notarize", step.getNotarize());
        }
        
        if (batch.getProductionLocation() != null) {
          ppParent.put("location", batch.getProductionLocation().getAddress());
        }
        if (step.getDate() != null) {
          ppParent.put("date", step.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        y.add(ppParent);
      });
    }
    info.put(batch.getBatchId(), y);
    info.put("global", productionProcessGlobal);
    return info;
  }
}
