package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.RecipeBatch;
import it.unical.IoTOnChain.data.model.RecipeRow;
import it.unical.IoTOnChain.data.model.RecipeRowBatch;

import java.util.List;

public interface RecipeBatchService {
  List<RecipeRowBatch> getRecipeRowsByIdList(List<String> ingredients);
  
  RecipeBatch createOneByCloneAndMaterialize(Company company, String s, int quantity, List<RecipeRow> rowFromDb);
}
