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
public class TransferToOwnerDTO implements Serializable {
  private String id;
  private String type;
  private String status;
  private String oldBatchID;
  private String newBatchID;
  private int quantity;
  private String unity;
  private String companySenderID;
  private String companySenderUsername;
  private String companyRecipientID;
  private String companyRecipientUsername;
  private String transferDateStart;
  private String lastUpdate;
  private List<ChainTransactionToOwnerDTO> txTransactionList;
}
