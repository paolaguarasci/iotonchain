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
import java.util.List;
import java.util.stream.Collectors;

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
    // assert tx != null;
    return transferRepository.save(Transfer.builder()
      .batchID(batch.getBatchId())
      .unity(batch.getProductType().getUnity())
      .companySenderID(companyLogged.getId().toString())
      .companyRecipientID(company.getId().toString())
      .type(Transfer.TransferType.ONESHOT)
      .quantity(quantity)
      .transferDateStart(LocalDateTime.now())
      .status(Transfer.TransferStatus.COMPLETED)
      .lastUpdate(LocalDateTime.now())
      .txTransactionList(List.of(tx))
      .build());
  }
  
  @Override
  public Transfer makeTransactionWithAcceptance(Company companyLogged, Batch batch, Company company, int quantity) throws MoveIsNotPossibleException, NoEnoughRawMaterialsException {
    return null;
  }
  
  @Override
  public List<Transfer> getAllForCompanyLogged(Company companyOwner) {
    return transferRepository.findAllByCompanySenderIDOrCompanyRecipientID(companyOwner.getId().toString(), companyOwner.getId().toString());
  }
  
  @Override
  public List<Transfer> getAllForCompanyLoggedAndBatchId(Company companyOwner, String batchId) {
    return transferRepository.findAllByCompanySenderIDOrCompanyRecipientID(companyOwner.getId().toString(),companyOwner.getId().toString()).stream().filter(transfer -> transfer.getBatchID().equals(batchId)).toList();
  }
  
  
  private ChainTransaction saveOnChain() {
    // TODO
    return ChainTransaction.builder().txId("txid").build();
  }
}
