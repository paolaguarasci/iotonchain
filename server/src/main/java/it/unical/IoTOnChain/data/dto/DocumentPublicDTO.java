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
public class DocumentPublicDTO implements Serializable {
  private NotarizePublicDTO notarize;
  private String title;
  private String description;
  private String link;
}
