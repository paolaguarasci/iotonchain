package it.unical.IoTOnChain.data.dto;

import it.unical.IoTOnChain.data.model.ChainTransaction;
import it.unical.IoTOnChain.data.model.Transfer;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
