package it.unical.IoTOnChain.repository;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Notarize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotarizeRepository extends JpaRepository<Notarize, UUID> {
  
  List<Notarize> findAllByCompanyOrderByNotarizedAtDesc(Company company);
  
}
