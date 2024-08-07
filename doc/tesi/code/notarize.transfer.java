  @Override
  public void notarize(Company company, Transfer transfer) throws NoSuchAlgorithmException, IOException, TransactionException {
    String doc = objectMapper.writeValueAsString(genericMapper.map(transfer));
    MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
    byte[] encodedHash = digest.digest(doc.getBytes(StandardCharsets.UTF_8));
    String sha3Hex = bytesToHex(encodedHash);
    log.debug("\nNotarizzazione di {}\nbyteArr {}\nhash {}", doc, encodedHash, sha3Hex);
    chainService.signString(encodedHash, (String txHash, String error) -> {
      if (error != null) {
        log.debug("[notarize(Transfer transfer)-int] Hash tx {} {} {}", txHash, doc, error);
        return "Ciao";
      }
      if (transfer.getTxTransactionList() == null) {
        transfer.setTxTransactionList(List.of(ChainTransaction.builder().txId(txHash).build()));
      } else {
        transfer.getTxTransactionList().add(ChainTransaction.builder().txId(txHash).build());
      }
      transferRepository.save(transfer);
      log.debug("[notarize(Transfer transfer)-ext]  Hash tx {} - {}", txHash, doc);
      return "ciao";
    });
  }