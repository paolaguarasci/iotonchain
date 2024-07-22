package it.unical.IoTOnChain.utils;


import org.springframework.context.annotation.Bean;

public class NormalizeString {

  @Bean
  String normalize(String str) {
    return str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
  }
}
