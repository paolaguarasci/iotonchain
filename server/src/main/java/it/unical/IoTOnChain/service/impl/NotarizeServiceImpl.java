package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.repository.NotarizeRepository;
import it.unical.IoTOnChain.service.NotarizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotarizeServiceImpl implements NotarizeService {
  private final NotarizeRepository notarizeRepository;
}
