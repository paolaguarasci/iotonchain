  @Override
  public Map<String, Object> getSensorData(String sensorId, List<String> props) {
    Map<String, Object> sensorData = new HashMap<>();
    
    var dt = digitalTwinsClientSync.getDigitalTwin(sensorId, Map.class);       
    
    dt.forEach((key, value) -> {
      if (props.contains(key)) {
        sensorData.put((String) key, value);
      }
    });
    
    return sensorData;
  }

@Override
  @Async
  public void updateSensors(List<MyDT> sensors) {
    sensors.forEach(sensor -> {      
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