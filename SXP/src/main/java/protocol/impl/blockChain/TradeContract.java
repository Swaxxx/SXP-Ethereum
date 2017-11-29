package protocol.impl.blockChain;

import java.io.IOException;
import java.lang.String;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

public final class TradeContract extends Contract {
    public static final String BINARY = "606060405234156200001057600080fd5b604051620012af380380620012af8339810160405280805190602001909190805190602001909190805182019190602001805182019190602001805182019190602001805182019190505033600860006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600860009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614806200014957508573ffffffffffffffffffffffffffffffffffffffff166000800160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614155b15156200015557600080fd5b856000800160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508360006001019080519060200190620001b392919062000252565b5084600360000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555082600360010190805190602001906200021392919062000252565b5081600690805190602001906200022c92919062000252565b5080600790805190602001906200024592919062000252565b5050505050505062000301565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200029557805160ff1916838001178555620002c6565b82800160010185558215620002c6579182015b82811115620002c5578251825591602001919060010190620002a8565b5b509050620002d59190620002d9565b5090565b620002fe91905b80821115620002fa576000816000905550600101620002e0565b5090565b90565b610f9e80620003116000396000f3006060604052600436106100d0576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630e9032df146100d557806318a2985b146101be57806351ff48471461024c5780635e01eb5a1461026157806360ccba55146102b657806367e404ce146103445780636cc7deba1461039957806381ea32a2146104275780638a4e3769146104b55780638dc716c5146104e2578063e521c2e914610570578063e64653e0146105c5578063ef78053814610653578063f029c24b146106a8575b600080fd5b34156100e057600080fd5b6100e8610791565b604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001831515151581526020018281038252848181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156101ad5780601f10610182576101008083540402835291602001916101ad565b820191906000526020600020905b81548152906001019060200180831161019057829003601f168201915b505094505050505060405180910390f35b34156101c957600080fd5b6101d16107d5565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156102115780820151818401526020810190506101f6565b50505050905090810190601f16801561023e5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561025757600080fd5b61025f610880565b005b341561026c57600080fd5b610274610a04565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156102c157600080fd5b6102c9610a2e565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156103095780820151818401526020810190506102ee565b50505050905090810190601f1680156103365780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561034f57600080fd5b610357610ad6565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156103a457600080fd5b6103ac610afc565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156103ec5780820151818401526020810190506103d1565b50505050905090810190601f1680156104195780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561043257600080fd5b61043a610ba7565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561047a57808201518184015260208101905061045f565b50505050905090810190601f1680156104a75780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34156104c057600080fd5b6104c8610c45565b604051808215151515815260200191505060405180910390f35b34156104ed57600080fd5b6104f5610d7b565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561053557808201518184015260208101905061051a565b50505050905090810190601f1680156105625780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561057b57600080fd5b610583610e19565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156105d057600080fd5b6105d8610e45565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156106185780820151818401526020810190506105fd565b50505050905090810190601f1680156106455780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561065e57600080fd5b610666610eed565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156106b357600080fd5b6106bb610f1a565b604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001831515151581526020018281038252848181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156107805780601f1061075557610100808354040283529160200191610780565b820191906000526020600020905b81548152906001019060200180831161076357829003601f168201915b505094505050505060405180910390f35b60008060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169080600101908060020160009054906101000a900460ff16905083565b6107dd610f5e565b60006001018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156108765780601f1061084b57610100808354040283529160200191610876565b820191906000526020600020905b81548152906001019060200180831161085957829003601f168201915b5050505050905090565b33600860006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600860009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166000800160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141561095e576001600060020160006101000a81548160ff021916908315150217905550610a02565b600860009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16600360000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614156109fc576001600360020160006101000a81548160ff021916908315150217905550610a01565b600080fd5b5b565b6000600860009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b610a36610f5e565b60068054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610acc5780601f10610aa157610100808354040283529160200191610acc565b820191906000526020600020905b815481529060010190602001808311610aaf57829003601f168201915b5050505050905090565b600860009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b610b04610f5e565b60036001018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610b9d5780601f10610b7257610100808354040283529160200191610b9d565b820191906000526020600020905b815481529060010190602001808311610b8057829003601f168201915b5050505050905090565b60068054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610c3d5780601f10610c1257610100808354040283529160200191610c3d565b820191906000526020600020905b815481529060010190602001808311610c2057829003601f168201915b505050505081565b60008060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16600860009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161480610d395750600360000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16600860009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16145b15610d7357600060020160009054906101000a900460ff168015610d6c5750600360020160009054906101000a900460ff165b9050610d78565b600080fd5b90565b60078054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610e115780601f10610de657610100808354040283529160200191610e11565b820191906000526020600020905b815481529060010190602001808311610df457829003601f168201915b505050505081565b60008060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b610e4d610f5e565b60078054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610ee35780601f10610eb857610100808354040283529160200191610ee3565b820191906000526020600020905b815481529060010190602001808311610ec657829003601f168201915b5050505050905090565b6000600360000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b60038060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169080600101908060020160009054906101000a900460ff16905083565b6020604051908101604052806000815250905600a165627a7a72305820de0ce1c53c647513fe6311812db9f4145d5fd7fea1e18249887692082c923b0e0029";

