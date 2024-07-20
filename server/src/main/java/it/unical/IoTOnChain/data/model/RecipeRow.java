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
@Builder
@Getter
@Setter
public class RecipeRow implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String unity;
  private Long quantity;
  private String note;
  
  @ManyToOne
  private ProductType product;
}
