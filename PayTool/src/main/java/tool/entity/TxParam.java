package tool.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

public class TxParam implements Serializable{
	private static final long serialVersionUID = 165294370362149876L;
	private List<String> erc20s;
	private List<String> tos; 
	private List<BigInteger> values;
	private List<String> ethTos;
	private List<BigInteger> ethValues;
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
