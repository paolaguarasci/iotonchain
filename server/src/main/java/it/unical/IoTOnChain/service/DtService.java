package it.unical.IoTOnChain.service;

import java.util.List;
import java.util.Map;

public interface DtService {
  
  Map<String, String> getSensorData(String sensorId, List<String> props);
  
  String createOneSensor(String sensorId, List<String> props);
  
  String destroyOneSensor(String sensorId);
}
