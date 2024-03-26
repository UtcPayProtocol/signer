package tool.entity;

import java.io.Serializable;
import java.math.BigInteger;

public class Info implements Serializable{
	private static final long serialVersionUID = 4222867948845211932L;
	private BigInteger gasPrice;
	private BigInteger gasLimit;
	private BigInteger nonce;
	private String txHash;
	public BigInteger getGasPrice() {
		return gasPrice;
	}
	public void setGasPrice(BigInteger gasPrice) {
		this.gasPrice = gasPrice;
	}
	public BigInteger getGasLimit() {
		return gasLimit;
	}
	public void setGasLimit(BigInteger gasLimit) {
		this.gasLimit = gasLimit;
	}
	public BigInteger getNonce() {
		return nonce;
	}
	public void setNonce(BigInteger nonce) {
		this.nonce = nonce;
	}
	
	public String getTxHash() {
		return txHash;
	}
	public void setTxHash(String txHash) {
		this.txHash = txHash;
	}
	public Info(BigInteger gasPrice, BigInteger gasLimit, BigInteger nonce, String txHash) {
		super();
		this.gasPrice = gasPrice;
		this.gasLimit = gasLimit;
		this.nonce = nonce;
		this.txHash = txHash;
	}
	
}
