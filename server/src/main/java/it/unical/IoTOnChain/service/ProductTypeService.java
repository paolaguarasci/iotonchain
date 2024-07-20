package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.ProductType;
import it.unical.IoTOnChain.data.model.Recipe;

public interface ProductTypeService {
  ProductType createProductTypeForCompany(Company company, String productTypeName, String unity, Recipe recipe);
}
