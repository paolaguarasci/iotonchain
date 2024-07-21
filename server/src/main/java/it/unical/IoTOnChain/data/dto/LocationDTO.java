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
public class LocationDTO implements Serializable {
  private String latitude;
  private String longitude;
  private String name;
  private String address;
}
