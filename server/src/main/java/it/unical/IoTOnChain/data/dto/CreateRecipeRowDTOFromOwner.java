package it.unical.IoTOnChain.data.dto;

import it.unical.IoTOnChain.data.model.ProductType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

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
