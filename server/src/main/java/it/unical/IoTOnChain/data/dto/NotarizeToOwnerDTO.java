package it.unical.IoTOnChain.data.dto;

import it.unical.IoTOnChain.data.model.ChainTransaction;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Document;
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
public class NotarizeToOwnerDTO implements Serializable {
  private String notarizedAt;
  private String data;
  private List<ChainTransactionToOwnerDTO> txTransactionList;
}
