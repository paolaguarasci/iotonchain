package it.unical.IoTOnChain.repository;

import it.unical.IoTOnChain.data.model.RecipeRow;
import it.unical.IoTOnChain.data.model.RecipeRowBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecipeRowBatchRepository extends JpaRepository<RecipeRowBatch, UUID> {
}
