package it.unical.IoTOnChain.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class PollingConfig implements Serializable {
  
  @Scheduled(fixedRate = 5000)
  public void poll() {
    System.out.println("Polling");
  }
}
