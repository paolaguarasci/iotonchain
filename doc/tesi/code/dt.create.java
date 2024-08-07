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