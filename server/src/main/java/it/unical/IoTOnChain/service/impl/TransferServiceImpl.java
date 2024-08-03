package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.ChainTransaction;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Transfer;
import it.unical.IoTOnChain.exception.MoveIsNotPossibleException;
import it.unical.IoTOnChain.exception.NoEnoughRawMaterialsException;
import it.unical.IoTOnChain.repository.TransferRepository;
import it.unical.IoTOnChain.repository.TransportRepository;
import it.unical.IoTOnChain.repository.TruckRepository;
import it.unical.IoTOnChain.service.BatchService;
import it.unical.IoTOnChain.service.CompanyService;
import it.unical.IoTOnChain.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TransferServiceImpl implements TransferService {
  private final TransferRepository transferRepository;
  private final BatchService batchService;
  private final CompanyService companyService;
  private final TruckRepository truckRepository;
  private final TransportRepository transportRepository;
  
  @Override
  public Transfer makeTransactionOneShot(Company companyLogged, Batch batch, Company company, int quantity) throws MoveIsNotPossibleException, NoEnoughRawMaterialsException {
    Batch newsBAtch = batchService.move(companyLogged, batch, company, quantity);
    ChainTransaction tx = saveOnChain();
    // assert tx != null;
    return transferRepository.save(Transfer.builder()
      .oldBatchID(batch.getBatchId())
      .newBatchID(newsBAtch.getBatchId())
      .unity(batch.getProductType().getUnity())
      .companySenderID(companyLogged.getId().toString())
      .companySenderUsername(companyLogged.getName())
      .companyRecipientID(company.getId().toString())
      .companyRecipientUsername(company.getName())
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
    List<Transfer> q1 = transferRepository.findAllByCompanySenderIDOrCompanyRecipientID(companyOwner.getId().toString(), companyOwner.getId().toString());
    List<Transfer> q2 = new java.util.ArrayList<>(q1.stream().filter(transfer -> transfer.getNewBatchID().equals(batchId)).toList());
    if (q2.size() == 1) {
      Company company1 = companyService.getOneById(q2.getFirst().getCompanySenderID());
      List<Transfer> x = getAllForCompanyLoggedAndBatchId(company1, q2.getFirst().getOldBatchID());
      q2.addAll(x);
    }
    log.debug("La query Q2 ha restituito {} risultati", q2.size());
    return q2;
  }
  
  
  private ChainTransaction saveOnChain() {
    // TODO
    return ChainTransaction.builder().txId("txid").build();
  }
}
