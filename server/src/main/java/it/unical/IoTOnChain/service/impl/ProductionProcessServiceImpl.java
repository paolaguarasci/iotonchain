package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.ProductionProcess;
import it.unical.IoTOnChain.data.model.ProductionStep;
import it.unical.IoTOnChain.repository.ProductionProcessRepository;
import it.unical.IoTOnChain.service.ProductionProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductionProcessServiceImpl implements ProductionProcessService {
  private final ProductionProcessRepository productionProcessRepository;
  
  @Override
  public ProductionProcess createOne(String name, List<ProductionStep> steps) {
    return productionProcessRepository.save(ProductionProcess.builder()
      .note(name)
      .steps(steps)
      .build());
  }
}
