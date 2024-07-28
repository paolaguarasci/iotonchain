package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.ProductType;
import it.unical.IoTOnChain.data.model.Recipe;
import it.unical.IoTOnChain.data.model.RecipeRow;

import java.util.List;
import java.util.Map;

public interface RecipeService {
  Recipe createOne(String name, Map<ProductType, List<String>> ingredients);
  
  List<RecipeRow> getRecipeRowsByIdList(List<String> ingredients);
  
  Recipe createOneByClone(String name, List<RecipeRow> rowFromDb);
  
  Recipe createOneByCloneAndMaterialize(String note, Integer quantity, List<RecipeRow> rowFromDb);
}
