package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.ProductionProcess;
import it.unical.IoTOnChain.data.model.ProductionStep;

import java.util.List;

public interface ProductionProcessService {
  ProductionProcess createOne(String name, List<ProductionStep> steps);
}
