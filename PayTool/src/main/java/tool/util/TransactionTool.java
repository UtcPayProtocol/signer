package tool.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.utils.Numeric;

import tool.configure.ToolProperties;
import tool.entity.Info;
import tool.entity.Result;
import tool.entity.SendTxInfo;
import tool.entity.TxInfo;

@Component
public class TransactionTool {
	public static final Logger log = LoggerFactory.getLogger(TransactionTool.class);
	public static final String SUCCESS = "1";
	public static final String SUCCESS_MSG = "SUCCESS";
	public static final String FAIL = "0";
	public static Credentials create;
	@Autowired
	private ToolProperties toolProperties;

	public Credentials getCredentials() {
		if (create == null) {
			try {
				String source = toolProperties.getKeystore();
				String password = SecurityUtils.decodeAESBySalt(toolProperties.getPassword(), salt);
				create = WalletUtils.loadCredentials(password, source);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return create;
	}

	public static String address;

	public String getAddress() {
		if (address == null) {
			address = getCredentials().getAddress();
		}
		return address;
	}
	
	public static String encodePassword(String password) {
		return SecurityUtils.encodeAESBySalt(password, salt);
	}
	public SendTxInfo sendEVMErc20s(List<String> erc20s, 
		List<String> tos, 
		List<BigInteger> values
	) throws Exception{
		byte[] transactions = getSendErc20Transactions(erc20s,tos,values);
		
		TxInfo txInfo = getEVMtxInfo(transactions);
		Result<Info> result = getInfo(txInfo);
		return sendEvmTransaction(txInfo,result.getData());
	}
	public SendTxInfo sendTVMErc20s(List<String> erc20s, 
			List<String> tos, 
			List<BigInteger> values
			) throws Exception{
		byte[] transactions = getSendErc20Transactions(erc20s,tos,values);
		
		TxInfo txInfo = getTVMtxInfo(transactions);
		Result<Info> result = getInfo(txInfo);
		return sendTvmTransaction(txInfo,result.getData());
	}
	
	public SendTxInfo sendEVMTransactions(
		List<String> erc20s, 
		List<String> tos, 
		List<BigInteger> values,
		List<String> ethTos, 
		List<BigInteger> ethValues
	) throws Exception{
		byte[] transactions = getSendTransactions(erc20s,tos,values,ethTos,ethValues);
		TxInfo txInfo = getEVMtxInfo(transactions);
		Result<Info> result = getInfo(txInfo);
		return sendEvmTransaction(txInfo,result.getData());
	}
	public SendTxInfo sendTVMTransactions(
			List<String> erc20s, 
			List<String> tos, 
			List<BigInteger> values,
			List<String> ethTos, 
			List<BigInteger> ethValues
			) throws Exception{
		byte[] transactions = getSendTransactions(erc20s,tos,values,ethTos,ethValues);
		TxInfo txInfo = getTVMtxInfo(transactions);
		Result<Info> result = getInfo(txInfo);
		return sendTvmTransaction(txInfo,result.getData());
	}
	public SendTxInfo sendEVMEths(
		List<String> tos, 
		List<BigInteger> values
	) throws Exception{
		byte[] erc20Transactions = getSendEthTransactions(tos,values);
		TxInfo txInfo = getEVMtxInfo(erc20Transactions);
		Result<Info> result = getInfo(txInfo);
		return sendEvmTransaction(txInfo,result.getData());
	}
	public SendTxInfo sendTVMEths(
			List<String> tos, 
			List<BigInteger> values
			) throws Exception{
		byte[] erc20Transactions = getSendEthTransactions(tos,values);
		TxInfo txInfo = getTVMtxInfo(erc20Transactions);
		Result<Info> result = getInfo(txInfo);
		return sendEvmTransaction(txInfo,result.getData());
	}
	@SuppressWarnings("rawtypes")
	private TxInfo getEVMtxInfo(byte[] transactions) {
		Function function = new Function(
	            "multiSend", 
	            Arrays.<Type>asList(new DynamicBytes(transactions)), 
	            Collections.<TypeReference<?>>emptyList()
	        );
		TxInfo txInfo = new TxInfo();
		txInfo.setAddress(getAddress());
		txInfo.setChainId(toolProperties.getChainIds().get(0));
		txInfo.setContractAddress(toolProperties.getContractAddrs().get(0));
		txInfo.setEncodedFunction(FunctionEncoder.encode(function));
		String methodSignature = buildMethodSignature(function.getName(), function.getInputParameters());
		txInfo.setFunctionSelector(methodSignature);
		return txInfo;
	}
	@SuppressWarnings("rawtypes")
	private TxInfo getTVMtxInfo(byte[] transactions) {
		Function function = new Function(
				"multiSend", 
				Arrays.<Type>asList(new DynamicBytes(transactions)), 
				Collections.<TypeReference<?>>emptyList()
				);
		TxInfo txInfo = new TxInfo();
		txInfo.setAddress(getAddress());
		txInfo.setChainId(toolProperties.getChainIds().get(1));
		txInfo.setContractAddress(toolProperties.getContractAddrs().get(1));
		txInfo.setEncodedFunction(FunctionEncoder.encode(function));
		String methodSignature = buildMethodSignature(function.getName(), function.getInputParameters());
		txInfo.setFunctionSelector(methodSignature);
		return txInfo;
	}
	private static String getTransferData(
		String token,
		String to,
		BigInteger value
	) {
		@SuppressWarnings("rawtypes")
		Function function = new Function(
            "transfer", 
            Arrays.<Type>asList(new Address(to),new Uint256(value)), 
            Collections.<TypeReference<?>>emptyList()
        );
		String data = FunctionEncoder.encode(function);
		BigInteger dataLength=new BigInteger(Numeric.hexStringToByteArray(data).length+"");
		StringBuilder sb=new StringBuilder("");
		sb.append(Numeric.cleanHexPrefix(token));
		sb.append(Numeric.cleanHexPrefix(TypeEncoder.encodePacked(new Uint256(BigInteger.ZERO))));
		sb.append(Numeric.cleanHexPrefix(TypeEncoder.encodePacked(new Uint256(dataLength))));
		sb.append(Numeric.cleanHexPrefix(data));
		return sb.toString();
	}
	private static String getEthTransferData(
		String to,
		BigInteger value
	) {
		StringBuilder sb=new StringBuilder("");
		sb.append(Numeric.cleanHexPrefix(to));
		sb.append(Numeric.cleanHexPrefix(TypeEncoder.encodePacked(new Uint256(value))));
		sb.append(Numeric.cleanHexPrefix(TypeEncoder.encodePacked(new Uint256(0))));
		return sb.toString();
	}
	
	public static byte[] getSendTransactions(
		List<String> erc20s, 
		List<String> tos, 
		List<BigInteger> values,
		List<String> ethTos, 
		List<BigInteger> ethValues
	)throws Exception {
		StringBuilder sb=new StringBuilder("0x");
		if(erc20s.size()==tos.size()&&tos.size()==values.size()) {
			for(int i=0;i<erc20s.size();i++) {
				sb.append(getTransferData(erc20s.get(i),tos.get(i),values.get(i)));
			}
		}
		if(ethTos.size()==ethValues.size()) {
			for(int i=0;i<ethTos.size();i++) {
				sb.append(getEthTransferData(ethTos.get(i),ethValues.get(i)));
			}
		}
		System.out.println(sb.toString());
		return Numeric.hexStringToByteArray(sb.toString());
	}
	public static byte[] getSendErc20Transactions(
		List<String> erc20s, 
		List<String> tos, 
		List<BigInteger> values
	)throws Exception {
		StringBuilder sb=new StringBuilder("0x");
		if(erc20s.size()==tos.size()&&tos.size()==values.size()) {
			for(int i=0;i<erc20s.size();i++) {
				sb.append(getTransferData(erc20s.get(i),tos.get(i),values.get(i)));
			}
		}
		return Numeric.hexStringToByteArray(sb.toString());
	}
	public static byte[] getSendEthTransactions(
		List<String> tos, 
		List<BigInteger> values
	)throws Exception {
		StringBuilder sb=new StringBuilder("0x");
		if(tos.size()==values.size()) {
			for(int i=0;i<tos.size();i++) {
				sb.append(getEthTransferData(tos.get(i),values.get(i)));
			}
		}
		return Numeric.hexStringToByteArray(sb.toString());
	}

	@SuppressWarnings({ "unchecked" })
	private Result<Info> getInfo(TxInfo txInfo) throws Exception {
		String response = doPostJson(toolProperties.getGetInfoUrl(), ObjectJsonHelper.serialize(txInfo));
		return ObjectJsonHelper.deserialize(response, Result.class);
	}
	@SuppressWarnings("rawtypes")
	private static String buildMethodSignature(
            final String methodName, final List<Type> parameters) {
        final StringBuilder result = new StringBuilder();
        result.append(methodName);
        result.append("(");
        final String params =
                parameters.stream().map(Type::getTypeAsString).collect(Collectors.joining(","));
        result.append(params);
        result.append(")");
        return result.toString();
    }
	private static final String salt = "wCwxDBGs3A0vWFdsRA6UK9kdKqyjAvTK";
	@SuppressWarnings({ "unchecked"})
	private SendTxInfo sendEvmTransaction(
		TxInfo txInfo,
		Info info
	) throws Exception {
		BigInteger nonce = info.getNonce();
		BigInteger gasPrice = info.getGasPrice();
		BigInteger gasLimit = info.getGasLimit();
		Long chainId = Numeric.toBigInt(txInfo.getChainId()).longValue();
		String contractAddr = txInfo.getContractAddress();
		BigInteger value = BigInteger.ZERO;
		String data = txInfo.getEncodedFunction();
		RawTransaction rawTransaction = RawTransaction.createTransaction(
			nonce, 
			gasPrice, 
			gasLimit, 
			contractAddr, 
			value,
			data
		);
		String signedTransactionData = Numeric.toHexString(
				TransactionEncoder.signMessage(rawTransaction, chainId, getCredentials()));
		return new SendTxInfo(txInfo,signedTransactionData);
//		SendTxInfo sendTxInfo=new SendTxInfo(txInfo,signedTransactionData);
//		String response = doPostJson(toolProperties.getSendTransactionUrl(), ObjectJsonHelper.serialize(sendTxInfo));
//		return ObjectJsonHelper.deserialize(response, Result.class);
	}
	@SuppressWarnings({ "unchecked"})
	private SendTxInfo sendTvmTransaction(
			TxInfo txInfo,
			Info info
			) throws Exception {
		String signedTransactionData = TronTransactionTool.signTransaction(info.getTxHash(),
				Numeric.encodeQuantity(getCredentials().getEcKeyPair().getPrivateKey())
		);
		return new SendTxInfo(txInfo,null);
//		SendTxInfo sendTxInfo=new SendTxInfo(txInfo,signedTransactionData);
//		String response = doPostJson(toolProperties.getSendTransactionUrl(), ObjectJsonHelper.serialize(sendTxInfo));
//		return ObjectJsonHelper.deserialize(response, Result.class);
	}
	public static String doPostJson(String url, String json) {
		System.out.println(url);
		System.out.println(json);
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.create("application/json", "utf-8"));
			httpPost.setEntity(entity);
			httpPost.setHeader("Accept", "application/json");
			// httpPost.setHeader("SessionId", sessionId);
			// 构建超时等配置信息
			RequestConfig config = RequestConfig.custom().setConnectTimeout(10000000) // 连接超时时间
					.setConnectionRequestTimeout(1000000) // 从连接池中取的连接的最长时间
					.setSocketTimeout(10 * 1000000) // 数据传输的超时时间
					.build();
			// 设置请求配置时间
			httpPost.setConfig(config);
			// 执行http请求
			response = httpClient.execute(httpPost);
			response.setHeader("Accept", "application/json");
			response.setHeader("Content-Type", "application/json;charset=utf-8");
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
			resultString = resultString.replaceAll("\\r\\n", " ");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}
}