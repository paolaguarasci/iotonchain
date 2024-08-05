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
  
  @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
  private Company company;
  
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private MyDT sensor;
  
  private LocalDateTime lastSensorsUpdate;
}
