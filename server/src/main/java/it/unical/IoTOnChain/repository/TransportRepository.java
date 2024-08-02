package it.unical.IoTOnChain.repository;

import it.unical.IoTOnChain.data.model.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransportRepository extends JpaRepository<Transport, UUID>, JpaSpecificationExecutor<Transport> {
  List<Transport> findAllByBatchIdOrderByDateStartDesc(String batchId);
  
  List<Transport> findAllByCompanyFromOrCompanyToOrderByDateStartDesc(String companyFrom, String companyTo);
}
