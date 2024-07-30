package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.RecipeBatch;
import it.unical.IoTOnChain.data.model.RecipeRowBatch;

import java.util.List;

public interface RecipeBatchService {
  List<RecipeRowBatch> getRecipeRowsByIdList(List<String> ingredients);
  
  RecipeBatch createOneByCloneAndMaterialize(String s, int quantity, List<RecipeRowBatch> rowFromDb);
}
