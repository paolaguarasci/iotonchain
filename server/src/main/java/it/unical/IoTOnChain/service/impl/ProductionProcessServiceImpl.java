package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.ProductionProcess;
import it.unical.IoTOnChain.data.model.ProductionStep;
import it.unical.IoTOnChain.repository.ProductionProcessRepository;
import it.unical.IoTOnChain.repository.ProductionStepRepository;
import it.unical.IoTOnChain.service.ProductionProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductionProcessServiceImpl implements ProductionProcessService {
  private final ProductionProcessRepository productionProcessRepository;
  private final ProductionStepRepository productionStepRepository;
  @Override
  public ProductionProcess createOne(String name, List<ProductionStep> steps) {
    return productionProcessRepository.save(ProductionProcess.builder()
      .note(name)
      .steps(steps)
      .build());
  }
  
  @Override
  public List<ProductionStep> getProcessStepsByIdList(List<Map<String, String>> steps) {
    List<ProductionStep> processSteps = new ArrayList<>();
    steps.forEach((Map<String, String> spt) -> {
      // id, position
      ProductionStep fromDB = productionStepRepository.findById(UUID.fromString(spt.get("id"))).orElseThrow();
      processSteps.add(Integer.parseInt(spt.get("position")), fromDB);
    });
    return processSteps;
  }
}
