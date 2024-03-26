package tool.entity;

import java.io.Serializable;

public class TxInfo implements Serializable{
	private static final long serialVersionUID = -3225641645652036735L;
	private String address;  
	private String contractAddress;  
	private String chainId;  
	private String encodedFunction;
	private String functionSelector;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContractAddress() {
		return contractAddress;
	}
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	public String getChainId() {
		return chainId;
	}
	public void setChainId(String chainId) {
		this.chainId = chainId;
	}
	public String getEncodedFunction() {
		return encodedFunction;
	}
	public void setEncodedFunction(String encodedFunction) {
		this.encodedFunction = encodedFunction;
	}
	public String getFunctionSelector() {
		return functionSelector;
	}
	public void setFunctionSelector(String functionSelector) {
		this.functionSelector = functionSelector;
	}
	
	
}
