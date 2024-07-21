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
public class CreateProductTypeDTOFromOwner implements Serializable {
  private String name;
  private String unity;
  private String state;
}
