package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.MyDT;
import it.unical.IoTOnChain.data.model.Transport;
import it.unical.IoTOnChain.data.model.Truck;
import it.unical.IoTOnChain.repository.TransportRepository;
import it.unical.IoTOnChain.repository.TruckRepository;
import it.unical.IoTOnChain.service.DtService;
import it.unical.IoTOnChain.service.TransportService;
import it.unical.IoTOnChain.service.TruckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransportServiceImpl implements TransportService {
  private final TransportRepository transportRepository;
  private final TruckService truckService;
  private final DtService dtService;
  private final TruckRepository truckRepository;
  
  @Override
  public Transport getOne(String id) {
    return transportRepository.findById(UUID.fromString(id)).orElse(null);
  }
  
  @Override
  public List<Transport> getOneByBatchID(String batchid) {
    return transportRepository.findAllByBatchIdOrderByDateStartDesc(batchid);
  }
  
  @Override
  public Map<String, Object> getSensorsDataByTransportId(String transportId) {
    Optional<Transport> transport = transportRepository.findById(UUID.fromString(transportId));
    if (transport.isPresent()) {
      Optional<Truck> truckOptional = Optional.ofNullable(truckService.getOneById(transport.get().getTruckId()));
      if (truckOptional.isPresent()) {
        Truck truck = truckOptional.get();
        truck.getSensor().getDtid();
      }
      
    }
    return null;
  }
  
  @Override
  public Transport createOne(String batchId, String location, String companyFrom, String companyTo) {
    Truck truck = truckService.getFirstAvailableTruck();
    if (truck == null) {
      // TODO crearlo eventualmente?
      return null;
    }
    
    Transport transport = Transport.builder()
      .truckId(truck.getId().toString())
      .batchId(batchId)
      .location(location)
      .dateStart(String.valueOf(LocalDateTime.now()))
      // .dateEnd(String.valueOf(LocalDateTime.now().plusDays(1)))
      .companyFrom(companyFrom)
      .companyTo(companyTo)
      .build();
    return transportRepository.save(transport);
  }
  
  @Override
  public List<Transport> getAllByCompany(Company company) {
    return transportRepository.findAllByCompanyFromOrCompanyToOrderByDateStartDesc(company.getName(), company.getName());
  }
  
  @Override
  @Async
  public void updateAllTransportDataFromDTHUb() throws InterruptedException {
    log.debug("Inizio updateAllTransportDataFromDTHUb");
    
    List<Transport> transports = transportRepository.findAll();
    List<UUID> trucksId = new ArrayList<>();
    transports.stream().filter(el -> el.getTruckId() != null).forEach(el -> trucksId.add(UUID.fromString(el.getTruckId())));
    List<Truck> trucks = truckRepository.findAllById(trucksId);
    
    List<MyDT> sensors = new ArrayList<>();
    trucks.forEach(el -> sensors.add(el.getSensor()));
    trucks.forEach(el -> el.setLastSensorsUpdate(LocalDateTime.now()));
    
    log.debug("Sto aggiornando {} sensori ", sensors);
    dtService.updateSensors(sensors);
    log.debug("Ho aggiornato {} sensori ", sensors.size());
    
    truckRepository.saveAll(trucks);
    log.debug("Fine updateAllTransportDataFromDTHUb");
  }
}
