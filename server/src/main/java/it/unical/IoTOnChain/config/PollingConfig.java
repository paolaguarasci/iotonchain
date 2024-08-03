package it.unical.IoTOnChain.config;

import it.unical.IoTOnChain.service.TransportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Slf4j
@RequiredArgsConstructor
public class PollingConfig implements Serializable {
  
  private final TransportService transportService;
  @Async
  @Scheduled(fixedRate = 1000 * 60) // millisecondi
  public void poll() throws InterruptedException {
    log.info("Polling from DT Hub...");
    
    transportService.updateAllTransportDataFromDTHUb();
    
    log.info("... ending");
  }
}
