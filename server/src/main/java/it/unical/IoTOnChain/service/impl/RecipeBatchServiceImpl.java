package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.*;
import it.unical.IoTOnChain.repository.BatchRepository;
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
  private final BatchRepository batchRepository;
  
  
  @Override
  public List<RecipeRowBatch> getRecipeRowsByIdList(List<String> ingredients) {
    return recipeRowBatchRepository.findAllById(ingredients.stream().map(UUID::fromString).toList());
  }
  
  @Override
  public RecipeBatch createOneByCloneAndMaterialize(Company company, String s, int quantity, List<RecipeRow> rowFromDb) {
    
    List<RecipeRowBatch> recipeRows = new ArrayList<>();
    
    for (RecipeRow recipeRow : rowFromDb) {
      recipeRows.add(RecipeRowBatch.builder()
        .unity(recipeRow.getUnity())
        .quantity(recipeRow.getQuantity() / 100 * quantity) // TODO esce sempre 0, sistemare!
        .product(batchRepository.findAllByCompanyOwnerAndProductType(company, recipeRow.getProduct()).getFirst()) // FIXME E' una semplificazione!
        .build());
    }
    return recipeBatchRepository.saveAndFlush(RecipeBatch.builder().note(s).recipeRow(recipeRows).build());
  }
  
  private Batch selectBatch(ProductType product, Long quantity) {
    // TODO ASP Per la selezione del lotto da utilizzare
    return null;
  }
}
