package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Truck;

import java.util.List;

public interface TruckService {
  Truck getOneById(String id);
  
  Truck getFirstAvailableTruck();
  
  Truck createOne(Company company);
  
  List<Truck> getAllByCompany(Company company);
}
