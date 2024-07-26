package it.unical.IoTOnChain.data.dto;


import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Notarize;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
public class DocumentToOwnerDTO implements Serializable {
  private String id;
  private NotarizeToOwnerDTO notarize;
  private String title;
  private String description;
  private String link;
  private String path;
}
