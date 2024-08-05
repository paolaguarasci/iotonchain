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
public class SensorsLog extends Auditable<String> implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String sensorId;
  private String property;
  private String value;
  
  @ManyToOne
  private Notarize notarize;
}
