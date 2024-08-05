package it.unical.IoTOnChain.config;

import com.azure.core.credential.TokenCredential;
import com.azure.core.http.rest.PagedIterable;
import com.azure.digitaltwins.core.BasicDigitalTwin;
import com.azure.digitaltwins.core.DigitalTwinsAsyncClient;
import com.azure.digitaltwins.core.DigitalTwinsClient;
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder;
import com.azure.digitaltwins.core.models.DigitalTwinsModelData;
import com.azure.identity.DefaultAzureCredentialBuilder;
import it.unical.IoTOnChain.data.model.DTModel;
import it.unical.IoTOnChain.repository.DTModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;


@Component
@Slf4j
@ConditionalOnProperty(name = "app.init.dt", havingValue = "true")
@Order(1)
public class InitTwin implements CommandLineRunner {
  private final CountDownLatch listModelsLatch = new CountDownLatch(1);
  private final DigitalTwinsClient digitalTwinsClientSync;
  private final DigitalTwinsAsyncClient digitalTwinsClientAsync;
  
  private final String digitalTwinDefaultId;
  private final String digitalTwinDefaultProperty;
  private final DTModelRepository dtModelRepository;
  
  
  public InitTwin(@Value("${app.dt.dturl}") String dturl, @Value("${app.dt.dtiddefault}") String digitalTwinDefaultId, @Value("${app.dt.dtpropertydefault}") String digitalTwinDefaultProperty, DTModelRepository dtModelRepository) {
    this.digitalTwinDefaultId = digitalTwinDefaultId;
    this.digitalTwinDefaultProperty = digitalTwinDefaultProperty;
    this.dtModelRepository = dtModelRepository;
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
      .doOnNext(modelData -> log.debug("MODEL DATA {} {}", modelData.getDtdlModel(), modelData.getModelId()))
      .doOnError(throwable -> log.debug("Error {}", throwable))
      .doOnTerminate(listModelsLatch::countDown)
      .subscribe();
    
    var dt = digitalTwinsClientSync.getDigitalTwin(digitalTwinDefaultId, Map.class);
    
    
    Map<String, String> models = filesAsAStrings("/home/paola/workspace/iotOnChain/server/src/main/resources/dt_model");
    
    log.debug("Ci sono {} modelli", models.keySet().size());
    String query = "SELECT * FROM DIGITALTWINS T WHERE CONTAINS(T.$dtId, 'truck')";
    PagedIterable<BasicDigitalTwin> deserializedResponse = digitalTwinsClientSync.query(query, BasicDigitalTwin.class);
    log.debug("Ci sono {} truck twin", deserializedResponse.stream().count());
    for (BasicDigitalTwin digitalTwin : deserializedResponse) {
      System.out.println("Delete digital twin with Id: " + digitalTwin.getId());
      digitalTwinsClientSync.deleteDigitalTwin(digitalTwin.getId());
    }
    
    models.keySet().forEach(key -> {
      Optional<DTModel> model = dtModelRepository.findByName(key.replace(".json", "").toLowerCase(Locale.ROOT));
      if (model.isEmpty()) {
        digitalTwinsClientSync.deleteModel("dtmi:iotchain:DigitalTwins:Truck;1");
        Iterable<DigitalTwinsModelData> modelList = digitalTwinsClientSync.createModels(List.of(models.get(key)));
        DigitalTwinsModelData modelData = modelList.iterator().next();
        if (modelData == null) {
          modelData = digitalTwinsClientSync.getModel(models.get(key));
          log.debug("DATAAAAAAAAAAA Model {}", modelData);
        }
        log.debug("Ho creato il modello {}", modelData.getModelId());
        dtModelRepository.save(DTModel.builder()
          .name(key.replace(".json", "").toLowerCase(Locale.ROOT))
          .fileName(key)
          .lastUpdateOnDTHUB(LocalDateTime.now().toString())
          .modelId(modelData.getModelId())
          // .model(models.get(key))
          .build());
      }
    });
    
    
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

//    log.debug("La temperatura del sensore {} e' {} gradi", dt.get("$dtId"), dt.get("Temperature"));
    
    
    log.info("Init Twin - End");
  }
  
  
  private Map<String, String> filesAsAStrings(String dir) {
    Map<String, String> contents = new HashMap<>();
    log.debug("Loading files from {}", dir);
    Stream.of(new File(dir).listFiles())
      .filter(file -> !file.isDirectory())
      .forEach(el -> {
        String fileName = el.getName();
        try {
          File file = ResourceUtils.getFile(dir + "/" + fileName);
          String content = String.join(" ", (Files.readAllLines(file.toPath())));
          contents.put(fileName, content);
        } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      });
    return contents;
  }
}
