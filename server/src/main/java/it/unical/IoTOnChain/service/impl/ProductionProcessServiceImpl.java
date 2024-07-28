package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.ProductionProcess;
import it.unical.IoTOnChain.data.model.ProductionStep;
import it.unical.IoTOnChain.repository.ProductionProcessRepository;
import it.unical.IoTOnChain.repository.ProductionStepRepository;
import it.unical.IoTOnChain.service.ProductionProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductionProcessServiceImpl implements ProductionProcessService {
  private final ProductionProcessRepository productionProcessRepository;
  private final ProductionStepRepository productionStepRepository;
  @Override
  public ProductionProcess createOne(String name, List<ProductionStep> steps) {
    List<ProductionStep> stepsOk = steps;
    List<ProductionStep> formDB = productionStepRepository.findAllById(steps.stream().map(step -> step.getId()).toList());
    if (formDB.isEmpty()) {
      stepsOk = productionStepRepository.saveAll(steps);
    }
    Set<ProductionStep> processes = new HashSet<>();
    processes.addAll(stepsOk);
    return productionProcessRepository.save(ProductionProcess.builder()
      .note(name)
      .steps(processes)
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
  
  @Override
  public ProductionProcess createOneByClone(String note, List<ProductionStep> stepFromDb) {
    Set<ProductionStep> processSteps = new HashSet<>();
    processSteps.addAll(stepFromDb);
    return productionProcessRepository.save(ProductionProcess.builder()
      .steps(processSteps)
      .note(note)
      .build());
  }
}
