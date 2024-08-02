package it.unical.IoTOnChain.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
public class TransportToOwnerDTO implements Serializable {
  private UUID id;
  private String truckId;
  private String batchId;
  private String dateStart;
  private String dateEnd;
  private String location;
  private String companyFrom;
  private String companyTo;
}
