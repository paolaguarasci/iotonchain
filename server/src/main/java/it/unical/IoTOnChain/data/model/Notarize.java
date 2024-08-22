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
  
  @Lob
  @Column(columnDefinition="LONGTEXT")
  private String data;
  
  @ManyToOne
  @ToString.Exclude
  private Company company;
  
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<ChainTransaction> txTransactionList = new ArrayList<>();
  
  
  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Notarize notarize)) return false;
    
    return getId().equals(notarize.getId());
  }
  
  @Override
  public int hashCode() {
    return getId().hashCode();
  }
}
