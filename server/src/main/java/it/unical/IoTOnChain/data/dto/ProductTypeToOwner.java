package it.unical.IoTOnChain.data.dto;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.ProcessingState;
import it.unical.IoTOnChain.data.model.Recipe;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ProductTypeToOwner implements Serializable {
  private String id;
  private String name;
  private String unity;
  private String state;
  private RecipeToOwner recipe;
}
