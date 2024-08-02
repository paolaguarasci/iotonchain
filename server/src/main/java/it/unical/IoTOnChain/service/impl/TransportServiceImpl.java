package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Transport;
import it.unical.IoTOnChain.data.model.Truck;
import it.unical.IoTOnChain.repository.TransportRepository;
import it.unical.IoTOnChain.service.DtService;
import it.unical.IoTOnChain.service.TransportService;
import it.unical.IoTOnChain.service.TruckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransportServiceImpl implements TransportService {
  private final TransportRepository transportRepository;
  private final TruckService truckService;
  private final DtService dtService;
  
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
}
