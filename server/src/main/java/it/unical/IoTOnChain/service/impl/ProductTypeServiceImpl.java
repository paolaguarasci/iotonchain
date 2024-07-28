package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.ProductType;
import it.unical.IoTOnChain.data.model.ProductionProcess;
import it.unical.IoTOnChain.data.model.Recipe;
import it.unical.IoTOnChain.repository.CompanyRepository;
import it.unical.IoTOnChain.repository.ProductTypeRepository;
import it.unical.IoTOnChain.repository.ProductionProcessRepository;
import it.unical.IoTOnChain.repository.RecipeRepository;
import it.unical.IoTOnChain.service.ProductTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductTypeServiceImpl implements ProductTypeService {
  private final ProductTypeRepository productTypeRepository;
  private final CompanyRepository companyRepository;
  private final RecipeRepository recipeRepository;
  private final ProductionProcessRepository productionProcessRepository;
  
  
  @Override
  @Transactional
  public ProductType createProductTypeForCompany(Company company, String productTypeName, String unity, Recipe recipe, ProductionProcess pp) {
    Optional<Company> companyFromDb = companyRepository.findById(company.getId());
    if (companyFromDb.isPresent()) {
      Company companyToUpdate = companyFromDb.get();
      if (companyToUpdate.getProductTypeList().stream().anyMatch(el -> el.getName().equals(productTypeName))) {
        log.debug("Product type {} already exists", productTypeName);
      } else {
        if (recipe != null && recipe.getId() == null) {
          recipe = recipeRepository.save(recipe);
        }
        ProductType ppSaved = productTypeRepository.save(ProductType.builder().productionProcess(pp).name(productTypeName).unity(unity).recipe(recipe).build());
        companyToUpdate.getProductTypeList().add(ppSaved);
        companyRepository.save(companyToUpdate);
        log.debug("Product type {} creato!", productTypeName);
        return ppSaved;
      }
    }
    return null;
  }
  
  @Override
  public List<ProductType> getAllProductTypesByLoggedCompany(String companyLogged) {
    
    if (companyLogged == null || companyLogged.isEmpty()) {
      return List.of();
    }
    Optional<Company> company = companyRepository.findByName(companyLogged);
    if (company.isPresent()) {
      // return productTypeRepository.findAllByCompany(company.get());
      return company.get().getProductTypeList();
    }
    return List.of();
    
  }
  
  @Override
  public ProductType getOneById(String productTypeID) {
    return productTypeRepository.findById(UUID.fromString(productTypeID)).orElse(null);
  }
}
