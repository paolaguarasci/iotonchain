package it.unical.IoTOnChain.data.model;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Getter
@Setter
public class ProductionProcess implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String note;
  
  @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @Builder.Default
  private Set<ProductionStep> steps = new HashSet<>();
}
