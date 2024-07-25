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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.init.chain", havingValue = "true")
public class InitChain implements CommandLineRunner {
  private final NotarizeService notarizeService;
  
  
  @SneakyThrows
  @Override
  public void run(String... args) throws Exception {
    log.info("Init Chain - Start");
    Notarize notarized = notarizeService.notarize("Ciao Chain!" + LocalDateTime.now());
    log.debug("Notarized {}", notarized);
    log.info("Init Chain - End");
  }
}
