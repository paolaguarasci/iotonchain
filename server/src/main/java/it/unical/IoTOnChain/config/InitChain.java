package it.unical.IoTOnChain.config;

import it.unical.IoTOnChain.service.NotarizeService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
    notarizeService.notarize("Ciao Chain!" + LocalDateTime.now());
    log.info("Init Chain - End");
  }
}
