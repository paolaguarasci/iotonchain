package it.unical.IoTOnChain.data.dto;

import it.unical.IoTOnChain.data.model.RecipeRow;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class RecipeToOwner implements Serializable {
  private String id;
  private String note;
  private List<RecipeRowToOwner> recipeRow;
}
