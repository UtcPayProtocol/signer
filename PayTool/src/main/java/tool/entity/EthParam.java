package tool.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class EthParam implements Serializable{
	private static final long serialVersionUID = 2846579686720528101L;
	private List<String> ethTos;
	private List<BigInteger> ethValues;
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
