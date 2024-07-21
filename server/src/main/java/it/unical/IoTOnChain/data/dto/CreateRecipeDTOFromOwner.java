package it.unical.IoTOnChain.data.dto;

import it.unical.IoTOnChain.data.model.RecipeRow;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class CreateRecipeDTOFromOwner implements Serializable {
  private String note;
  private List<CreateRecipeRowDTOFromOwner> recipeRow;
}
