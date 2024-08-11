package it.unical.IoTOnChain.repository;

import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface BatchRepository extends JpaRepository<Batch, UUID> {
  List<Batch> findAllByCompanyOwner(Company company);
  
  List<Batch> findAllByBatchIdAndCompanyOwner(String batchId, Company company);
  
  Optional<Batch> findByBatchIdAndCompanyOwner(String batchId, Company company);
  
  Optional<Batch> findByIdAndCompanyOwner(UUID id, Company companyOwner);
  
  List<Batch> findAllByCompanyOwnerAndProductType(Company companyOwner, ProductType productType);
  
  Set<Batch> findAllByBatchIdIsIn(List<String> batchId);
  
  Optional<Batch> findByBatchId(String batchId);
}
