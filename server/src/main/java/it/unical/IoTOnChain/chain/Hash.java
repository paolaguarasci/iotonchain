package it.unical.IoTOnChain.chain;

import io.reactivex.Flowable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.Callable;

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
public class Hash extends Contract {
  public static final String BINARY = "0x608060405234801561001057600080fd5b50610e44806100206000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c8063031af1661461006757806309b6511c1461008357806321f0426e146100b55780638129fc1c146100e7578063d40b3ea6146100f1578063d6b3fa8a14610121575b600080fd5b610081600480360381019061007c91906106b7565b610151565b005b61009d60048036038101906100989190610713565b6102e0565b6040516100ac939291906107e7565b60405180910390f35b6100cf60048036038101906100ca9190610713565b610392565b6040516100de939291906107e7565b60405180910390f35b6100ef6104db565b005b61010b60048036038101906101069190610713565b6104dd565b6040516101189190610840565b60405180910390f35b61013b60048036038101906101369190610713565b610507565b6040516101489190610840565b60405180910390f35b6000801b8203610196576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161018d906108a7565b60405180910390fd5b6001600083815260200190815260200160002060009054906101000a900460ff16156101f7576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016101ee90610913565b60405180910390fd5b600180600084815260200190815260200160002060006101000a81548160ff021916908315150217905550604051806060016040528083815260200142815260200182815250600080848152602001908152602001600020600082015181600001556020820151816001015560408201518160020190816102789190610b3f565b509050503373ffffffffffffffffffffffffffffffffffffffff16827f6ddcdacc7853efdcdc5d1faceebf5e8e9579ef2c5d2634dece02d09a5d1ea37d6000808681526020019081526020016000206040516102d49190610d80565b60405180910390a35050565b600060205280600052604060002060009150905080600001549080600101549080600201805461030f90610962565b80601f016020809104026020016040519081016040528092919081815260200182805461033b90610962565b80156103885780601f1061035d57610100808354040283529160200191610388565b820191906000526020600020905b81548152906001019060200180831161036b57829003601f168201915b5050505050905083565b6000806060600080600086815260200190815260200160002060405180606001604052908160008201548152602001600182015481526020016002820180546103da90610962565b80601f016020809104026020016040519081016040528092919081815260200182805461040690610962565b80156104535780601f1061042857610100808354040283529160200191610453565b820191906000526020600020905b81548152906001019060200180831161043657829003601f168201915b50505050508152505090506001600086815260200190815260200160002060009054906101000a900460ff166104be576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104b590610dee565b60405180910390fd5b806000015181602001518260400151935093509350509193909250565b565b60006001600083815260200190815260200160002060009054906101000a900460ff169050919050565b60016020528060005260406000206000915054906101000a900460ff1681565b6000604051905090565b600080fd5b600080fd5b6000819050919050565b61054e8161053b565b811461055957600080fd5b50565b60008135905061056b81610545565b92915050565b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6105c48261057b565b810181811067ffffffffffffffff821117156105e3576105e261058c565b5b80604052505050565b60006105f6610527565b905061060282826105bb565b919050565b600067ffffffffffffffff8211156106225761062161058c565b5b61062b8261057b565b9050602081019050919050565b82818337600083830152505050565b600061065a61065584610607565b6105ec565b90508281526020810184848401111561067657610675610576565b5b610681848285610638565b509392505050565b600082601f83011261069e5761069d610571565b5b81356106ae848260208601610647565b91505092915050565b600080604083850312156106ce576106cd610531565b5b60006106dc8582860161055c565b925050602083013567ffffffffffffffff8111156106fd576106fc610536565b5b61070985828601610689565b9150509250929050565b60006020828403121561072957610728610531565b5b60006107378482850161055c565b91505092915050565b6107498161053b565b82525050565b6000819050919050565b6107628161074f565b82525050565b600081519050919050565b600082825260208201905092915050565b60005b838110156107a2578082015181840152602081019050610787565b60008484015250505050565b60006107b982610768565b6107c38185610773565b93506107d3818560208601610784565b6107dc8161057b565b840191505092915050565b60006060820190506107fc6000830186610740565b6108096020830185610759565b818103604083015261081b81846107ae565b9050949350505050565b60008115159050919050565b61083a81610825565b82525050565b60006020820190506108556000830184610831565b92915050565b7f486173682063616e6e6f74206265207a65726f00000000000000000000000000600082015250565b6000610891601383610773565b915061089c8261085b565b602082019050919050565b600060208201905081810360008301526108c081610884565b9050919050565b7f4861736820616c7265616479207369676e656400000000000000000000000000600082015250565b60006108fd601383610773565b9150610908826108c7565b602082019050919050565b6000602082019050818103600083015261092c816108f0565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061097a57607f821691505b60208210810361098d5761098c610933565b5b50919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b6000600883026109f57fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff826109b8565b6109ff86836109b8565b95508019841693508086168417925050509392505050565b6000819050919050565b6000610a3c610a37610a328461074f565b610a17565b61074f565b9050919050565b6000819050919050565b610a5683610a21565b610a6a610a6282610a43565b8484546109c5565b825550505050565b600090565b610a7f610a72565b610a8a818484610a4d565b505050565b5b81811015610aae57610aa3600082610a77565b600181019050610a90565b5050565b601f821115610af357610ac481610993565b610acd846109a8565b81016020851015610adc578190505b610af0610ae8856109a8565b830182610a8f565b50505b505050565b600082821c905092915050565b6000610b1660001984600802610af8565b1980831691505092915050565b6000610b2f8383610b05565b9150826002028217905092915050565b610b4882610768565b67ffffffffffffffff811115610b6157610b6061058c565b5b610b6b8254610962565b610b76828285610ab2565b600060209050601f831160018114610ba95760008415610b97578287015190505b610ba18582610b23565b865550610c09565b601f198416610bb786610993565b60005b82811015610bdf57848901518255600182019150602085019450602081019050610bba565b86831015610bfc5784890151610bf8601f891682610b05565b8355505b6001600288020188555050505b505050505050565b60008160001c9050919050565b6000819050919050565b6000610c3b610c3683610c11565b610c1e565b9050919050565b610c4b8161053b565b82525050565b6000819050919050565b6000610c6e610c6983610c11565b610c51565b9050919050565b610c7e8161074f565b82525050565b600082825260208201905092915050565b60008154610ca281610962565b610cac8186610c84565b94506001821660008114610cc75760018114610cdd57610d10565b60ff198316865281151560200286019350610d10565b610ce685610993565b60005b83811015610d0857815481890152600182019150602081019050610ce9565b808801955050505b50505092915050565b6000606083016000808401549050610d3081610c28565b610d3d6000870182610c42565b5060018401549050610d4e81610c5b565b610d5b6020870182610c75565b50600284018583036040870152610d728382610c95565b925050819250505092915050565b60006020820190508181036000830152610d9a8184610d19565b905092915050565b7f48617368206e6f74207369676e65640000000000000000000000000000000000600082015250565b6000610dd8600f83610773565b9150610de382610da2565b602082019050919050565b60006020820190508181036000830152610e0781610dcb565b905091905056fea26469706673582212204bc1daeae7357ec7c46adea8d20f63ed25a09e9d7c02cf208da6ef5228d967c064736f6c63430008180033";
  public static final String FUNC_GETSIGNDATA = "getSignData";
  public static final String FUNC_INITIALIZE = "initialize";
  public static final String FUNC_ISHASHSIGNED = "isHashSigned";
  public static final String FUNC_KEYEXISTS = "keyExists";
  public static final String FUNC_SIGNDATA = "signData";
  public static final String FUNC_SIGNHASH = "signHash";
  public static final Event HASHSIGNED_EVENT = new Event("HashSigned",
    Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {
    }, new TypeReference<Address>(true) {
    }, new TypeReference<SignData>() {
    }));
  public static final Event INITIALIZED_EVENT = new Event("Initialized",
    Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {
    }));
  ;
  protected static final HashMap<String, String> _addresses;
  ;
  private static String librariesLinkedBinary;
  
  static {
    _addresses = new HashMap<String, String>();
  }
  
  @Deprecated
  protected Hash(String contractAddress, Web3j web3j, Credentials credentials,
                 BigInteger gasPrice, BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }
  
  protected Hash(String contractAddress, Web3j web3j, Credentials credentials,
                 ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }
  
  @Deprecated
  protected Hash(String contractAddress, Web3j web3j, TransactionManager transactionManager,
                 BigInteger gasPrice, BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }
  
  protected Hash(String contractAddress, Web3j web3j, TransactionManager transactionManager,
                 ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
  }
  
  public static List<HashSignedEventResponse> getHashSignedEvents(
    TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(HASHSIGNED_EVENT, transactionReceipt);
    ArrayList<HashSignedEventResponse> responses = new ArrayList<HashSignedEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      HashSignedEventResponse typedResponse = new HashSignedEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.hash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
      typedResponse.signer = (String) eventValues.getIndexedValues().get(1).getValue();
      typedResponse.data = (SignData) eventValues.getNonIndexedValues().get(0);
      responses.add(typedResponse);
    }
    return responses;
  }
  
  public static HashSignedEventResponse getHashSignedEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(HASHSIGNED_EVENT, log);
    HashSignedEventResponse typedResponse = new HashSignedEventResponse();
    typedResponse.log = log;
    typedResponse.hash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
    typedResponse.signer = (String) eventValues.getIndexedValues().get(1).getValue();
    typedResponse.data = (SignData) eventValues.getNonIndexedValues().get(0);
    return typedResponse;
  }
  
  public static List<InitializedEventResponse> getInitializedEvents(
    TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(INITIALIZED_EVENT, transactionReceipt);
    ArrayList<InitializedEventResponse> responses = new ArrayList<InitializedEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      InitializedEventResponse typedResponse = new InitializedEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.version = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }
  
  public static InitializedEventResponse getInitializedEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(INITIALIZED_EVENT, log);
    InitializedEventResponse typedResponse = new InitializedEventResponse();
    typedResponse.log = log;
    typedResponse.version = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
    return typedResponse;
  }
  
  @Deprecated
  public static Hash load(String contractAddress, Web3j web3j, Credentials credentials,
                          BigInteger gasPrice, BigInteger gasLimit) {
    return new Hash(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }
  
  @Deprecated
  public static Hash load(String contractAddress, Web3j web3j,
                          TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
    return new Hash(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }
  
  public static Hash load(String contractAddress, Web3j web3j, Credentials credentials,
                          ContractGasProvider contractGasProvider) {
    return new Hash(contractAddress, web3j, credentials, contractGasProvider);
  }
  
  public static Hash load(String contractAddress, Web3j web3j,
                          TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return new Hash(contractAddress, web3j, transactionManager, contractGasProvider);
  }
  
  public static RemoteCall<Hash> deploy(Web3j web3j, Credentials credentials,
                                        ContractGasProvider contractGasProvider) {
    return deployRemoteCall(Hash.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
  }
  
  @Deprecated
  public static RemoteCall<Hash> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice,
                                        BigInteger gasLimit) {
    return deployRemoteCall(Hash.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
  }
  
  public static RemoteCall<Hash> deploy(Web3j web3j, TransactionManager transactionManager,
                                        ContractGasProvider contractGasProvider) {
    return deployRemoteCall(Hash.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
  }
  
  @Deprecated
  public static RemoteCall<Hash> deploy(Web3j web3j, TransactionManager transactionManager,
                                        BigInteger gasPrice, BigInteger gasLimit) {
    return deployRemoteCall(Hash.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
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
  
  public Flowable<HashSignedEventResponse> hashSignedEventFlowable(EthFilter filter) {
    return web3j.ethLogFlowable(filter).map(log -> getHashSignedEventFromLog(log));
  }
  
  public Flowable<HashSignedEventResponse> hashSignedEventFlowable(
    DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(HASHSIGNED_EVENT));
    return hashSignedEventFlowable(filter);
  }
  
  public Flowable<InitializedEventResponse> initializedEventFlowable(EthFilter filter) {
    return web3j.ethLogFlowable(filter).map(log -> getInitializedEventFromLog(log));
  }
  
  public Flowable<InitializedEventResponse> initializedEventFlowable(
    DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(INITIALIZED_EVENT));
    return initializedEventFlowable(filter);
  }
  
  public RemoteFunctionCall<Tuple3<byte[], BigInteger, String>> call_getSignData(
    byte[] dataHash) {
    final Function function = new Function(FUNC_GETSIGNDATA,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(dataHash)),
      Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
      }, new TypeReference<Uint256>() {
      }, new TypeReference<Utf8String>() {
      }));
    return new RemoteFunctionCall<Tuple3<byte[], BigInteger, String>>(function,
      new Callable<Tuple3<byte[], BigInteger, String>>() {
        @Override
        public Tuple3<byte[], BigInteger, String> call() throws Exception {
          List<Type> results = executeCallMultipleValueReturn(function);
          return new Tuple3<byte[], BigInteger, String>(
            (byte[]) results.get(0).getValue(),
            (BigInteger) results.get(1).getValue(),
            (String) results.get(2).getValue());
        }
      });
  }
  
  public RemoteFunctionCall<TransactionReceipt> send_getSignData(byte[] dataHash) {
    final Function function = new Function(
      FUNC_GETSIGNDATA,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(dataHash)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }
  
  public RemoteFunctionCall<TransactionReceipt> send_initialize() {
    final Function function = new Function(
      FUNC_INITIALIZE,
      Arrays.<Type>asList(),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }
  
  public RemoteFunctionCall<Boolean> call_isHashSigned(byte[] hash) {
    final Function function = new Function(FUNC_ISHASHSIGNED,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(hash)),
      Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
      }));
    return executeRemoteCallSingleValueReturn(function, Boolean.class);
  }
  
  public RemoteFunctionCall<TransactionReceipt> send_isHashSigned(byte[] hash) {
    final Function function = new Function(
      FUNC_ISHASHSIGNED,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(hash)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }
  
  public RemoteFunctionCall<Boolean> call_keyExists(byte[] param0) {
    final Function function = new Function(FUNC_KEYEXISTS,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)),
      Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
      }));
    return executeRemoteCallSingleValueReturn(function, Boolean.class);
  }
  
  public RemoteFunctionCall<TransactionReceipt> send_keyExists(byte[] param0) {
    final Function function = new Function(
      FUNC_KEYEXISTS,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }
  
  public RemoteFunctionCall<Tuple3<byte[], BigInteger, String>> call_signData(byte[] param0) {
    final Function function = new Function(FUNC_SIGNDATA,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)),
      Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
      }, new TypeReference<Uint256>() {
      }, new TypeReference<Utf8String>() {
      }));
    return new RemoteFunctionCall<Tuple3<byte[], BigInteger, String>>(function,
      new Callable<Tuple3<byte[], BigInteger, String>>() {
        @Override
        public Tuple3<byte[], BigInteger, String> call() throws Exception {
          List<Type> results = executeCallMultipleValueReturn(function);
          return new Tuple3<byte[], BigInteger, String>(
            (byte[]) results.get(0).getValue(),
            (BigInteger) results.get(1).getValue(),
            (String) results.get(2).getValue());
        }
      });
  }
  
  public RemoteFunctionCall<TransactionReceipt> send_signData(byte[] param0) {
    final Function function = new Function(
      FUNC_SIGNDATA,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }
  
  public RemoteFunctionCall<TransactionReceipt> send_signHash(byte[] hash, String data) {
    final Function function = new Function(
      FUNC_SIGNHASH,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(hash),
        new org.web3j.abi.datatypes.Utf8String(data)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }
  
  protected String getStaticDeployedAddress(String networkId) {
    return _addresses.get(networkId);
  }
  
  public static class SignData extends DynamicStruct {
    public byte[] hashSigned;
    
    public BigInteger timestamp;
    
    public String data;
    
    public SignData(byte[] hashSigned, BigInteger timestamp, String data) {
      super(new org.web3j.abi.datatypes.generated.Bytes32(hashSigned),
        new org.web3j.abi.datatypes.generated.Uint256(timestamp),
        new org.web3j.abi.datatypes.Utf8String(data));
      this.hashSigned = hashSigned;
      this.timestamp = timestamp;
      this.data = data;
    }
    
    public SignData(Bytes32 hashSigned, Uint256 timestamp, Utf8String data) {
      super(hashSigned, timestamp, data);
      this.hashSigned = hashSigned.getValue();
      this.timestamp = timestamp.getValue();
      this.data = data.getValue();
    }
  }
  
  public static class HashSignedEventResponse extends BaseEventResponse {
    public byte[] hash;
    
    public String signer;
    
    public SignData data;
  }
  
  public static class InitializedEventResponse extends BaseEventResponse {
    public BigInteger version;
  }
}
