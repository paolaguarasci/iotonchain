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
  private final UserInfoService userInfoService;
  private final ChainService chainService;
  private final ProductionProcessService productionProcessService;
  
  @Override
  // @Transactional(propagation = Propagation.REQUIRED)
  @SneakyThrows
  public void run(String... args) throws Exception {
    log.info("Init DB - Start");
    
    // A quanto ho capito se non fai la get del Future si nchiovetta
    
    chainService.testAsync();
    log.info("Init DB - Finished ssss");
    
    
    Company paolaSPA = makeCompany("paolaspa");
    ProductionStep raccoltaBasilico = ProductionStep.builder().position(0).name("Raccolta").description("Raccolta del basilico").build();
    ProductionProcess pBasilico = makeProcess("basilico", List.of(raccoltaBasilico));
    ProductType basilicoType = makeProductTypeAndAssociateToCompany(paolaSPA, "basilico ligure", "kg", null, pBasilico);
    Batch basilicoBatch = produce(paolaSPA, basilicoType, 10, "batchId_123_basilico");
    
    Company nicolaSPA = makeCompany("nicolaspa");
    ProductionStep raccoltaAglio = ProductionStep.builder().position(0).name("Raccolta").description("Raccolta dell'aglio").build();
    ProductionProcess pAglio = makeProcess("processo produttivo dell'aglio", List.of(raccoltaAglio));
    ProductType aglioType = makeProductTypeAndAssociateToCompany(nicolaSPA, "aglio", "kg", null, pAglio);
    Batch aglioBatch = produce(nicolaSPA, aglioType, 10, "batchId_123_aglio");
    
    Company filippoSPA = makeCompany("filippospa");
    ProductionStep raccoltaOlive = ProductionStep.builder().position(0).name("Raccolta").description("Raccolta delle olive").build();
    ProductionStep franturaOlive = ProductionStep.builder().position(1).name("Frantura").description("Olio al frantoio").build();
    ProductionProcess pOlio = makeProcess("processo produttivo dell'olio", List.of(raccoltaOlive, franturaOlive));
    ProductType olioType = makeProductTypeAndAssociateToCompany(filippoSPA, "olio", "lt", null, pOlio);
    Batch olioBatch = produce(filippoSPA, olioType, 10, "batchId_123_olio");
    
    Company barillaSPA = makeCompany("barillaspa");
    Map<ProductType, List<String>> ingredientsOfPestoLigure = new HashMap<>();
    ingredientsOfPestoLigure.put(basilicoType, List.of("80", "%"));
    ingredientsOfPestoLigure.put(aglioType, List.of("5", "%"));
    ingredientsOfPestoLigure.put(olioType, List.of("15", "%"));
    Recipe pestoRecipe = makeRecipe("pesto ligure", ingredientsOfPestoLigure);
    
    
    ProductionStep produzionePesto = ProductionStep.builder().position(0).name("Cucino").description("Produco il pesto").build();
    ProductionProcess pPesto = makeProcess("processo produttivo dell'olio", List.of(produzionePesto));
    ProductType pestoType = makeProductTypeAndAssociateToCompany(barillaSPA, "pesto", "kg", pestoRecipe, pPesto);
    
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
    return batchService.produce(company, type, quantity, batchId, List.of(), List.of(), List.of());
  }
  
  private ProductType makeProductTypeAndAssociateToCompany(Company company, String productTypeName, String unity, Recipe recipe, ProductionProcess productionProcess) {
    return productTypeService.createProductTypeForCompany(company, normalizeString(productTypeName), unity, recipe, productionProcess);
  }
  
  private Company makeCompany(String name) {
    Company c = companyService.makeOne(normalizeString(name));
    if (c == null) {
      c = companyService.getOneByName(name);
    }
    return c;
  }
  
  private Recipe makeRecipe(String name, Map<ProductType, List<String>> ingredients) {
    return recipeService.createOne(name, ingredients);
  }
  
  private ProductionProcess makeProcess(String name, List<ProductionStep> steps) {
    return productionProcessService.createOne(name, steps);
  }
  
}
