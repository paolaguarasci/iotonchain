package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Transfer;
import it.unical.IoTOnChain.exception.MoveIsNotPossibleException;

import java.util.List;

public interface TransferService {
  Transfer makeTransactionOneShot(Company companyLogged, Batch batch, Company company, int quantity) throws MoveIsNotPossibleException, Exception;
  
  Transfer makeTransactionWithAcceptance(Company companyLogged, Batch batch, Company company, int quantity) throws MoveIsNotPossibleException, Exception;
  
  List<Transfer> getAllForCompanyLogged(Company companyOwner);
  
  List<Transfer> getAllForCompanyLoggedAndBatchId(Company companyOwner, String batchId);
  
  Transfer accept(Company companyLogged, String trans_id) throws Exception, MoveIsNotPossibleException;
  
  Transfer reject(Company companyLogged, String trans_id) throws Exception, MoveIsNotPossibleException;
}
