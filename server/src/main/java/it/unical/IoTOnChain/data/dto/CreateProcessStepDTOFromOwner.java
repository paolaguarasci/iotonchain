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
public class CreateProcessStepDTOFromOwner implements Serializable {
  private int order;
  private String name;
  private String description;
}
