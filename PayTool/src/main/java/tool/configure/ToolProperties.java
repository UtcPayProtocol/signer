package tool.configure;

import static tool.configure.ToolProperties.TOOL_PREFIX;

import java.math.BigInteger;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = TOOL_PREFIX)
public class ToolProperties {
	public static final String TOOL_PREFIX = "tool";
	private List<String> chainIds;
	private List<String> contractAddrs;
	private String getInfoUrl;
	private String sendTransactionUrl;
	private String keystore;
	//TransactionTool.encodePassword(password)
	private String password;
	private BigInteger gasPrice;
	private BigInteger gasLimit;
	public List<String> getChainIds() {
		return chainIds;
	}
	public void setChainIds(List<String> chainIds) {
		this.chainIds = chainIds;
	}
	public List<String> getContractAddrs() {
		return contractAddrs;
	}
	public void setContractAddrs(List<String> contractAddrs) {
		this.contractAddrs = contractAddrs;
	}
	public String getGetInfoUrl() {
		return getInfoUrl;
	}
	public void setGetInfoUrl(String getInfoUrl) {
		this.getInfoUrl = getInfoUrl;
	}
	public String getSendTransactionUrl() {
		return sendTransactionUrl;
	}
	public void setSendTransactionUrl(String sendTransactionUrl) {
		this.sendTransactionUrl = sendTransactionUrl;
	}
	public String getKeystore() {
		return keystore;
	}
	public void setKeystore(String keystore) {
		this.keystore = keystore;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
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
	
	
}
