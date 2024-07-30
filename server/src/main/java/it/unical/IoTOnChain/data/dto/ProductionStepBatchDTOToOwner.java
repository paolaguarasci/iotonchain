package it.unical.IoTOnChain.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
public class ProductionStepBatchDTOToOwner implements Serializable {
  private String id;
  private String date;
  private int position;
  private String name;
  private NotarizeToOwnerDTO notarization;
  private String description;
}
