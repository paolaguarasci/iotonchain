package it.unical.IoTOnChain.data.mapper;

import it.unical.IoTOnChain.data.dto.*;
import it.unical.IoTOnChain.data.model.*;
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
  
  Recipe map(CreateRecipeDTOFromOwner recipe);
  
  Recipe mapListRecipe(CreateRecipeDTOFromOwner recipe);
  
  RecipeRow map(CreateRecipeRowDTOFromOwner recipe);
  
  List<RecipeRow> mapListRecipeRow(List<CreateRecipeRowDTOFromOwner> recipes);
  
  List<DocumentToOwnerDTO> mapDocumentsToDTO(List<Document> allByCompanyLogged);
  
  DocumentToOwnerDTO map(Document entity);
  
  DocumentPublicDTO mapToPublic(Document check);
  
  ProductionProcess map(CreatePPDTOFromOwner process);
  
  ProductionStep map(CreateProcessStepDTOFromOwner process);
  
  ProductionStepTOChainDTO map(ProductionStep ps);
  
  ProductionStepBatchTOChainDTO map(ProductionStepBatch ps);
  
  List<TransportToOwnerDTO> map(List<Transport> allByCompany);
  
  TransportToOwnerDTO map(Transport entity);
  
  List<TruckToOwnerDTO> mapTruckListToDTOOwner(List<Truck> allByCompany);
  
  TruckToOwnerDTO map(Truck entity);
  
  
  List<NotarizeToOwnerDTO> mapNotarizeListToOwner(List<Notarize> all);
  
  @Mapping(source = "document.id", target = "documentId")
  NotarizeToOwnerDTO map(Notarize entity);
}
