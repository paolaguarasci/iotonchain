package it.unical.IoTOnChain.data.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
public class CreateBatchDTOFromOwner implements Serializable {
  private String batchId;
  private String description;
  private int quantity;
  private String unity;
  private List<String> documents;
  private List<String> ingredients;
  // private List<Map<String, String>> ingredients;
  private List<Map<String, String>> productionSteps;
  private String productTypeID;
  
  // private Batch.ProcessProductType processType;
  // private boolean isFinal;
  // private LocationDTO productionLocation;
  // private Set<Batch> rawMaterialList = new HashSet<>();
}
