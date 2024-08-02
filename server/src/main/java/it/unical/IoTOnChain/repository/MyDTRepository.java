package it.unical.IoTOnChain.repository;

import it.unical.IoTOnChain.data.model.MyDT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MyDTRepository extends JpaRepository<MyDT, UUID>, JpaSpecificationExecutor<MyDT> {
}
