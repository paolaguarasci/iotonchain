package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.RecipeBatch;
import it.unical.IoTOnChain.data.model.RecipeRowBatch;

import java.util.List;
import java.util.Map;

public interface RecipeBatchService {
  List<RecipeRowBatch> getRecipeRowsByIdList(List<String> ingredients);
  
  RecipeBatch createOneByCloneAndMaterialize(Map<Batch, Integer> localBatchAndQuantity);
}
