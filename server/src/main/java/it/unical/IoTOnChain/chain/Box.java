package it.unical.IoTOnChain.chain;

import io.reactivex.Flowable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.*;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.0.
 */
@SuppressWarnings("rawtypes")
public class Box extends Contract {
  public static final String BINARY = "0x608060405234801561001057600080fd5b50604051610864380380610864833981810160405281019061003291906102e2565b33600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16036100a55760006040517f1e4fbdf700000000000000000000000000000000000000000000000000000000815260040161009c9190610350565b60405180910390fd5b6100b4816100ca60201b60201c565b506100c48161018e60201b60201c565b50610395565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050816000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a35050565b61019c6101dd60201b60201c565b806001819055507f93fe6d397c74fdf1402a8b72e47b68512f0510d7b98a4bc4cbdf6ac7108b3c59816040516101d2919061037a565b60405180910390a150565b6101eb61027660201b60201c565b73ffffffffffffffffffffffffffffffffffffffff1661020f61027e60201b60201c565b73ffffffffffffffffffffffffffffffffffffffff16146102745761023861027660201b60201c565b6040517f118cdaa700000000000000000000000000000000000000000000000000000000815260040161026b9190610350565b60405180910390fd5b565b600033905090565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b600080fd5b6000819050919050565b6102bf816102ac565b81146102ca57600080fd5b50565b6000815190506102dc816102b6565b92915050565b6000602082840312156102f8576102f76102a7565b5b6000610306848285016102cd565b91505092915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061033a8261030f565b9050919050565b61034a8161032f565b82525050565b60006020820190506103656000830184610341565b92915050565b610374816102ac565b82525050565b600060208201905061038f600083018461036b565b92915050565b6104c0806103a46000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80632e64cec11461005c5780636057361d1461007a578063715018a6146100965780638da5cb5b146100a0578063f2fde38b146100be575b600080fd5b6100646100da565b604051610071919061035c565b60405180910390f35b610094600480360381019061008f91906103a8565b6100e4565b005b61009e61012d565b005b6100a8610141565b6040516100b59190610416565b60405180910390f35b6100d860048036038101906100d3919061045d565b61016a565b005b6000600154905090565b6100ec6101f0565b806001819055507f93fe6d397c74fdf1402a8b72e47b68512f0510d7b98a4bc4cbdf6ac7108b3c5981604051610122919061035c565b60405180910390a150565b6101356101f0565b61013f6000610277565b565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b6101726101f0565b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16036101e45760006040517f1e4fbdf70000000000000000000000000000000000000000000000000000000081526004016101db9190610416565b60405180910390fd5b6101ed81610277565b50565b6101f861033b565b73ffffffffffffffffffffffffffffffffffffffff16610216610141565b73ffffffffffffffffffffffffffffffffffffffff16146102755761023961033b565b6040517f118cdaa700000000000000000000000000000000000000000000000000000000815260040161026c9190610416565b60405180910390fd5b565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050816000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a35050565b600033905090565b6000819050919050565b61035681610343565b82525050565b6000602082019050610371600083018461034d565b92915050565b600080fd5b61038581610343565b811461039057600080fd5b50565b6000813590506103a28161037c565b92915050565b6000602082840312156103be576103bd610377565b5b60006103cc84828501610393565b91505092915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000610400826103d5565b9050919050565b610410816103f5565b82525050565b600060208201905061042b6000830184610407565b92915050565b61043a816103f5565b811461044557600080fd5b50565b60008135905061045781610431565b92915050565b60006020828403121561047357610472610377565b5b600061048184828501610448565b9150509291505056fea2646970667358221220fc56ab4ded1da2122bada7f5a44654814a3f4515a6ed2bbe14548aa79c66cb2e64736f6c63430008180033";
  public static final String FUNC_OWNER = "owner";
  public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";
  public static final String FUNC_RETRIEVE = "retrieve";
  public static final String FUNC_STORE = "store";
  public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";
  public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred",
    Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
    }, new TypeReference<Address>(true) {
    }));
  public static final Event VALUECHANGED_EVENT = new Event("ValueChanged",
    Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
    }));
  ;
  protected static final HashMap<String, String> _addresses;
  ;
  private static String librariesLinkedBinary;
  
  static {
    _addresses = new HashMap<String, String>();
  }
  
  @Deprecated
  protected Box(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
                BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }
  
  protected Box(String contractAddress, Web3j web3j, Credentials credentials,
                ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }
  
  @Deprecated
  protected Box(String contractAddress, Web3j web3j, TransactionManager transactionManager,
                BigInteger gasPrice, BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }
  
  protected Box(String contractAddress, Web3j web3j, TransactionManager transactionManager,
                ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
  }
  
  public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(
    TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
    ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
      typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }
  
  public static OwnershipTransferredEventResponse getOwnershipTransferredEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
    OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
    typedResponse.log = log;
    typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
    typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
    return typedResponse;
  }
  
  public static List<ValueChangedEventResponse> getValueChangedEvents(
    TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VALUECHANGED_EVENT, transactionReceipt);
    ArrayList<ValueChangedEventResponse> responses = new ArrayList<ValueChangedEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      ValueChangedEventResponse typedResponse = new ValueChangedEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }
  
  public static ValueChangedEventResponse getValueChangedEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VALUECHANGED_EVENT, log);
    ValueChangedEventResponse typedResponse = new ValueChangedEventResponse();
    typedResponse.log = log;
    typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
    return typedResponse;
  }
  
  @Deprecated
  public static Box load(String contractAddress, Web3j web3j, Credentials credentials,
                         BigInteger gasPrice, BigInteger gasLimit) {
    return new Box(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }
  
  @Deprecated
  public static Box load(String contractAddress, Web3j web3j,
                         TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
    return new Box(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }
  
  public static Box load(String contractAddress, Web3j web3j, Credentials credentials,
                         ContractGasProvider contractGasProvider) {
    return new Box(contractAddress, web3j, credentials, contractGasProvider);
  }
  
  public static Box load(String contractAddress, Web3j web3j,
                         TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return new Box(contractAddress, web3j, transactionManager, contractGasProvider);
  }
  
  public static RemoteCall<Box> deploy(Web3j web3j, Credentials credentials,
                                       ContractGasProvider contractGasProvider, BigInteger value) {
    String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(value)));
    return deployRemoteCall(Box.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
  }
  
  public static RemoteCall<Box> deploy(Web3j web3j, TransactionManager transactionManager,
                                       ContractGasProvider contractGasProvider, BigInteger value) {
    String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(value)));
    return deployRemoteCall(Box.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
  }
  
  @Deprecated
  public static RemoteCall<Box> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice,
                                       BigInteger gasLimit, BigInteger value) {
    String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(value)));
    return deployRemoteCall(Box.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
  }
  
  @Deprecated
  public static RemoteCall<Box> deploy(Web3j web3j, TransactionManager transactionManager,
                                       BigInteger gasPrice, BigInteger gasLimit, BigInteger value) {
    String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(value)));
    return deployRemoteCall(Box.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
  }
  
  public static void linkLibraries(List<Contract.LinkReference> references) {
    librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
  }
  
  private static String getDeploymentBinary() {
    if (librariesLinkedBinary != null) {
      return librariesLinkedBinary;
    } else {
      return BINARY;
    }
  }
  
  public static String getPreviouslyDeployedAddress(String networkId) {
    return _addresses.get(networkId);
  }
  
  public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
    EthFilter filter) {
    return web3j.ethLogFlowable(filter).map(log -> getOwnershipTransferredEventFromLog(log));
  }
  
  public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
    DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
    return ownershipTransferredEventFlowable(filter);
  }
  
  public Flowable<ValueChangedEventResponse> valueChangedEventFlowable(EthFilter filter) {
    return web3j.ethLogFlowable(filter).map(log -> getValueChangedEventFromLog(log));
  }
  
  public Flowable<ValueChangedEventResponse> valueChangedEventFlowable(
    DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(VALUECHANGED_EVENT));
    return valueChangedEventFlowable(filter);
  }
  
  public RemoteFunctionCall<String> call_owner() {
    final Function function = new Function(FUNC_OWNER,
      Arrays.<Type>asList(),
      Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
      }));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }
  
  public RemoteFunctionCall<TransactionReceipt> send_owner() {
    final Function function = new Function(
      FUNC_OWNER,
      Arrays.<Type>asList(),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }
  
  public RemoteFunctionCall<TransactionReceipt> send_renounceOwnership() {
    final Function function = new Function(
      FUNC_RENOUNCEOWNERSHIP,
      Arrays.<Type>asList(),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }
  
  public RemoteFunctionCall<BigInteger> call_retrieve() {
    final Function function = new Function(FUNC_RETRIEVE,
      Arrays.<Type>asList(),
      Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
      }));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }
  
  public RemoteFunctionCall<TransactionReceipt> send_retrieve() {
    final Function function = new Function(
      FUNC_RETRIEVE,
      Arrays.<Type>asList(),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }
  
  public RemoteFunctionCall<TransactionReceipt> send_store(BigInteger value) {
    final Function function = new Function(
      FUNC_STORE,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(value)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }
  
  public RemoteFunctionCall<TransactionReceipt> send_transferOwnership(String newOwner) {
    final Function function = new Function(
      FUNC_TRANSFEROWNERSHIP,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }
  
  protected String getStaticDeployedAddress(String networkId) {
    return _addresses.get(networkId);
  }
  
  public static class OwnershipTransferredEventResponse extends BaseEventResponse {
    public String previousOwner;
    
    public String newOwner;
  }
  
  public static class ValueChangedEventResponse extends BaseEventResponse {
    public BigInteger value;
  }
}
