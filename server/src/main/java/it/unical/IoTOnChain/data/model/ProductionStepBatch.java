package it.unical.IoTOnChain.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Getter
@Setter
public class ProductionStepBatch implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private LocalDateTime date;
  private int position;
  private String name;
  @OneToOne
  private Notarize notarize;
  private String description;
}
