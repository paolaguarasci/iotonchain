package it.unical.IoTOnChain.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class CreatePPDTOFromOwner implements Serializable {
  private String note;
  private List<CreateProcessStepDTOFromOwner> steps;
}
