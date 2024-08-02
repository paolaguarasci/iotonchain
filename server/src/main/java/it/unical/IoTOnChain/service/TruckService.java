package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.Truck;

public interface TruckService {
  Truck getOneById(String id);
  
  Truck getFirstAvailableTruck();
  
  Truck createOne(String batchId);
}
