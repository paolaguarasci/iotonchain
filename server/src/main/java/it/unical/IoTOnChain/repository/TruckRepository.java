package it.unical.IoTOnChain.repository;

import it.unical.IoTOnChain.data.model.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TruckRepository extends JpaRepository<Truck, UUID>, JpaSpecificationExecutor<Truck> {
  @Query(value = "select t from Truck t where t.id not in (select distinct tr.truckId from Transport tr where tr.truckId is not null)")
  List<Truck> findAllAvailable();
}
