package it.unical.IoTOnChain.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ChainTransactionToOwnerDTO implements Serializable {
  private String id;
  private String txId;
}
