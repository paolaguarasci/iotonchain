package it.unical.IoTOnChain.data.dto;

import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Location;
import it.unical.IoTOnChain.data.model.ProductType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
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
