package tool.entity;

public class SendTxInfo extends TxInfo {
	private static final long serialVersionUID = -2953256079955591061L;
	private String signedTransactionData;

	public String getSignedTransactionData() {
		return signedTransactionData;
	}

	public void setSignedTransactionData(String signedTransactionData) {
		this.signedTransactionData = signedTransactionData;
	}

	public SendTxInfo(
		TxInfo txInfo, 
		String signedTransactionData
	) {
		super();
		if(txInfo!=null) {
			this.setAddress(txInfo.getAddress());
			this.setContractAddress(txInfo.getContractAddress());
			this.setChainId(txInfo.getChainId());
			this.setEncodedFunction(txInfo.getEncodedFunction());
			this.setFunctionSelector(txInfo.getFunctionSelector());
		}
		this.setSignedTransactionData(signedTransactionData);
	}
}
