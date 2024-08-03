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
public class Truck implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  
  @OneToOne(cascade = CascadeType.ALL)
  private MyDT sensor;
  
  private LocalDateTime lastSensorsUpdate;
}
