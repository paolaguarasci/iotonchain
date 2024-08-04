package it.unical.IoTOnChain.config;


import it.unical.IoTOnChain.service.SolverService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.init.asp", havingValue = "true")
public class InitASP implements CommandLineRunner {
  private final SolverService solverService;
  
  @SneakyThrows
  @Override
  public void run(String... args) throws Exception {
    log.info("Init ASP - Start");
    List<String> models = solverService.solveFile("FindBatch.lp");
    for (String model : models) {
      log.info(model);
    }
    log.debug("Ci sono {} modelli", models.size());
    models.forEach(model -> {
      log.debug(model);
      var results = solverService.atomArita2ToList(model, "scelto");
      log.debug("Ci sono {} soluzioni", results.size());
      results.forEach(result -> {
        log.debug("lotto {} quantity {}", result.getValue0(), result.getValue1());
      });
    });
    log.info("Init ASP - End");
  }
  
}
