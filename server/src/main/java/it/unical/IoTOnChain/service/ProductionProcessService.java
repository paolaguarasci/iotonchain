package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.ProductionProcess;
import it.unical.IoTOnChain.data.model.ProductionStep;

import java.util.List;
import java.util.Map;

public interface ProductionProcessService {
  ProductionProcess createOne(String name, List<ProductionStep> steps);
  
  List<ProductionStep> getProcessStepsByIdList(List<Map<String, String>> steps);
}
