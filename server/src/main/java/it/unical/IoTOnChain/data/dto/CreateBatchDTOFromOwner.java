package it.unical.IoTOnChain.data.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
public class CreateBatchDTOFromOwner implements Serializable {
  private String batchId;
  private int quantity;
  private String unity;
  // private boolean isFinal;
  private String productTypeID;
  // private Batch.ProcessProductType processType;
  // private LocationDTO productionLocation;
//  private Set<Batch> rawMaterialList = new HashSet<>();


}
