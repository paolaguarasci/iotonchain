package it.unical.IoTOnChain.config;

import it.unical.IoTOnChain.data.model.*;
import it.unical.IoTOnChain.exception.MoveIsNotPossibleException;
import it.unical.IoTOnChain.exception.NoEnoughRawMaterialsException;
import it.unical.IoTOnChain.service.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.init.db", havingValue = "true")
public class InitDB implements CommandLineRunner {
  private final CompanyService companyService;
  private final ProductTypeService productTypeService;
  private final BatchService batchService;
  private final RecipeService recipeService;
  private final TransferService transferService;
  
  
  @SneakyThrows
  @Override
  public void run(String... args) throws Exception {
    log.info("Init DB - Start");
    
    Company paolaSPA = makeCompany("paolaSPA");
    ProductType basilicoType = makeProductTypeAndAssociateToCompany(paolaSPA, "basilico ligure", "kg", null);
    Batch basilicoBatch = produce(paolaSPA, basilicoType, 10, "batchId_123_basilico");

    Company nicolaSPA = makeCompany("nicolaSPA");
    ProductType aglioType = makeProductTypeAndAssociateToCompany(nicolaSPA, "aglio", "kg", null);
    Batch aglioBatch = produce(nicolaSPA, aglioType, 10, "batchId_123_aglio");

    Company filippoSPA = makeCompany("filippoSPA");
    ProductType olioType = makeProductTypeAndAssociateToCompany(filippoSPA, "olio", "lt", null);
    Batch olioBatch = produce(filippoSPA, olioType, 10, "batchId_123_olio");

    Company barillaSPA = makeCompany("barillaSPA");
    Map<ProductType, List<String>> ingredientsOfPestoLigure = new HashMap<>();
    ingredientsOfPestoLigure.put(basilicoType, List.of("80", "%"));
    ingredientsOfPestoLigure.put(aglioType, List.of("5", "%"));
    ingredientsOfPestoLigure.put(olioType, List.of("15", "%"));
    Recipe pestoRecipe = makeRecipe("pesto ligure", ingredientsOfPestoLigure);
    ProductType pestoType = makeProductTypeAndAssociateToCompany(barillaSPA, "pesto", "kg", pestoRecipe);

    Batch pestoBatch = null;

    try {
      pestoBatch = produce(barillaSPA, pestoType, 1, "batchId_123_pesto");
      log.error("Hai prodotto del pesto!");
    } catch (NoEnoughRawMaterialsException e) {
      log.error("Non hai abbastanza materie prime!");
      transferBatch(paolaSPA, basilicoBatch, barillaSPA, 5);
      transferBatch(nicolaSPA, aglioBatch, barillaSPA, 5);
      transferBatch(filippoSPA, olioBatch, barillaSPA, 5);
      pestoBatch = produce(barillaSPA, pestoType, 1, "batchId_123_pesto");
    }
    
    log.info("Init DB - End");
  }
  
  private void transferBatch(Company companyLogged, Batch basilicoBatch, Company barillaSPA, int i) throws MoveIsNotPossibleException, NoEnoughRawMaterialsException {
    transferService.makeTransactionOneShot(companyLogged, basilicoBatch, barillaSPA, i);
  }
  
  private Batch produce(Company company, ProductType type, int quantity, String batchId) throws NoEnoughRawMaterialsException {
    return batchService.produce(company, type, quantity, batchId);
  }
  
  private ProductType makeProductTypeAndAssociateToCompany(Company company, String productTypeName, String unity, Recipe recipe) {
    return productTypeService.createProductTypeForCompany(company, productTypeName, unity, recipe);
  }
  
  private Company makeCompany(String name) {
    return companyService.makeOne(name);
  }
  
  private Recipe makeRecipe(String name, Map<ProductType, List<String>> ingredients) {
    return recipeService.createOne(name, ingredients);
  }
}
