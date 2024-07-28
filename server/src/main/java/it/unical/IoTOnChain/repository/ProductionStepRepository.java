package it.unical.IoTOnChain.repository;

import it.unical.IoTOnChain.data.model.ProductionStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductionStepRepository extends JpaRepository<ProductionStep, UUID> {
}
