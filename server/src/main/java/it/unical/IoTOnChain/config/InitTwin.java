package it.unical.IoTOnChain.config;

import com.azure.core.credential.TokenCredential;
import com.azure.digitaltwins.core.DigitalTwinsAsyncClient;
import com.azure.digitaltwins.core.DigitalTwinsClient;
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;


@Component
@Slf4j
@ConditionalOnProperty(name = "app.init.dt", havingValue = "true")
public class InitTwin implements CommandLineRunner {
  final CountDownLatch listModelsLatch = new CountDownLatch(1);
  
  
  @Override
  @Transactional
  public void run(String... args) throws Exception {
    
    log.info("Init Twin - Start");
    
    TokenCredential defaultAzureCredential = new DefaultAzureCredentialBuilder().build();
    
    DigitalTwinsClient client = new DigitalTwinsClientBuilder()
      .credential(defaultAzureCredential)
      .endpoint("https://dtFiliera.api.wus2.digitaltwins.azure.net")
      .buildClient();
    
    client.listModels().forEach(digitalTwinsModelData -> {
      log.debug("My DT {}", digitalTwinsModelData.getDtdlModel());
    });
    
    DigitalTwinsAsyncClient asyncClient = new DigitalTwinsClientBuilder()
      .credential(defaultAzureCredential)
      .endpoint("https://dtFiliera.api.wus2.digitaltwins.azure.net")
      .buildAsyncClient();
    
    asyncClient.listModels()
      .doOnNext(modelData -> log.debug("MODEL DATA {}", modelData))
      .doOnError(throwable -> log.debug("Error {}", throwable))
      .doOnTerminate(listModelsLatch::countDown)
      .subscribe();
    
    
    log.info("Init Twin - End");
  }
}
