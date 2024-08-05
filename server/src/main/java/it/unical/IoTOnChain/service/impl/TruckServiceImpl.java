package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.MyDT;
import it.unical.IoTOnChain.data.model.Truck;
import it.unical.IoTOnChain.repository.TruckRepository;
import it.unical.IoTOnChain.service.DtService;
import it.unical.IoTOnChain.service.TruckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TruckServiceImpl implements TruckService {
  
  
  private final TruckRepository truckRepository;
  private final DtService dtService;
  private final Random random = new Random();
  
  @Override
  public Truck getOneById(String id) {
    return truckRepository.findById(UUID.fromString(id)).orElse(null);
  }
  
  @Override
  public Truck getFirstAvailableTruck() {
    List<Truck> trucks = truckRepository.findAllAvailable();
    if (!trucks.isEmpty()) {
      return truckRepository.findAllAvailable().stream().findFirst().get();
    }
    return null;
  }
  
  @Override
  public Truck createOne(Company company) {
    MyDT dt = new MyDT();
    int indice = random.nextInt((10) + 1);
    String sensorsId = dtService.createOneSensor("truck", "truck_" + company.getName() + "_" + indice);
    log.debug("Created truck with sensor id " + sensorsId);
    dt.setDtid(sensorsId);
    dt.setProp1("Location");
    dt.setProp2("Temperature");
    return truckRepository.save(Truck.builder().company(company).sensor(dt).build());
  }
  
  @Override
  public List<Truck> getAllByCompany(Company company) {
    return truckRepository.findAllByCompanyId(company.getId());
  }
  
}
