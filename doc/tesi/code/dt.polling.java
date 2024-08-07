// PollingConfig.java 

@Component
@Slf4j
@RequiredArgsConstructor
public class PollingConfig implements Serializable {
  private final TransportService transportService;
  
  @Async
  @Scheduled(fixedDelayString = "${app.dt.interval-update-ms}", initialDelayString = "${app.dt.initial-update-ms}")
  public void poll() throws InterruptedException {
    log.info("Polling from DT Hub...");
    transportService.updateAllTransportDataFromDTHUb();
  }
}




// TransportServiceImpl.java 

@Override
@Async
public void updateAllTransportDataFromDTHUb() throws InterruptedException {
  List<Transport> transports = transportRepository.findAll();
  List<UUID> trucksId = new ArrayList<>();
  transports.stream().filter(el -> el.getTruckId() != null).forEach(el -> trucksId.add(UUID.fromString(el.getTruckId())));
  List<Truck> trucks = truckRepository.findAllById(trucksId);
  List<MyDT> sensors = new ArrayList<>();
  trucks.forEach(el -> {
    el.setLastSensorsUpdate(LocalDateTime.now());
    sensors.add(el.getSensor());
  });
  dtService.updateSensors(sensors);
  sensors.forEach(el -> el.setLastUpdated(LocalDateTime.now()));
  myDTRepository.saveAll(sensors);
  truckRepository.saveAll(trucks);
}