package it.unical.IoTOnChain.utils;


import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StringTools {
  
  @Bean
  public String normalize(String str) {
    return str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
  }
  
  @Bean
  public String clean(String dirt) {
    return Jsoup.clean(dirt, Safelist.none());
  }
}
