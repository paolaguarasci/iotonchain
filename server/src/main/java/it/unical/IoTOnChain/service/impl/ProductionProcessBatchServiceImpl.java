package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.ProductionProcess;
import it.unical.IoTOnChain.data.model.ProductionProcessBatch;
import it.unical.IoTOnChain.data.model.ProductionStep;
import it.unical.IoTOnChain.data.model.ProductionStepBatch;
import it.unical.IoTOnChain.repository.ProductionProcessBatchRepository;
import it.unical.IoTOnChain.repository.ProductionStepBatchRepository;
import it.unical.IoTOnChain.repository.ProductionStepRepository;
import it.unical.IoTOnChain.service.ProductionProcessBatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductionProcessBatchServiceImpl implements ProductionProcessBatchService {
  private final ProductionProcessBatchRepository productionProcessBatchRepository;
  private final ProductionStepBatchRepository productionStepBatchRepository;
  
  @Override
  public List<ProductionStepBatch> getProcessStepsByIdList(List<Map<String, String>> steps) {
    List<ProductionStepBatch> processSteps = new ArrayList<>();
    steps.forEach((Map<String, String> spt) -> {
      // id, position
      ProductionStepBatch fromDB = productionStepBatchRepository.findById(UUID.fromString(spt.get("id"))).orElseThrow();
      processSteps.add(Integer.parseInt(spt.get("position")), fromDB);
    });
    return processSteps;
  }
  
  @Override
  public ProductionProcessBatch createOneByClone(String s, List<ProductionStepBatch> stepFromDb) {
    Set<ProductionStepBatch> processSteps = new HashSet<>();
    processSteps.addAll(stepFromDb);
    return productionProcessBatchRepository.save(ProductionProcessBatch.builder()
      .steps(processSteps)
      .note(s)
      .build());
  }
}
