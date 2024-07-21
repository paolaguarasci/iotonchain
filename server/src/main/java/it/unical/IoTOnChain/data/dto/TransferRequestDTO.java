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
public class TransferRequestDTO implements Serializable {
  private String batchID;
  private int quantity;
  private String unity;
  private String companyRecipientID;
  private String type;
  
}
