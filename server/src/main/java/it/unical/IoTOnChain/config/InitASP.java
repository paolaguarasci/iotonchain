package it.unical.IoTOnChain.config;


import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.service.BatchService;
import it.unical.IoTOnChain.service.CompanyService;
import it.unical.IoTOnChain.service.SolverService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.init.asp", havingValue = "true")
public class InitASP implements CommandLineRunner {
  private final SolverService solverService;
  private final CompanyService companyService;
  private final BatchService batchService;
  
  @SneakyThrows
  @Override
  public void run(String... args) throws Exception {
    log.info("Init ASP - Start");
    var r = findBatchesByQuantityAndType("barillaspa", "pesto", 1);
    log.debug("lotti scelti {}", String.join(", ", r));
    log.info("Init ASP - End");
  }
  
  private List<String> findBatchesByQuantityAndType(String companyName, String productType, int quantity) throws IOException, URISyntaxException {
    StringBuilder fatti = new StringBuilder();
    List<Triplet> batchTriplets = new ArrayList<>();
    List<Batch> batches = batchService.getAllProductByCompanyLogged(companyName);
    
    for (Batch batch : batches) {
      batchTriplets.add(new Triplet<>(batch.getBatchId(), batch.getProductType().getName(), batch.getQuantity()));
    }
    
    fatti
      .append(solverService.listToAtomArita3("batch", batchTriplets)).append(" ")
      .append(solverService.listToAtomArita1("amount", List.of(String.valueOf(quantity)))).append(" ")
      .append(solverService.listToAtomArita1("type", List.of(productType)));
    
    log.info(fatti.toString());
    
    List<String> models = solverService.solveFileAndString("FindBatch.lp", fatti.toString());
    if (!models.isEmpty()) {
      List<Pair> results = solverService.atomArita2ToList(models.getFirst(), "scelto");
      return results.stream().map(r -> r.getValue0().toString()).toList();
    }
    return new ArrayList<>();
  }
  
}
