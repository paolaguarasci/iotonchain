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
  public Batch produce(Company company, ProductType type, int quantity, String batchId) throws NoEnoughRawMaterialsException {
    boolean checkMaterial = true;
    Set<Batch> rawMaterials = new HashSet<>();
    log.debug("company {}", company);
    log.debug("ptype {}", type);
    log.debug("quantity {}", quantity);
    log.debug("batchid {}", batchId);

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
      return batchRepository.save(Batch.builder()
        .companyOwner(company)
        .companyProducer(company)
        .batchId(batchId)
        .productType(type)
        .rawMaterialList(rawMaterials)
        .productionDate(LocalDateTime.now())
        .quantity(quantity)
        .build());
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
      productTypeService.createProductTypeForCompany(company, batch.getProductType().getName(), batch.getProductType().getUnity(), batch.getProductType().getRecipe());
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
      .rawMaterialList(old.getRawMaterialList())
      .productionDate(LocalDateTime.now())
      .quantity(quantity)
      .build());
  }
}
