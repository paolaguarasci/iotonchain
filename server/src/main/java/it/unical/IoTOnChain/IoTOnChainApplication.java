package it.unical.IoTOnChain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class IoTOnChainApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(IoTOnChainApplication.class, args);
  }
  
}
