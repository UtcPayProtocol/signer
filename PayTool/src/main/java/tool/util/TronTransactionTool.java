package tool.util;

import java.math.BigInteger;

import org.bouncycastle.jcajce.provider.digest.SHA256;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.key.KeyPair;
import org.tron.trident.core.transaction.TransactionBuilder;
import org.tron.trident.proto.Chain.Transaction;
import org.tron.trident.utils.Numeric;

import com.google.protobuf.ByteString;

import tool.entity.Info;
import tool.entity.Result;
import tool.entity.SendTxInfo;
import tool.entity.TxInfo;


public class TronTransactionTool {
	public static Result<Info> getInfo(TxInfo txInfo){
		Result<Info> result = new Result<Info>();
		try {
			ApiWrapper client = ApiWrapper.ofMainnet("0x123","0x1");
			TransactionBuilder triggerCallV2 = client.triggerCallV2(txInfo.getAddress(), 
					txInfo.getContractAddress(), txInfo.getEncodedFunction());
			Transaction transaction = triggerCallV2.build();
			String txHash = Numeric.toHexString(calculateTransactionHash(transaction));
			Info info = new Info(BigInteger.ZERO,BigInteger.ZERO,BigInteger.ZERO,txHash);
			result.setData(info);
			result.setCode(TransactionTool.SUCCESS);
			result.setMessage(TransactionTool.SUCCESS_MSG);
		} catch (Exception e) {
			result.setCode(TransactionTool.FAIL);
			result.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	public static Result<String> sendTransaction(SendTxInfo sendTxInfo){
		Result<String> result = new Result<String>();
		try {
			ApiWrapper client = ApiWrapper.ofMainnet("0x123","0x1");
			TransactionBuilder triggerCallV2 = client.triggerCallV2(
					sendTxInfo.getAddress(), 
					sendTxInfo.getContractAddress(), 
					sendTxInfo.getEncodedFunction()
			);
			Transaction transaction = triggerCallV2.build();
			String signedTransactionData = sendTxInfo.getSignedTransactionData();
			byte[] signature = Numeric.hexStringToByteArray(signedTransactionData);
			Transaction signedTxn = transaction.toBuilder().addSignature(ByteString.copyFrom(signature)).build();
			client.broadcastTransaction(signedTxn);
		} catch (Exception e) {
			result.setCode(TransactionTool.FAIL);
			result.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	public static String signTransaction(String txHash, String privateKey)throws Exception {
		KeyPair keyPair = new KeyPair(privateKey);
        byte[] txid = Numeric.hexStringToByteArray(txHash);
        byte[] signature = KeyPair.signTransaction(txid, keyPair);
        return Numeric.toHexString(signature);
    }
	private static byte[] calculateTransactionHash (Transaction txn) {
        SHA256.Digest digest = new SHA256.Digest();
        digest.update(txn.getRawData().toByteArray());
        byte[] txid = digest.digest();

        return txid;
    }
	
}
