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
public class Notarize implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String hash; // hash dei dati da notarizzare
  private LocalDateTime notarizedAt;
  
  // sono mutualmente esclusivi
  @OneToOne
  private Document document;
  private String data;
  
  @ManyToOne
  private Company company;
  
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<ChainTransaction> txTransactionList = new ArrayList<>();
}
