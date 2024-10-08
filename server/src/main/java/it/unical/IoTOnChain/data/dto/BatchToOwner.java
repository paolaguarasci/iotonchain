package it.unical.IoTOnChain.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
public class BatchToOwner implements Serializable {
  private String id;
  private String batchId;
  private int quantity;
  private String unity;
  private boolean isFinal;
  private String productionDate;
  private String companyOwnerID;
  private String companyProducerID;
  private ProductTypeToOwner productType;
  private String processType;
  private LocationDTO productionLocation;
  // private Set<BatchToOwner> rawMaterials;
  private Set<DocumentToOwnerDTO> documents;
  private RecipeBatchToOwner localRecipe;
  private ProductionProcessBatchToOwner localProcessProduction;
}
