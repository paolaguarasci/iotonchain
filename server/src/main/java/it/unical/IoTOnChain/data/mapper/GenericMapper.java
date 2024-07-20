package it.unical.IoTOnChain.data.mapper;

import it.unical.IoTOnChain.data.dto.BatchToOwner;
import it.unical.IoTOnChain.data.model.Batch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface GenericMapper {
  List<BatchToOwner> mapForProductOwner(List<Batch> allProductByCompanyLogged);
  
  
  @Mapping(source = "companyOwner.id", target = "companyOwnerID")
  @Mapping(source = "companyProducer.id", target = "companyProducerID")
  @Mapping(source = "productType.unity", target = "unity")
  BatchToOwner map(Batch bath);
}
