package it.unical.IoTOnChain.repository;

import it.unical.IoTOnChain.data.model.ProductionStepBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductionStepBatchRepository extends JpaRepository<ProductionStepBatch, UUID> {
}
