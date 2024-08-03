package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.ProductionProcessBatch;
import it.unical.IoTOnChain.data.model.ProductionStep;
import it.unical.IoTOnChain.data.model.ProductionStepBatch;

import java.util.List;
import java.util.Map;

public interface ProductionProcessBatchService {
  List<ProductionStepBatch> getProcessStepsByIdList(List<Map<String, String>> steps);
  
  ProductionProcessBatch createOneByClone(String s, List<ProductionStep> stepFromDb);
  
  ProductionStepBatch getOneById(String stepId);
  
  Batch getBatchByPPSId(String stepId);
}
