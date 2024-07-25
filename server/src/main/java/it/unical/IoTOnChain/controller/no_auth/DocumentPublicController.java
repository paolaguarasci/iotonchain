package it.unical.IoTOnChain.controller.no_auth;

import it.unical.IoTOnChain.data.dto.DocumentPublicDTO;
import it.unical.IoTOnChain.data.mapper.GenericMapper;
import it.unical.IoTOnChain.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public/doc")
public class DocumentPublicController {
  
  private final DocumentService documentService;
  private final GenericMapper genericMapper;
  
  // FIXME va messo per lo meno un rate limiter perche' puo' essere usato come oracolo
  @PostMapping("/check/{hash}")
  public ResponseEntity<DocumentPublicDTO> notarizeDocument(@PathVariable String hash) {
    log.debug("Notarize document for company logged");
    String hashRealeTx = "0xfaaa24f7237e88ecb1fcb19ca9a8d2d0a73aaae801206c3adfad0585c4198ef3";
    return ResponseEntity.ok().body(genericMapper.mapToPublic(documentService.check(hash)));
  }
  
}
