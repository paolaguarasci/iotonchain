package it.unical.IoTOnChain.repository;

import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.ProductionStepBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductionStepBatchRepository extends JpaRepository<ProductionStepBatch, UUID> {
  
  
  // FIXME non funziona!
  @Query(value = "select b from ProductionStep psb cross join ProductionProcessBatch ppb  cross join Batch b where psb.id=:sid")
  Optional<Batch> findBatchFromPPSId(@Param("sid") UUID step_id);
}
