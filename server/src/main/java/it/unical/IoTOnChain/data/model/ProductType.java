package it.unical.IoTOnChain.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
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
  
  @ManyToOne
  private Recipe recipe;
}
