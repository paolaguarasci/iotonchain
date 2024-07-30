package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.Recipe;
import it.unical.IoTOnChain.data.model.RecipeBatch;
import it.unical.IoTOnChain.data.model.RecipeRow;
import it.unical.IoTOnChain.data.model.RecipeRowBatch;
import it.unical.IoTOnChain.repository.RecipeBatchRepository;
import it.unical.IoTOnChain.repository.RecipeRowBatchRepository;
import it.unical.IoTOnChain.service.RecipeBatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecipeBatchServiceImpl implements RecipeBatchService {
  private final RecipeBatchRepository recipeBatchRepository;
  private final RecipeRowBatchRepository recipeRowBatchRepository;
  
  
  @Override
  public List<RecipeRowBatch> getRecipeRowsByIdList(List<String> ingredients) {
    return recipeRowBatchRepository.findAllById(ingredients.stream().map(UUID::fromString).toList());
  }
  
  @Override
  public RecipeBatch createOneByCloneAndMaterialize(String s, int quantity, List<RecipeRowBatch> rowFromDb) {
    
    List<RecipeRowBatch> recipeRows = new ArrayList<>();
    
    for (RecipeRowBatch recipeRow : rowFromDb) {
      recipeRows.add(RecipeRowBatch.builder()
        .unity(recipeRow.getUnity())
        .quantity(recipeRow.getQuantity() / 100 * quantity) // TODO esce sempre 0, sistemare!
        .product(recipeRow.getProduct())
        .build());
    }
    return recipeBatchRepository.save(RecipeBatch.builder().note(s).recipeRow(recipeRows).build());
    
  }
  
}
