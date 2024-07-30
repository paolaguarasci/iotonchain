package it.unical.IoTOnChain.repository;

import it.unical.IoTOnChain.data.model.Recipe;
import it.unical.IoTOnChain.data.model.RecipeBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecipeBatchRepository extends JpaRepository<RecipeBatch, UUID> {
}
