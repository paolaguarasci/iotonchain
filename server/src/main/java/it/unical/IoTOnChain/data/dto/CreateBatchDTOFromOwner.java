package it.unical.IoTOnChain.data.dto;


import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.Location;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
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
