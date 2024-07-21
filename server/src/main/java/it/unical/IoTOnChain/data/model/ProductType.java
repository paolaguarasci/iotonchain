package it.unical.IoTOnChain.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class ProductType implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  
  private String name;
  private String unity;
  
  @Enumerated(EnumType.STRING)
  private ProcessingState state;
  
  @ManyToOne
  @ToString.Exclude
  private Company company;
  
  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  private Recipe recipe;
  
  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ProductType that)) return false;
    
    return getId().equals(that.getId());
  }
  
  @Override
  public int hashCode() {
    return getId().hashCode();
  }
}
