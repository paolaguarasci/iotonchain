package it.unical.IoTOnChain.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Builder
// @SuperBuilder
// @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// @DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Batch implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @Column(nullable = false)
  private String batchId;
  //  @Column(nullable = false)
//  private int tokenBatchProductId;
  // private String name;
  private int quantity;
  @Builder.Default
  private boolean isFinal = false;
  @Column(nullable = false)
  private LocalDateTime productionDate;
  @ManyToOne
  private Company companyOwner;
  @ManyToOne
  private Company companyProducer;
  
  @ManyToOne(fetch = FetchType.EAGER)
  private ProductType productType;
  
  @ManyToOne(fetch = FetchType.EAGER)
  private ProductionStep productionStep;
  
  @Enumerated(EnumType.STRING)
  private ProcessProductType processType;
  @ManyToOne
  private Location productionLocation;
  
//  @ManyToMany
//  @Builder.Default
//  private Set<Batch> rawMaterials = new HashSet<>();
  
  @ManyToMany
  @Builder.Default
  private Set<Document> documents = new HashSet<>();
  
  @ManyToOne
  private RecipeBatch localRecipe;
  
  @ManyToOne
  private ProductionProcessBatch localProcessProduction;

//  @ManyToMany
//  private Set<TransactionProductTrasfering> movementList = new HashSet<>();
  
  
  public enum ProcessProductType {
    Automatic, Manual, SemiAutomatic
  }
  
}
