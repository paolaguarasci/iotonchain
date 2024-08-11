package it.unical.IoTOnChain.controller.no_auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unical.IoTOnChain.data.mapper.GenericMapper;
import it.unical.IoTOnChain.service.BatchService;
import it.unical.IoTOnChain.service.CompanyService;
import it.unical.IoTOnChain.service.DocumentService;
import it.unical.IoTOnChain.utils.StringTools;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public")
public class PublicController {
  private final BatchService batchService;
  private final CompanyService companyService;
  private final DocumentService documentService;
  private final ObjectMapper objectMapper;
  private final GenericMapper genericMapper;
  private final StringTools stringTools;
  
  @GetMapping("/{company_name}/{batch_id}")
  public ResponseEntity<String> getTrackInfo(@PathVariable String batch_id, @PathVariable String company_name) throws JsonProcessingException {
    String idCleaned = stringTools.cleanStr(batch_id);
    String companyNameCleaned = stringTools.cleanStr(company_name);
    log.debug("Get single product PUBLIC information {} {}", idCleaned, companyNameCleaned);
    if (idCleaned == null || idCleaned.isEmpty() || companyNameCleaned == null || companyNameCleaned.isEmpty()) {
      return ResponseEntity.badRequest().body(null);
    }
    return ResponseEntity.ok(objectMapper.writeValueAsString(batchService.trackInfoPublic(companyNameCleaned, idCleaned)));
  }
  
  // FIXME va messo per lo meno un rate limiter perche' puo' essere usato come oracolo
//  @PostMapping("/doc/{hash}")
//  public ResponseEntity<DocumentPublicDTO> notarizeDocument(@PathVariable String hash) {
//    log.debug("Notarize document for company logged");
//    String hashRealeTx = "0xfaaa24f7237e88ecb1fcb19ca9a8d2d0a73aaae801206c3adfad0585c4198ef3";
//    return ResponseEntity.ok().body(genericMapper.mapToPublic(documentService.check(hash)));
//  }
}
