package it.unical.IoTOnChain.data.dto;

import it.unical.IoTOnChain.data.model.ProductType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
public class RecipeRowToOwner implements Serializable {
  private String id;
  private String unity;
  private Long quantity;
  private String note;
  private ProductType product;
  
}
