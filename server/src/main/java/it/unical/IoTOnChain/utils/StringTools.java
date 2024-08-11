package it.unical.IoTOnChain.utils;


import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class StringTools {
  
  public String normalizeStr(String str) {
    return str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
  }
  
  public String cleanStr(String dirt) {
    return Jsoup.clean(dirt, Safelist.none());
  }
}
