package it.unical.IoTOnChain.controller.company;

import it.unical.IoTOnChain.data.dto.DocumentToOwnerDTO;
import it.unical.IoTOnChain.data.mapper.GenericMapper;
import it.unical.IoTOnChain.data.model.Company;
import it.unical.IoTOnChain.data.model.Document;
import it.unical.IoTOnChain.service.CompanyService;
import it.unical.IoTOnChain.service.DocumentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload2.core.*;
import org.apache.commons.fileupload2.jakarta.JakartaServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/company/doc")
public class DocumentController {
  private final CompanyService companyService;
  private final DocumentService documentService;
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
  public ResponseEntity<Object> uploadDocument(@AuthenticationPrincipal Jwt principal, MultipartFile file) throws IOException {
    
    String companyName = principal.getClaimAsString("company");
    Company company = companyService.getOneByName(companyName);
    if (company == null) {
      return ResponseEntity.badRequest().build();
    }
    log.debug("Upload document for company logged 0 {} ", company.getName());
    //  0x734a8918ede5dbc461d11f391a79efb0baf6fde8c1a78031f43ca7471a9fae3c
    try {
      Files.deleteIfExists(this.root.resolve(file.getOriginalFilename()));
      Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
      log.debug("Uploaded document to company logged {}", this.root.resolve(file.getOriginalFilename()));
      return ResponseEntity.ok(genericMapper.map(documentService.createOne(company, this.root.resolve(file.getOriginalFilename()))));
    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("A file of that name already exists.");
      }
      throw new RuntimeException(e.getMessage());
    }
  }
  

}
