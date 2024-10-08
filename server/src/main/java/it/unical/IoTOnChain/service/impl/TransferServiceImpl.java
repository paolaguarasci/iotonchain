package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Transfer;
import it.unical.IoTOnChain.exception.MoveIsNotPossibleException;
import it.unical.IoTOnChain.exception.NoEnoughRawMaterialsException;
import it.unical.IoTOnChain.repository.TransferRepository;
import it.unical.IoTOnChain.repository.TransportRepository;
import it.unical.IoTOnChain.repository.TruckRepository;
import it.unical.IoTOnChain.service.BatchService;
import it.unical.IoTOnChain.service.CompanyService;
import it.unical.IoTOnChain.service.NotarizeService;
import it.unical.IoTOnChain.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
  private final NotarizeService notarizeService;
  
  @Override
  public Transfer makeTransactionOneShot(Company companyLogged, Batch batch, Company company, int quantity) throws MoveIsNotPossibleException, Exception {
    Batch newsBAtch = batchService.move(companyLogged, batch, company, quantity, false);
    
    Transfer trs = Transfer.builder()
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
      .build();
    
    
    transferRepository.save(trs);
    saveOnChain(companyLogged, trs);
    return trs;
  }
  
  @Override
  public Transfer makeTransactionWithAcceptance(Company companyLogged, Batch batch, Company company, int quantity) throws MoveIsNotPossibleException, Exception {
    
    if (batch.getQuantity() >= quantity) {
      batchService.block(companyLogged, batch.getBatchId(), quantity);
    } else {
      throw new NoEnoughRawMaterialsException("No more enough materials");
    }
    
    Transfer trs = Transfer.builder()
      .oldBatchID(batch.getBatchId())
      .unity(batch.getProductType().getUnity())
      .companySenderID(companyLogged.getId().toString())
      .companySenderUsername(companyLogged.getName())
      .companyRecipientID(company.getId().toString())
      .companyRecipientUsername(company.getName())
      .type(Transfer.TransferType.WITHACCEPTANCE)
      .quantity(quantity)
      .transferDateStart(LocalDateTime.now())
      .status(Transfer.TransferStatus.INIT)
      .lastUpdate(LocalDateTime.now())
      .build();
    transferRepository.save(trs);
    
    saveOnChain(companyLogged, trs);
    return trs;
  }
  
  @Override
  public List<Transfer> getAllForCompanyLogged(Company companyOwner) {
    return transferRepository.findAllByCompanySenderIDOrCompanyRecipientID(companyOwner.getId().toString(), companyOwner.getId().toString());
  }
  
  @Override
  public List<Transfer> getAllForCompanyLoggedAndBatchId(Company companyOwner, String batchId) {
    List<Transfer> q1 = transferRepository.findAllByCompanySenderIDOrCompanyRecipientID(companyOwner.getId().toString(), companyOwner.getId().toString());
    List<Transfer> q2 = new java.util.ArrayList<>(q1.stream().filter(transfer -> transfer.getNewBatchID() != null && transfer.getNewBatchID().equals(batchId)).toList());
    if (q2.size() == 1) {
      Company company1 = companyService.getOneById(q2.getFirst().getCompanySenderID());
      List<Transfer> x = getAllForCompanyLoggedAndBatchId(company1, q2.getFirst().getOldBatchID());
      q2.addAll(x);
    }
    log.debug("La query Q2 ha restituito {} risultati", q2.size());
    return q2;
  }
  
  @Override
  public Transfer accept(Company companyLogged, String trans_id) throws Exception, MoveIsNotPossibleException {
    Optional<Transfer> tx = transferRepository.findById(UUID.fromString(trans_id));
    if (tx.isPresent()) {
      Transfer transfer = tx.get();
      transfer.setStatus(Transfer.TransferStatus.COMPLETED);
      Company company = companyService.getOneById(transfer.getCompanySenderID());
      int quantity = transfer.getQuantity();
      Batch oldBatch = batchService.getOneByBatchIdAndCompany(company, transfer.getOldBatchID());
      Batch newsBAtch = batchService.move(company, oldBatch, companyLogged, quantity, true);
      transfer.setNewBatchID(newsBAtch.getBatchId());
      saveOnChain(companyLogged, transfer);
      return transferRepository.save(transfer);
    }
    return null;
  }
  
  @Override
  public Transfer reject(Company companyLogged, String trans_id) throws Exception, MoveIsNotPossibleException {
    Optional<Transfer> tx = transferRepository.findById(UUID.fromString(trans_id));
    if (tx.isPresent()) {
      Transfer transfer = tx.get();
      transfer.setStatus(Transfer.TransferStatus.REJECTED);
      int quantity = transfer.getQuantity();
      Company company = companyService.getOneById(transfer.getCompanySenderID());
      batchService.refound(company, transfer.getOldBatchID(), quantity);
      saveOnChain(companyLogged, transfer);
      return transferRepository.save(transfer);
    }
    return null;
  }
  
  @Override
  public Transfer abort(Company companyLogged, String trans_id) throws Exception {
    Optional<Transfer> tx = transferRepository.findById(UUID.fromString(trans_id));
    if (tx.isPresent() && companyLogged.getId().toString().equals(tx.get().getCompanySenderID()) && tx.get().getStatus().equals(Transfer.TransferStatus.INIT)) {
      Transfer transfer = tx.get();
      transfer.setStatus(Transfer.TransferStatus.ABORTED);
      int quantity = transfer.getQuantity();
      Company company = companyService.getOneById(transfer.getCompanySenderID());
      batchService.refound(company, transfer.getOldBatchID(), quantity);
      saveOnChain(companyLogged, transfer);
      return transferRepository.save(transfer);
    }
    return null;
  }
  
  
  @Async
  public void saveOnChain(Company company, Transfer transfer) throws Exception {
    notarizeService.notarize(company, transfer);
  }
}
