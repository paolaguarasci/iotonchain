package it.unical.IoTOnChain.repository;

import it.unical.IoTOnChain.data.model.TrackingBatchProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrackingBatchProductRepository extends JpaRepository<TrackingBatchProduct, UUID>, JpaSpecificationExecutor<TrackingBatchProduct> {
}
