package it.unical.IoTOnChain.service.impl;

import com.azure.core.credential.TokenCredential;
import com.azure.digitaltwins.core.BasicDigitalTwin;
import com.azure.digitaltwins.core.BasicDigitalTwinMetadata;
import com.azure.digitaltwins.core.DigitalTwinsClient;
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unical.IoTOnChain.data.model.DTModel;
import it.unical.IoTOnChain.data.model.MyDT;
import it.unical.IoTOnChain.data.model.SensorsLog;
import it.unical.IoTOnChain.repository.DTModelRepository;
import it.unical.IoTOnChain.repository.MyDTRepository;
import it.unical.IoTOnChain.repository.SensorsLogRepository;
import it.unical.IoTOnChain.service.DtService;
import it.unical.IoTOnChain.service.NotarizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
@Transactional
public class DtServiceImpl implements DtService {
  
  
  private final DTModelRepository dTModelRepository;
  private final CountDownLatch listModelsLatch = new CountDownLatch(1);
  private final DigitalTwinsClient digitalTwinsClientSync;
  //private final DigitalTwinsAsyncClient digitalTwinsClientAsync;
  private final String digitalTwinDefaultProperty;
  private final String digitalTwinDefaultId;
  private final SensorsLogRepository sensorsLogRepository;
  private final NotarizeService notarizeService;
  private final ObjectMapper objectMapper;
  private final MyDTRepository myDTRepository;
  
  public DtServiceImpl(@Value("${app.dt.dturl}") String dturl, @Value("${app.dt.dtiddefault}") String digitalTwinDefaultId, @Value("${app.dt.dtpropertydefault}") String digitalTwinDefaultProperty, SensorsLogRepository sensorsLogRepository,
                       DTModelRepository dTModelRepository, NotarizeService notarizeService, ObjectMapper objectMapper, MyDTRepository myDTRepository) {
    this.digitalTwinDefaultId = digitalTwinDefaultId;
    this.digitalTwinDefaultProperty = digitalTwinDefaultProperty;
    this.sensorsLogRepository = sensorsLogRepository;
    this.notarizeService = notarizeService;
    this.objectMapper = objectMapper;
    TokenCredential tokenCredential = new DefaultAzureCredentialBuilder().build();
    digitalTwinsClientSync = new DigitalTwinsClientBuilder()
      .credential(tokenCredential)
      .endpoint(dturl)
      .buildClient();
//    digitalTwinsClientAsync = new DigitalTwinsClientBuilder()
//      .credential(tokenCredential)
//      .endpoint(dturl)
//      .buildAsyncClient();
    this.dTModelRepository = dTModelRepository;
    this.myDTRepository = myDTRepository;
  }
  
  @Override
  public Map<String, Object> getSensorData(String sensorId, List<String> props) {
    Map<String, Object> sensorData = new HashMap<>();
    var dt = digitalTwinsClientSync.getDigitalTwin(sensorId, Map.class);
    
    log.debug("Row DATA SENSOR {} - {}", sensorId, dt.toString());
    
    dt.forEach((key, value) -> {
      if (props.contains(key)) {
        sensorData.put((String) key, value);
      }
    });
    return sensorData;
  }
  
  
  @Override
  public String createOneSensor(String sensorName, String dtName) {
    DTModel model = dTModelRepository.findByName(sensorName).orElseThrow();
    BasicDigitalTwin basicTwin = new BasicDigitalTwin(dtName)
      .setMetadata(new BasicDigitalTwinMetadata()
        .setModelId(model.getModelId()))
      .addToContents("Temperature", 0)
      .addToContents("Location", "none");
    BasicDigitalTwin basicTwinResponse = digitalTwinsClientSync.createOrReplaceDigitalTwin(dtName, basicTwin, BasicDigitalTwin.class);
    log.debug("Ho creato il sensore {} ", basicTwinResponse.getId());
    return basicTwinResponse.getId() != null && !basicTwinResponse.getId().isEmpty() ? basicTwinResponse.getId() : dtName;
  }
  
  @Override
  public String destroyOneSensor(String sensorId) {
    return null;
  }
  
  @Override
  @Async
  public void updateSensors(List<MyDT> sensors) {
    sensors.forEach(sensor -> {
      log.debug("Sto aggiornando il sensore {}, sulle proprietà {} {} {}", sensor.getDtid(), sensor.getProp1(), sensor.getProp2(), sensor.getProp3());
      
      
      List<String> props = new ArrayList<>();
      
      if (sensor.getProp1() != null) {
        props.add(sensor.getProp1());
      }
      if (sensor.getProp2() != null) {
        props.add(sensor.getProp2());
      }
      if (sensor.getProp3() != null) {
        props.add(sensor.getProp3());
      }
      
      Map<String, Object> res = this.getSensorData(sensor.getDtid(), props);
      List<SensorsLog> sensorsLogs = new ArrayList<>();
      
      if (res.get(sensor.getProp1()) != null) {
        sensor.setVal1(String.valueOf(res.get(sensor.getProp1())));
        sensorsLogs.add(SensorsLog.builder()
          .sensorId(sensor.getDtid())
          .property(sensor.getProp1())
          .value(sensor.getVal1())
          .build());
      }
      
      if (res.get(sensor.getProp2()) != null) {
        sensor.setVal2(String.valueOf(res.get(sensor.getProp2())));
        sensorsLogs.add(SensorsLog.builder()
          .sensorId(sensor.getDtid())
          .property(sensor.getProp2())
          .value(sensor.getVal2())
          .build());
      }
      
      if (res.get(sensor.getProp3()) != null) {
        sensor.setVal3(String.valueOf(res.get(sensor.getProp3())));
        sensorsLogs.add(SensorsLog.builder()
          .sensorId(sensor.getDtid())
          .property(sensor.getProp3())
          .value(sensor.getVal3())
          .build());
      }
      List<MyDT> sensorSaved = myDTRepository.saveAll(sensors);
      List<SensorsLog> saved = sensorsLogRepository.saveAll(sensorsLogs);
      try {
        if (!saved.isEmpty()) {
          notarizeService.notarize(saved);
        }
      } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (TransactionException e) {
        throw new RuntimeException(e);
      }
    });
  }
  
}
