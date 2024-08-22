package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.RecipeBatch;
import it.unical.IoTOnChain.data.model.RecipeRowBatch;
import it.unical.IoTOnChain.repository.BatchRepository;
import it.unical.IoTOnChain.repository.RecipeBatchRepository;
import it.unical.IoTOnChain.repository.RecipeRowBatchRepository;
import it.unical.IoTOnChain.service.RecipeBatchService;
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
public class RecipeBatchServiceImpl implements RecipeBatchService {
  private final RecipeBatchRepository recipeBatchRepository;
  private final RecipeRowBatchRepository recipeRowBatchRepository;
  private final BatchRepository batchRepository;
  
  
  @Override
  public List<RecipeRowBatch> getRecipeRowsByIdList(List<String> ingredients) {
    return recipeRowBatchRepository.findAllById(ingredients.stream().map(UUID::fromString).toList());
  }
  
  @Override
  public RecipeBatch createOneByCloneAndMaterialize(Map<Batch, Integer> localBatchAndQuantity) {
    List<RecipeRowBatch> recipeRows = new ArrayList<>();
    for (Map.Entry<Batch, Integer> entry : localBatchAndQuantity.entrySet()) {
      recipeRows.add(RecipeRowBatch.builder()
        .unity(entry.getKey().getProductType().getUnity())
        .quantity(entry.getValue().longValue())
        .product(entry.getKey())
        .build());
    }
    return recipeBatchRepository.saveAndFlush(RecipeBatch.builder().note("").recipeRow(recipeRows).build());
  }
}
