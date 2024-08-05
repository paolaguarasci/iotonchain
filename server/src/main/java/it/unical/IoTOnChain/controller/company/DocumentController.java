package it.unical.IoTOnChain.controller.company;

import it.unical.IoTOnChain.data.dto.DocumentToOwnerDTO;
import it.unical.IoTOnChain.data.mapper.GenericMapper;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Document;
import it.unical.IoTOnChain.service.CompanyService;
import it.unical.IoTOnChain.service.DocumentService;
import it.unical.IoTOnChain.service.NotarizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/company/doc")
public class DocumentController {
  private final CompanyService companyService;
  private final DocumentService documentService;
  private final NotarizeService notarizeService;
  private final GenericMapper genericMapper;
  private final Path root = Paths.get("/tmp");
  
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
  public ResponseEntity<Object> uploadDocument(@AuthenticationPrincipal Jwt principal, @RequestPart String name, @RequestPart String description, @RequestPart String dateStart, @RequestPart String dateEnd, MultipartFile file) throws IOException {
    
    String companyName = principal.getClaimAsString("company");
    Company company = companyService.getOneByName(companyName);
    if (company == null) {
      return ResponseEntity.badRequest().build();
    }
    log.debug("Upload document for company logged 0 {} ", company.getName());
    
    Instant instant1 = Instant.ofEpochMilli(Long.parseLong(dateStart));
    Instant instant2 = Instant.ofEpochMilli(Long.parseLong(dateEnd));
    LocalDateTime localDateTime1 = LocalDateTime.ofInstant(instant1, ZoneId.of("Europe/Rome"));
    LocalDateTime localDateTime2 = LocalDateTime.ofInstant(instant2, ZoneId.of("Europe/Rome"));
    log.debug("Local Date Time 1 {}", localDateTime1);
    log.debug("Local Date Time 2 {}", localDateTime2);
    
    try {
      Files.deleteIfExists(this.root.resolve(file.getOriginalFilename()));
      Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
      log.debug("Uploaded document to company logged {}", this.root.resolve(file.getOriginalFilename()));
      return ResponseEntity.ok(genericMapper.map(documentService.createOne(company, name, description, localDateTime1, localDateTime2, this.root.resolve(file.getOriginalFilename()))));
    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("A file of that name already exists.");
      }
      throw new RuntimeException(e.getMessage());
    }
  }
  
  @GetMapping("/notarize/{doc_id}")
  public ResponseEntity<Object> notarizeDocument(@AuthenticationPrincipal Jwt principal, @PathVariable String doc_id) throws IOException {
    String companyName = principal.getClaimAsString("company");
    Company company = companyService.getOneByName(companyName);
    if (company == null) {
      return ResponseEntity.badRequest().build();
    }
    log.debug("Notarize document for company logged {} ", company.getName());
    try {
      documentService.notarize(company, Jsoup.clean(doc_id, Safelist.none()));
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }
  
  @GetMapping("/{doc_id}")
  public ResponseEntity<DocumentToOwnerDTO> getDocument(@AuthenticationPrincipal Jwt principal, @PathVariable String doc_id) throws IOException {
    String companyName = principal.getClaimAsString("company");
    Company company = companyService.getOneByName(companyName);
    if (company == null) {
      return null;
    }
    Document document = documentService.getOneByCompanyLogged(company, Jsoup.clean(doc_id, Safelist.none()));
    if (document == null) {
      return null;
    }
    return ResponseEntity.ok().body(genericMapper.map(document));
  }
  
  @GetMapping("/{doc_id}/resource")
  public ResponseEntity<?> getDocumentResource(@AuthenticationPrincipal Jwt principal, @PathVariable String doc_id) throws IOException {
    String companyName = principal.getClaimAsString("company");
    Company company = companyService.getOneByName(companyName);
    if (company == null) {
      return null;
    }
    Document document = documentService.getOneByCompanyLogged(company, Jsoup.clean(doc_id, Safelist.none()));
    if (document == null) {
      return null;
    }
    
    Resource resource = documentService.loadAsResource(document.getPath());
    
    String contentType = "application/octet-stream";
    String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
    
    return ResponseEntity.ok()
      .contentType(MediaType.parseMediaType(contentType))
      .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
      .body(resource);
  }
}
