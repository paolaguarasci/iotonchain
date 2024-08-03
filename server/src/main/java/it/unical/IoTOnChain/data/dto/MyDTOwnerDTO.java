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
public class MyDTOwnerDTO implements Serializable {
  private String id;
  private String dtid;
  
  private String prop1;
  private String prop2;
  private String prop3;
  
  private String val1;
  private String val2;
  private String val3;
  
  private String lastUpdated;
}
