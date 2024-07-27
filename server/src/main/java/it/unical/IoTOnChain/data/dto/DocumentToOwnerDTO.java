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
public class DocumentToOwnerDTO implements Serializable {
  private String id;
  private NotarizeToOwnerDTO notarize;
  private String title;
  private String description;
  private String link;
  private String path;
}

// tirare fuori i linked batch ai documenti
//
