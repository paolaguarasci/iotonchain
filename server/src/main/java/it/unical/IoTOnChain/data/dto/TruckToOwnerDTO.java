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
public class TruckToOwnerDTO implements Serializable {
  private String id;
  private MyDTOwnerDTO sensor;
  private String lastSensorsUpdate;
}
