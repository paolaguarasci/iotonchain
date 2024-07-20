package it.unical.IoTOnChain.service;

import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Transfer;
import it.unical.IoTOnChain.exception.MoveIsNotPossibleException;
import it.unical.IoTOnChain.exception.NoEnoughRawMaterialsException;

public interface TransferService {
  Transfer makeTransactionOneShot(Company companyLogged, Batch batch, Company company, int quantity) throws MoveIsNotPossibleException, NoEnoughRawMaterialsException;
}
