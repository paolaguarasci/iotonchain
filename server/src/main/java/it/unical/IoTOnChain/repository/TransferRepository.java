package it.unical.IoTOnChain.repository;

import it.unical.IoTOnChain.data.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer> {
}
