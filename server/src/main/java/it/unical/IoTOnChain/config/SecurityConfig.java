package it.unical.IoTOnChain.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Slf4j
//@EnableWebSecurity
public class SecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//    http
//      .authorizeHttpRequests((requests) -> requests
//        .anyRequest().authenticated()
//      ).oauth2ResourceServer((oauth2) ->
//        oauth2.jwt(Customizer.withDefaults()));
    
    http.csrf(AbstractHttpConfigurer::disable)
      .cors(AbstractHttpConfigurer::disable)
      .exceptionHandling((exception) -> {
        log.error("Errore {}", exception);
      })
      .authorizeHttpRequests((requests) -> requests
        .anyRequest().permitAll()
      );
    return http.build();
  }
}
