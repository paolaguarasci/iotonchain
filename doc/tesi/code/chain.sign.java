  @Async
  public void signString(byte[] str, java.util.function.BiFunction<String, String, String> func) throws IOException, TransactionException {
    Hash contract = (Hash) this.registrationContract.get("hash");
    TransactionReceipt txReceipt = null;
    Function function = new Function("signHash",
      Arrays.asList(new Bytes32(str), new Utf8String(Arrays.toString(str))),
      Collections.emptyList());
    String txData = FunctionEncoder.encode(function);
    EthSendTransaction txHash = this.transactionManager.sendTransaction(DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT, contract.getContractAddress(), txData, BigInteger.ZERO);
    TransactionReceiptProcessor receiptProcessor = new PollingTransactionReceiptProcessor(this.web3j, TransactionManager.DEFAULT_POLLING_FREQUENCY, TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);
    String error = null;
    String msg = null;
    try {
      txReceipt = receiptProcessor.waitForTransactionReceipt(txHash.getTransactionHash());
      msg = txReceipt.getTransactionHash();
    } catch (TransactionException e) {
      error = e.getMessage();
    }
    func.apply(msg, error);
  }