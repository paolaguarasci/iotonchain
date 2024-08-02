package it.unical.IoTOnChain.service.impl;

import com.azure.core.credential.TokenCredential;
import com.azure.digitaltwins.core.DigitalTwinsClient;
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import it.unical.IoTOnChain.service.DtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
@Transactional
public class DtServiceImpl implements DtService {
  private final CountDownLatch listModelsLatch = new CountDownLatch(1);
  private final DigitalTwinsClient digitalTwinsClientSync;
  //private final DigitalTwinsAsyncClient digitalTwinsClientAsync;
  private final String digitalTwinDefaultProperty;
  private final String digitalTwinDefaultId;
  
  public DtServiceImpl(@Value("${app.dt.dturl}") String dturl, @Value("${app.dt.dtiddefault}") String digitalTwinDefaultId, @Value("${app.dt.dtpropertydefault}") String digitalTwinDefaultProperty) {
    this.digitalTwinDefaultId = digitalTwinDefaultId;
    this.digitalTwinDefaultProperty = digitalTwinDefaultProperty;
    TokenCredential tokenCredential = new DefaultAzureCredentialBuilder().build();
    digitalTwinsClientSync = new DigitalTwinsClientBuilder()
      .credential(tokenCredential)
      .endpoint(dturl)
      .buildClient();
//    digitalTwinsClientAsync = new DigitalTwinsClientBuilder()
//      .credential(tokenCredential)
//      .endpoint(dturl)
//      .buildAsyncClient();
  }
  
  @Override
  public Map<String, String> getSensorData(String sensorId, List<String> props) {
    Map<String, String> sensorData = new HashMap<>();
    var dt = digitalTwinsClientSync.getDigitalTwin(digitalTwinDefaultId, Map.class);
    
    dt.forEach((key, value) -> {
      if (props.contains(key)) {
        sensorData.put((String) key, (String) value);
      }
    });
    
    return sensorData;
  }
  
  
  @Override
  public String createOneSensor(String sensorId, List<String> props) {
    return "ciaociaio";
  }
  
  @Override
  public String destroyOneSensor(String sensorId) {
    return null;
  }
  
}
