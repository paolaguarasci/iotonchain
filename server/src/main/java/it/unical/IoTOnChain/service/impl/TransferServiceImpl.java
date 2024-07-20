package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.ChainTransaction;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Transfer;
import it.unical.IoTOnChain.exception.MoveIsNotPossibleException;
import it.unical.IoTOnChain.exception.NoEnoughRawMaterialsException;
import it.unical.IoTOnChain.repository.TransferRepository;
import it.unical.IoTOnChain.service.BatchService;
import it.unical.IoTOnChain.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
  private final TransferRepository transferRepository;
  private final BatchService batchService;
  @Override
  public Transfer makeTransactionOneShot(Company companyLogged, Batch batch, Company company, int quantity) throws MoveIsNotPossibleException, NoEnoughRawMaterialsException {
    batchService.move(companyLogged, batch, company, quantity);
    ChainTransaction tx = saveOnChain();
    return transferRepository.save(Transfer.builder()
        .batchID(batch.getBatchId())
        .companyRecipientID(company.getId().toString())
        .type(Transfer.TransferType.ONESHOT)
        .quantity(quantity)
        .transferDateStart(LocalDateTime.now())
        .status(Transfer.TransferStatus.COMPLETED)
      .build());
  }
  
  private ChainTransaction saveOnChain() {
    return null;
  }
}
