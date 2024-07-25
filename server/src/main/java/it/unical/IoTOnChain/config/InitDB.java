package it.unical.IoTOnChain.config;

import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.ProductType;
import it.unical.IoTOnChain.data.model.Recipe;
import it.unical.IoTOnChain.exception.MoveIsNotPossibleException;
import it.unical.IoTOnChain.exception.NoEnoughRawMaterialsException;
import it.unical.IoTOnChain.service.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

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
  private final UserInfoService userInfoService;
  private final ChainService chainService;
  
  @SneakyThrows
  @Override
  public void run(String... args) throws Exception {
    log.info("Init DB - Start");
    
    // A quanto ho capito se non fai la get del Future si nchiovetta
    
    Future<String> s = chainService.testAsync();
    log.info("Init DB - Finished ssss");
    log.info("Init DB - async {}", s.get());
    
    Company paolaSPA = makeCompany("paolaspa");
    ProductType basilicoType = makeProductTypeAndAssociateToCompany(paolaSPA, "basilico ligure", "kg", null);
    Batch basilicoBatch = produce(paolaSPA, basilicoType, 10, "batchId_123_basilico");
    
    Company nicolaSPA = makeCompany("nicolaspa");
    ProductType aglioType = makeProductTypeAndAssociateToCompany(nicolaSPA, "aglio", "kg", null);
    Batch aglioBatch = produce(nicolaSPA, aglioType, 10, "batchId_123_aglio");
    
    Company filippoSPA = makeCompany("filippospa");
    ProductType olioType = makeProductTypeAndAssociateToCompany(filippoSPA, "olio", "lt", null);
    Batch olioBatch = produce(filippoSPA, olioType, 10, "batchId_123_olio");
    
    Company barillaSPA = makeCompany("barillaspa");
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
    
    // Create one operator for paolaspa company
    userInfoService.newUser(paolaSPA, "op_paola");
    userInfoService.newUser(nicolaSPA, "op_nicola");
    userInfoService.newUser(barillaSPA, "op_barilla");
    userInfoService.newUser(filippoSPA, "op_filippo");
    
    log.info("Init DB - End");
  }
  
  private String normalizeString(String str) {
    return str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
  }
  
  private void transferBatch(Company companyLogged, Batch basilicoBatch, Company barillaSPA, int i) throws MoveIsNotPossibleException, NoEnoughRawMaterialsException {
    transferService.makeTransactionOneShot(companyLogged, basilicoBatch, barillaSPA, i);
  }
  
  private Batch produce(Company company, ProductType type, int quantity, String batchId) throws NoEnoughRawMaterialsException {
    return batchService.produce(company, type, quantity, batchId);
  }
  
  private ProductType makeProductTypeAndAssociateToCompany(Company company, String productTypeName, String unity, Recipe recipe) {
    return productTypeService.createProductTypeForCompany(company, normalizeString(productTypeName), unity, recipe);
  }
  
  private Company makeCompany(String name) {
    return companyService.makeOne(normalizeString(name));
  }
  
  private Recipe makeRecipe(String name, Map<ProductType, List<String>> ingredients) {
    return recipeService.createOne(name, ingredients);
  }
  
}
