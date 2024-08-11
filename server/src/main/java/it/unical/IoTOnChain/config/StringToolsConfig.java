package it.unical.IoTOnChain.config;

import it.unical.IoTOnChain.utils.StringTools;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StringToolsConfig {
  
  @Bean
  public StringTools stringTools() {
    return new StringTools();
  }
}
