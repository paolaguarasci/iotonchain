package it.unical.IoTOnChain.data.dto;

import it.unical.IoTOnChain.data.model.ChainTransaction;
import it.unical.IoTOnChain.data.model.Transfer;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class TransferToOwnerDTO implements Serializable {
  private String id;
  private String type;
  private String status;
  private String batchID;
  private int quantity;
  private String unity;
  private String companySenderID;
  private String companyRecipientID;
  private String transferDateStart;
  private String lastUpdate;
  private List<ChainTransactionToOwnerDTO> txTransactionList;
}
