package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Transport;

import java.util.List;
import java.util.Map;

public interface TransportService {
  Transport getOne(String id);
  
  List<Transport> getOneByBatchID(String batchid);
  
  Map<String, Object> getSensorsDataByTransportId(String transportId);
  
  Transport createOne(String batchId, String location, String companyFrom, String companyTo);
  
  List<Transport> getAllByCompany(Company company);
  
  void updateAllTransportDataFromDTHUb() throws InterruptedException;
}
