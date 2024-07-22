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
public class CreateRecipeRowDTOFromOwner implements Serializable {
  private String unity;
  private Long quantity;
  private String note;
  private ProductTypeToOwner product;
}
