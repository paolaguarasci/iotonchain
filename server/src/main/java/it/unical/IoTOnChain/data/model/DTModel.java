package it.unical.IoTOnChain.data.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
public class DTModel implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  @JdbcTypeCode(SqlTypes.UUID)
  private UUID id;
  private String name;
  private String fileName;
  private String modelId;
  private String lastUpdateOnDTHUB;
  private String model;
}
