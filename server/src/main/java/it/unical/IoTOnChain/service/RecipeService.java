package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.ProductType;
import it.unical.IoTOnChain.data.model.Recipe;

import java.util.List;
import java.util.Map;

public interface RecipeService {
  Recipe createOne(String name, Map<ProductType, List<String>> ingredients);
}
