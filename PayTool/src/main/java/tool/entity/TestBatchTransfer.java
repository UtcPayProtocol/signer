package tool.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class TestBatchTransfer implements Serializable{
	private static final long serialVersionUID = 1580346195051786478L;
	private String chainId;
	private List<String> erc20s;
	private List<String> tos;
	private List<BigInteger> values;
	private List<String> ethTos;
	private List<BigInteger> ethValues;
	
	public String getChainId() {
		return chainId;
	}
	public void setChainId(String chainId) {
		this.chainId = chainId;
	}
	public List<String> getErc20s() {
		return erc20s;
	}
	public void setErc20s(List<String> erc20s) {
		this.erc20s = erc20s;
	}
	public List<String> getTos() {
		return tos;
	}
	public void setTos(List<String> tos) {
		this.tos = tos;
	}
	public List<BigInteger> getValues() {
		return values;
	}
	public void setValues(List<BigInteger> values) {
		this.values = values;
	}
	public List<String> getEthTos() {
		return ethTos;
	}
	public void setEthTos(List<String> ethTos) {
		this.ethTos = ethTos;
	}
	public List<BigInteger> getEthValues() {
		return ethValues;
	}
	public void setEthValues(List<BigInteger> ethValues) {
		this.ethValues = ethValues;
	}
	
}
