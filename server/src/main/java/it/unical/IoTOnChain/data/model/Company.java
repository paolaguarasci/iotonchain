package it.unical.IoTOnChain.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Builder
public class Company implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String name;
  
  @ManyToMany(cascade = CascadeType.ALL)
  @Builder.Default
  private List<ProductType> productTypeList = new ArrayList<>();
  
  @OneToMany(cascade = CascadeType.ALL)
  @Builder.Default
  private List<Batch> products = new ArrayList<>();

//  @OneToMany(cascade = CascadeType.ALL)
//  @Builder.Default
//  private List<CertificateDocument> certificateList = new ArrayList<>();
  
  
  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Company company)) return false;
    return getId().equals(company.getId());
  }
  
  @Override
  public int hashCode() {
    return getId().hashCode();
  }
}