    private TradeContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
    	super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private TradeContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    @SuppressWarnings("rawtypes")
	public RemoteCall<Address> getSender() throws IOException {
        Function function = new Function("getSender", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    @SuppressWarnings("rawtypes")
    public RemoteCall<Bool> sign() {
        Function function = new Function("sign", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    @SuppressWarnings("rawtypes")
    public RemoteCall<Bool> isSigned() {
        Function function = new Function("isSigned", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    @SuppressWarnings("rawtypes")
    public RemoteCall<Address> getAddr1() throws IOException {
        Function function = new Function("getAddr1", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    @SuppressWarnings("rawtypes")
    public RemoteCall<Address> getAddr2() throws IOException {
        Function function = new Function("getAddr2", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    @SuppressWarnings("rawtypes")
    public RemoteCall<Utf8String> getItem1() throws IOException {
        Function function = new Function("getItem1", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    @SuppressWarnings("rawtypes")
    public RemoteCall<Utf8String> getItem2() throws IOException {
        Function function = new Function("getItem2", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    @SuppressWarnings("rawtypes")
    public RemoteCall<Utf8String> getClause1() throws IOException {
        Function function = new Function("getClause1", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    @SuppressWarnings("rawtypes")
    public RemoteCall<Utf8String> getClause2() throws IOException {
        Function function = new Function("getClause2", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    @SuppressWarnings("rawtypes")
    public RemoteCall<TransactionReceipt> kill() throws IOException, TransactionException {
		Function function = new Function("kill", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @SuppressWarnings("rawtypes")
    public static RemoteCall<TradeContract> deploy(Web3j web3j, Credentials credentials,
    		BigInteger gasPrice, BigInteger gasLimit, BigInteger initialValue,
    		Address part1, Address part2, Utf8String item1,
    		Utf8String item2, Utf8String clause1, Utf8String clause2) {
    	
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(part1, part2, item1, item2, clause1, clause2));
        return deployRemoteCall(TradeContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor, initialValue);
    }

    @SuppressWarnings("rawtypes")
    public static RemoteCall<TradeContract> deploy(Web3j web3j, TransactionManager transactionManager,
    		BigInteger gasPrice, BigInteger gasLimit, BigInteger initialValue,
    		Address part1, Address part2, Utf8String item1,
    		Utf8String item2, Utf8String clause1, Utf8String clause2) {
    	
    	String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(part1, part2, item1, item2, clause1, clause2));
        return deployRemoteCall(TradeContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor, initialValue);
    }

    public static TradeContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TradeContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static TradeContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TradeContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
