package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.ProductionProcessBatch;
import it.unical.IoTOnChain.data.model.ProductionStep;
import it.unical.IoTOnChain.data.model.ProductionStepBatch;
import it.unical.IoTOnChain.repository.ProductionProcessBatchRepository;
import it.unical.IoTOnChain.repository.ProductionStepBatchRepository;
import it.unical.IoTOnChain.service.ProductionProcessBatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
  public ProductionProcessBatch createOneByClone(String s, List<ProductionStep> stepFromDb) {
    List<ProductionStepBatch> processSteps = new ArrayList<>();
    
    for (int i = 0; i < stepFromDb.size(); i++) {
      processSteps.add(ProductionStepBatch.builder()
        .position(i)
        .date(stepFromDb.get(i).getDate())
        .description(stepFromDb.get(i).getDescription())
        .name(stepFromDb.get(i).getName())
        .build());
    }
    
    return productionProcessBatchRepository.saveAndFlush(ProductionProcessBatch.builder()
      .steps(processSteps)
      .note(s)
      .build());
  }
  
  @Override
  public ProductionStepBatch getOneById(String stepId) {
    return productionStepBatchRepository.findById(UUID.fromString(stepId)).orElseThrow();
  }
  
  @Override
  public Batch getBatchByPPSId(String stepId) {
    return productionStepBatchRepository.findBatchFromPPSId(UUID.fromString(stepId)).orElseThrow();
  }
}
