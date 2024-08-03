package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.MyDT;

import java.util.List;
import java.util.Map;

public interface DtService {
  
  Map<String, Object> getSensorData(String sensorId, List<String> props);
  
  String createOneSensor(String sensorName, String dtName);
  
  String destroyOneSensor(String sensorId);
  
  void updateSensors(List<MyDT> sensors);
}
