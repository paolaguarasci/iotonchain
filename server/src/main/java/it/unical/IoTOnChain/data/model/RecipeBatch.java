package it.unical.IoTOnChain.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
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
public class RecipeBatch implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String note;
  
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @Builder.Default
  private List<RecipeRowBatch> recipeRow = new ArrayList<>();
  
  
}
