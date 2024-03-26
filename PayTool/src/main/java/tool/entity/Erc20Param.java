package tool.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class Erc20Param implements Serializable{
	private static final long serialVersionUID = -8027583189558614343L;
	private List<String> erc20s;
	private List<String> tos; 
	private List<BigInteger> values;
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
}
