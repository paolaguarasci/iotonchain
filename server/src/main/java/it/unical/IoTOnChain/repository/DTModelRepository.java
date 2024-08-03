package it.unical.IoTOnChain.repository;

import it.unical.IoTOnChain.data.model.DTModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DTModelRepository extends JpaRepository<DTModel, UUID>, JpaSpecificationExecutor<DTModel> {
  Optional<DTModel> findByName(String key);
}
