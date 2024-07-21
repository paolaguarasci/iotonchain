package it.unical.IoTOnChain.controller.company;

import it.unical.IoTOnChain.data.dto.TransferRequestDTO;
import it.unical.IoTOnChain.data.dto.TransferToOwnerDTO;
import it.unical.IoTOnChain.data.mapper.GenericMapper;
import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.exception.MoveIsNotPossibleException;
import it.unical.IoTOnChain.exception.NoEnoughRawMaterialsException;
import it.unical.IoTOnChain.service.BatchService;
import it.unical.IoTOnChain.service.CompanyService;
import it.unical.IoTOnChain.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/company/transfer")
public class TransferController {
  
  private final BatchService batchService;
  private final CompanyService  companyService;
  private final TransferService transferService;
  private final GenericMapper mapper;
  
  
  @GetMapping()
  private ResponseEntity<List<TransferToOwnerDTO>> listAll() {
    log.debug("Create one batch of product type for company logged");
    String companyLogged = "barillaSPA";
//    if (principal != null) {
//      companyLogged = principal.getClaimAsString("given_name");
//    }
    Company companyOwner = companyService.getOneByName(companyLogged);
      return ResponseEntity.status(HttpStatus.OK).body(mapper.mapListTransfer(transferService.getAllForCompanyLogged(companyOwner)));

  }
  
  @PostMapping()
  private ResponseEntity<TransferToOwnerDTO> createOne(@RequestBody TransferRequestDTO dto) throws NoEnoughRawMaterialsException, MoveIsNotPossibleException {
    String companyLogged = "barillaSPA";
//    if (principal != null) {
//      companyLogged = principal.getClaimAsString("given_name");
//    }
    log.debug("Transfer batch {} ", dto.toString());
    Company companyOwner = companyService.getOneByName(companyLogged);
    Company companyReceiver = companyService.getOneById(dto.getCompanyRecipientID());
    Batch batch = batchService.getOneByBatchIdAndCompany(companyOwner, dto.getBatchID());
    log.debug("Create one transfer from batch id {} owner {} to company {} quantity {} ", batch.getBatchId(), batch.getCompanyOwner().getName(), companyReceiver.getName(), dto.getQuantity());
    if(dto.getType().toLowerCase().trim().equals("oneshot")) {
      return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(transferService.makeTransactionOneShot(companyOwner, batch , companyReceiver, dto.getQuantity())));
    } else {
      return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(transferService.makeTransactionWithAcceptance(companyOwner, batch , companyReceiver, dto.getQuantity())));
    }
  }
  
//  @PostMapping("/bi-face")
//  private ResponseEntity<TransferToOwnerDTO> createOne(@RequestBody TransferRequestDTO dto) {
//    return transferService.makeTransactionBiFace();
//  }
  
}
