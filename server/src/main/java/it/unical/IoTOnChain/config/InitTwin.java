package it.unical.IoTOnChain.config;

import com.azure.core.credential.TokenCredential;
import com.azure.digitaltwins.core.DigitalTwinsAsyncClient;
import com.azure.digitaltwins.core.DigitalTwinsClient;
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.CountDownLatch;


@Component
@Slf4j
@ConditionalOnProperty(name = "app.init.dt", havingValue = "true")
public class InitTwin implements CommandLineRunner {
  private final CountDownLatch listModelsLatch = new CountDownLatch(1);
  private final DigitalTwinsClient digitalTwinsClientSync;
  private final DigitalTwinsAsyncClient digitalTwinsClientAsync;
  
  private final String digitalTwinDefaultId;
  private final String digitalTwinDefaultProperty;
  
  
  public InitTwin(@Value("${app.dt.dturl}") String dturl, @Value("${app.dt.dtiddefault}") String digitalTwinDefaultId, @Value("${app.dt.dtpropertydefault}") String digitalTwinDefaultProperty) {
    this.digitalTwinDefaultId = digitalTwinDefaultId;
    this.digitalTwinDefaultProperty = digitalTwinDefaultProperty;
    TokenCredential tokenCredential = new DefaultAzureCredentialBuilder().build();
    digitalTwinsClientSync = new DigitalTwinsClientBuilder()
      .credential(tokenCredential)
      .endpoint(dturl)
      .buildClient();
    digitalTwinsClientAsync = new DigitalTwinsClientBuilder()
      .credential(tokenCredential)
      .endpoint(dturl)
      .buildAsyncClient();
  }
  
  @Override
  @Transactional
  public void run(String... args) throws Exception {
    
    log.info("Init Twin - Start");
    
    digitalTwinsClientSync.listModels().forEach(digitalTwinsModelData -> {
      log.debug("My DT {}", digitalTwinsModelData.getDtdlModel());
    });
    
    digitalTwinsClientAsync.listModels()
      .doOnNext(modelData -> log.debug("MODEL DATA {}", modelData))
      .doOnError(throwable -> log.debug("Error {}", throwable))
      .doOnTerminate(listModelsLatch::countDown)
      .subscribe();
    
    var dt = digitalTwinsClientSync.getDigitalTwin(digitalTwinDefaultId, Map.class);
    
    dt.forEach((key, value) -> {
      if (key.equals("$metadata")) {
        Map<String, Object> metadata = (Map<String, Object>) value;
        metadata.forEach((key1, value1) -> {
          log.debug("metadata KEY {} - VALUE {}", key1, value1);
        });
      } else {
        log.debug("KEY {} - VALUE {}", key, value);
      }
    });
    
    log.debug("La temperatura del sensore {} e' {} gradi", dt.get("$dtId"), dt.get("Temperature"));
    
    
    log.info("Init Twin - End");
  }
}
