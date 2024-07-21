package it.unical.IoTOnChain.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
public class LocationDTO implements Serializable {
  private String latitude;
  private String longitude;
  private String name;
  private String address;
}
