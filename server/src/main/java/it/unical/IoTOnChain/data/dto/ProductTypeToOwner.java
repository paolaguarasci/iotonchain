package it.unical.IoTOnChain.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
public class ProductTypeToOwner implements Serializable {
  private String id;
  private String name;
  private String unity;
  private String state;
  private RecipeToOwner recipe;
  private PPToOwner productionProcess;
}
