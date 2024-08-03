package it.unical.IoTOnChain.service.impl;

import com.azure.core.credential.TokenCredential;
import com.azure.digitaltwins.core.BasicDigitalTwin;
import com.azure.digitaltwins.core.BasicDigitalTwinMetadata;
import com.azure.digitaltwins.core.DigitalTwinsClient;
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import it.unical.IoTOnChain.data.model.DTModel;
import it.unical.IoTOnChain.data.model.MyDT;
import it.unical.IoTOnChain.data.model.SensorsLog;
import it.unical.IoTOnChain.repository.DTModelRepository;
import it.unical.IoTOnChain.repository.SensorsLogRepository;
import it.unical.IoTOnChain.service.DtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
  
  public DtServiceImpl(@Value("${app.dt.dturl}") String dturl, @Value("${app.dt.dtiddefault}") String digitalTwinDefaultId, @Value("${app.dt.dtpropertydefault}") String digitalTwinDefaultProperty, SensorsLogRepository sensorsLogRepository,
                       DTModelRepository dTModelRepository) {
    this.digitalTwinDefaultId = digitalTwinDefaultId;
    this.digitalTwinDefaultProperty = digitalTwinDefaultProperty;
    this.sensorsLogRepository = sensorsLogRepository;
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
  }
  
  @Override
  public Map<String, Object> getSensorData(String sensorId, List<String> props) {
    Map<String, Object> sensorData = new HashMap<>();
    var dt = digitalTwinsClientSync.getDigitalTwin(digitalTwinDefaultId, Map.class);
    
    dt.forEach((key, value) -> {
      if (props.contains(key)) {
        sensorData.put((String) key, value);
      }
    });
    
    
    return sensorData;
  }
  
  
  @Override
  public String createOneSensor(String sensorName, String dtName) {
    Optional<DTModel> model = dTModelRepository.findByName(sensorName);
    if (model.isPresent()) {
      BasicDigitalTwin basicTwin = new BasicDigitalTwin(dtName).setMetadata(new BasicDigitalTwinMetadata().setModelId(model.get().getModelId()));
      BasicDigitalTwin basicTwinResponse = digitalTwinsClientSync.createOrReplaceDigitalTwin(dtName, basicTwin, BasicDigitalTwin.class);
      return basicTwinResponse.getId();
    }
    return null;
  }
  
  @Override
  public String destroyOneSensor(String sensorId) {
    return null;
  }
  
  @Override
  public void updateSensors(List<MyDT> sensors) {
    sensors.forEach(sensor -> {
      log.debug("Sto aggiornando il sensore {}, sulle proprieta' {} {} {}", sensor.getDtid(), sensor.getProp1(), sensor.getProp2(), sensor.getProp3());
      
      
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
      
      if (res.get(sensor.getProp1()) != null) {
        sensor.setVal1(String.valueOf(res.get(sensor.getProp1())));
        sensorsLogRepository.save(SensorsLog.builder()
          .sensorId(sensor.getDtid())
          .property(sensor.getProp1())
          .value(sensor.getVal1())
          .build());
      }
      
      if (res.get(sensor.getProp2()) != null) {
        sensor.setVal2(String.valueOf(res.get(sensor.getProp2())));
        sensorsLogRepository.save(SensorsLog.builder()
          .sensorId(sensor.getDtid())
          .property(sensor.getProp2())
          .value(sensor.getVal2())
          .build());
      }
      
      if (res.get(sensor.getProp3()) != null) {
        sensor.setVal3(String.valueOf(res.get(sensor.getProp3())));
        sensorsLogRepository.save(SensorsLog.builder()
          .sensorId(sensor.getDtid())
          .property(sensor.getProp3())
          .value(sensor.getVal3())
          .build());
      }
      
      
      // TODO Notarize Sensor Data!
      
    });
  }
  
}
