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
public class NotarizeToOwnerDTO implements Serializable {
  private String id;
  private String hash;
  private String notarizedAt;
  private String data;
  private DocumentToOwnerDTO document;
  private List<ChainTransactionToOwnerDTO> txTransactionList;
}
