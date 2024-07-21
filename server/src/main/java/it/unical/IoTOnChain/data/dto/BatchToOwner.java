package it.unical.IoTOnChain.data.dto;

import it.unical.IoTOnChain.data.model.Batch;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
public class BatchToOwner implements Serializable {
  private String id;
  private String batchId;
  // private String name;
  private int quantity;
  private String unity;
  private boolean isFinal;
  private String productionDate;
  private String companyOwnerID;
  private String companyProducerID;
  private ProductTypeToOwner productType;
  private Batch.ProcessProductType processType;
//  private Location productionLocation;
//  private Set<Batch> rawMaterialList = new HashSet<>();

}
