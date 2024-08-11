package it.unical.IoTOnChain.controller.company;

import it.unical.IoTOnChain.data.dto.TransferRequestDTO;
import it.unical.IoTOnChain.data.dto.TransferToOwnerDTO;
import it.unical.IoTOnChain.data.mapper.GenericMapper;
import it.unical.IoTOnChain.data.model.Batch;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.exception.MoveIsNotPossibleException;
import it.unical.IoTOnChain.service.BatchService;
import it.unical.IoTOnChain.service.CompanyService;
import it.unical.IoTOnChain.service.TransferService;
import it.unical.IoTOnChain.utils.StringTools;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/company/transfer")
public class TransferController {
  private final BatchService batchService;
  private final CompanyService companyService;
  private final TransferService transferService;
  private final GenericMapper mapper;
  private final StringTools stringTools;
  
  @GetMapping()
  private ResponseEntity<List<TransferToOwnerDTO>> listAll(@AuthenticationPrincipal Jwt principal) {
    log.debug("Create one batch of product type for company logged");
    String companyLogged = principal.getClaimAsString("company");
    Company companyOwner = companyService.getOneByName(companyLogged);
    return ResponseEntity.status(HttpStatus.OK).body(mapper.mapListTransfer(transferService.getAllForCompanyLogged(companyOwner)));
  }
  
  
  @GetMapping("/{batch_id}")
  private ResponseEntity<List<TransferToOwnerDTO>> listAllByBatchId(@AuthenticationPrincipal Jwt principal, @PathVariable String batch_id) {
    log.debug("Create one batch of product type for company logged");
    String companyLogged = principal.getClaimAsString("company");
    Company companyOwner = companyService.getOneByName(companyLogged);
    return ResponseEntity.status(HttpStatus.OK).body(mapper.mapListTransfer(transferService.getAllForCompanyLoggedAndBatchId(companyOwner, stringTools.cleanStr(batch_id))));
  }
  
  @PostMapping()
  private ResponseEntity<TransferToOwnerDTO> createOne(@AuthenticationPrincipal Jwt principal, @RequestBody TransferRequestDTO dto) throws Exception, MoveIsNotPossibleException {
    String companyLogged = principal.getClaimAsString("company");
    log.debug("Transfer batch {} ", dto.toString());
    Company companyOwner = companyService.getOneByName(companyLogged);
    Company companyReceiver = companyService.getOneById(stringTools.cleanStr(dto.getCompanyRecipientID()));
    Batch batch = batchService.getOneByBatchIdAndCompany(companyOwner, stringTools.cleanStr(dto.getBatchID()));
    log.debug("Create one transfer from batch id {} owner {} to company {} quantity {} ", batch.getBatchId(), batch.getCompanyOwner().getName(), companyReceiver.getName(), dto.getQuantity());
    if (stringTools.cleanStr(dto.getType()).toLowerCase().trim().equals("oneshot")) {
      return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(transferService.makeTransactionOneShot(companyOwner, batch, companyReceiver, dto.getQuantity())));
    } else {
      return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(transferService.makeTransactionWithAcceptance(companyOwner, batch, companyReceiver, dto.getQuantity())));
    }
  }
  
  
  @GetMapping("/{trans_id}/accept")
  private ResponseEntity<TransferToOwnerDTO> accept(@AuthenticationPrincipal Jwt principal, @PathVariable String trans_id) throws Exception, MoveIsNotPossibleException {
    String companyName = principal.getClaimAsString("company");
    Company companyLogged = companyService.getOneByName(companyName);
    return ResponseEntity.ok(mapper.map(transferService.accept(companyLogged, stringTools.cleanStr(trans_id))));
  }
  
  @GetMapping("/{trans_id}/reject")
  private ResponseEntity<TransferToOwnerDTO> reject(@AuthenticationPrincipal Jwt principal, @PathVariable String trans_id) throws Exception, MoveIsNotPossibleException {
    String companyName = principal.getClaimAsString("company");
    Company companyLogged = companyService.getOneByName(companyName);
    return ResponseEntity.ok(mapper.map(transferService.reject(companyLogged, stringTools.cleanStr(trans_id))));
  }
  
  @GetMapping("/{trans_id}/abort")
  private ResponseEntity<TransferToOwnerDTO> block(@AuthenticationPrincipal Jwt principal, @PathVariable String trans_id) throws Exception, MoveIsNotPossibleException {
    String companyName = principal.getClaimAsString("company");
    Company companyLogged = companyService.getOneByName(companyName);
    return ResponseEntity.ok(mapper.map(transferService.abort(companyLogged, stringTools.cleanStr(trans_id))));
  }
}
