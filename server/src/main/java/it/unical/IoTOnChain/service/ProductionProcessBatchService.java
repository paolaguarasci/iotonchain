package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.ProductionProcessBatch;
import it.unical.IoTOnChain.data.model.ProductionStepBatch;

import java.util.List;
import java.util.Map;

public interface ProductionProcessBatchService {
  List<ProductionStepBatch> getProcessStepsByIdList(List<Map<String, String>> steps);
  
  ProductionProcessBatch createOneByClone(String s, List<ProductionStepBatch> stepFromDb);
}
