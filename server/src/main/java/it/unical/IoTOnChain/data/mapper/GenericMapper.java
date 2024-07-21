package it.unical.IoTOnChain.data.mapper;

import it.unical.IoTOnChain.data.dto.BatchToOwner;
import it.unical.IoTOnChain.data.dto.CompanyLite;
import it.unical.IoTOnChain.data.dto.ProductTypeToOwner;
import it.unical.IoTOnChain.data.dto.TransferToOwnerDTO;
import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.ProductType;
import it.unical.IoTOnChain.data.model.Transfer;
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
  
  List<ProductTypeToOwner> mapForProductTypeOwner(List<ProductType> allProductTypesByLoggedCompany);
  
  ProductTypeToOwner map(ProductType entity);
  
  List<CompanyLite> mapCompanyLite(List<Company> allCompanyClient);
  
  CompanyLite map(Company entity);
  
  TransferToOwnerDTO map(Transfer transfer);
  
  List<TransferToOwnerDTO> mapListTransfer(List<Transfer> allForCompanyLogged);
}
