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
public class SensorsLogOwnerDTO implements Serializable {
  private String id;
  private String sensorId;
  private String property;
  private String value;
  private NotarizeToOwnerDTO notarize;
}
