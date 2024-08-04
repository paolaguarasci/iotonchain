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
public class CreateTransportDTO implements Serializable {
  private String batchId;
  private String location;
  private String companyFrom;
  private String companyTo;
}
