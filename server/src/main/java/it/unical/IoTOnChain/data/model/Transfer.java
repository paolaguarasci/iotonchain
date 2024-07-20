package it.unical.IoTOnChain.data.model;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Getter
@Setter
public class Transfer implements Serializable {
  
  public enum TransferType {
    ONESHOT, WITHACCEPTANCE;
  }
  
  public enum TransferStatus  implements Serializable {
    COMPLETED, INIT, PENDING, REJECTED;
  }
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private TransferType type = TransferType.ONESHOT;
  
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private TransferStatus status = TransferStatus.INIT;
  
  private String batchID;
  private int quantity;
  private String unity;
  
  private String companySenderID;
  private String companyRecipientID;
  
  private LocalDateTime transferDateStart;
  private LocalDateTime lastUpdate;
  
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<ChainTransaction> txTransactionList = new ArrayList<>();
}
