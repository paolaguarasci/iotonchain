package it.unical.IoTOnChain.repository;

import it.unical.IoTOnChain.data.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, UUID> {
}
