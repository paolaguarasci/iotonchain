package it.unical.IoTOnChain.data.model;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Getter
@Setter
public class Document implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  
  @OneToOne
  private Notarize notarize;
  
  private String title;
  private String description;
  private String link;
  private String path;
  
  @ManyToOne
  private Company owner;
  // TODO decidere come modellare sto documento: come file, come json... come???
}
