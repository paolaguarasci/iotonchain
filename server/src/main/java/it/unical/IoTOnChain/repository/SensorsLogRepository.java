package it.unical.IoTOnChain.repository;

import it.unical.IoTOnChain.data.model.SensorsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SensorsLogRepository extends JpaRepository<SensorsLog, UUID>, JpaSpecificationExecutor<SensorsLog> {
}
