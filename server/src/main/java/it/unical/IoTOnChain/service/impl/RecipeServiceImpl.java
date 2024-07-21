package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.ProductType;
import it.unical.IoTOnChain.data.model.Recipe;
import it.unical.IoTOnChain.data.model.RecipeRow;
import it.unical.IoTOnChain.repository.RecipeRepository;
import it.unical.IoTOnChain.repository.RecipeRowRepository;
import it.unical.IoTOnChain.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService {
  private final RecipeRepository recipeRepository;
  private final RecipeRowRepository recipeRowRepository;
  
  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
  public Recipe createOne(String name, Map<ProductType, List<String>> ingredients) {
    List<RecipeRow> recipeRows = new ArrayList<>();
    
    for (Map.Entry<ProductType, List<String>> ingredient : ingredients.entrySet()) {
      recipeRows.add(RecipeRow.builder().product(ingredient.getKey()).quantity(Long.valueOf(ingredient.getValue().get(0))).unity(ingredient.getValue().get(1)).build());
    }
    return recipeRepository.save(Recipe.builder().recipeRow(recipeRows).build());
  }
}
