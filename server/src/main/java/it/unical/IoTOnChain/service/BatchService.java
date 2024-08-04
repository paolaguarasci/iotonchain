package it.unical.IoTOnChain.service;


import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.ProductType;
import it.unical.IoTOnChain.exception.MoveIsNotPossibleException;
import it.unical.IoTOnChain.exception.NoEnoughRawMaterialsException;

import java.util.List;
import java.util.Map;

public interface BatchService {
  List<Batch> getAllProductByCompanyLogged(String companyLogged);
  
  Batch produce(Company company, ProductType type, int quantity, String batchId, List<String> documents, List<String> ingredients, List<Map<String, String>> steps) throws NoEnoughRawMaterialsException;
  
  Batch move(Company owner, Batch batch, Company company, int quantity, boolean is2step) throws MoveIsNotPossibleException, NoEnoughRawMaterialsException;
  
  Batch getOneByBatchIdAndCompany(Company companyOwner, String batchID);
  
  Batch produceByMovement(Company company, ProductType productType, int quantity, String batchId, Batch old);
  
  Map<String, Object> trackInfo(Company companyLogged, String batchId);
  
  void block(Company company, String batchID, int quantity);
  
  void refound(Company company, String batchID, int quantity);
}
