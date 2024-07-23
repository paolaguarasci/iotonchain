package it.unical.IoTOnChain.controller.company;

import it.unical.IoTOnChain.data.dto.DocumentToOwnerDTO;
import it.unical.IoTOnChain.data.mapper.GenericMapper;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.service.CompanyService;
import it.unical.IoTOnChain.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/company/doc")
public class DocumentController {
  private final CompanyService companyService;
  private final DocumentService documentService;
  private final GenericMapper genericMapper;

  @GetMapping
  public ResponseEntity<List<DocumentToOwnerDTO>> getDocuments(@AuthenticationPrincipal Jwt principal) {
    log.debug("Get all document for company logged");
    String companyName = principal.getClaimAsString("company");
    Company company = companyService.getOneByName(companyName);
    if (company == null) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(genericMapper.mapDocumentsToDTO(documentService.getAllByCompanyLogged(company)));
  }

  @PostMapping("/upload")
  public ResponseEntity<DocumentToOwnerDTO> uploadDocument(@AuthenticationPrincipal Jwt principal) {
    log.debug("Upload document for company logged");
    // TODO
    return ResponseEntity.ok().build();
  }

  @PostMapping("/notarize")
  public ResponseEntity<Void> notarizeDocument(@AuthenticationPrincipal Jwt principal) {
    log.debug("Notarize document for company logged");
    // TODO
    return ResponseEntity.ok().build();
  }
}
