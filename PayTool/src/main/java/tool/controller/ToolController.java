package tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import tool.entity.Erc20Param;
import tool.entity.EthParam;
import tool.entity.Result;
import tool.entity.SendTxInfo;
import tool.entity.TxParam;
import tool.util.TransactionTool;

@RestController
@RequestMapping("/")
public class ToolController {
	public ToolController() {
		super();
	}
	@Autowired
	private TransactionTool transactionTool;
	
	@ApiOperation(value = "批量发送evmERC20", notes = "获取批量发送evmERC20签名和参数信息")
	@PostMapping("/sendEVMErc20s")
	@GetMapping("/sendEVMErc20s")
	public Result<SendTxInfo> sendEVMErc20s(@RequestBody Erc20Param erc20Param) throws Exception {
		System.out.println(erc20Param.getErc20s());
		System.out.println(erc20Param.getTos());
		System.out.println(erc20Param.getValues());
		Result<SendTxInfo> result = new Result<SendTxInfo>();
		try {
			SendTxInfo sendTxInfo = transactionTool.sendEVMErc20s(
					erc20Param.getErc20s(), erc20Param.getTos(), erc20Param.getValues());
			result.setData(sendTxInfo);
			result.setMessage(TransactionTool.SUCCESS_MSG);
			result.setCode(TransactionTool.SUCCESS);
		} catch (Exception e) {
			result.setCode(TransactionTool.FAIL);
			result.setMessage(TransactionTool.FAIL);
			e.printStackTrace();
		}
		result.setMessage(TransactionTool.SUCCESS_MSG);
		result.setCode(TransactionTool.SUCCESS);
		
		return result;
	}
	@ApiOperation(value = "批量发送tvmERC20", notes = "获取批量发送tvmERC20签名和参数信息")
	@PostMapping("/sendTVMErc20s")
	@GetMapping("/sendTVMErc20s")
	public Result<SendTxInfo> sendTVMErc20s(@RequestBody Erc20Param erc20Param) throws Exception {
		Result<SendTxInfo> result = new Result<SendTxInfo>();
		try {
			SendTxInfo sendTxInfo = transactionTool.sendTVMErc20s(
					erc20Param.getErc20s(), erc20Param.getTos(), erc20Param.getValues());
			result.setData(sendTxInfo);
			result.setMessage(TransactionTool.SUCCESS_MSG);
			result.setCode(TransactionTool.SUCCESS);
		} catch (Exception e) {
			result.setCode(TransactionTool.FAIL);
			result.setMessage(TransactionTool.FAIL);
			e.printStackTrace();
		}
		result.setMessage(TransactionTool.SUCCESS_MSG);
		result.setCode(TransactionTool.SUCCESS);
		
		return result;
	}
	@ApiOperation(value = "批量发送EvmETH", notes = "获取批量发送EvmETH签名和参数信息")
	@PostMapping("/sendEVMEths")
	@GetMapping("/sendEVMEths")
	public Result<SendTxInfo> sendEVMEths(@RequestBody EthParam ethParam) throws Exception {
		Result<SendTxInfo> result = new Result<SendTxInfo>();
		try {
			SendTxInfo sendTxInfo = transactionTool.sendEVMEths(ethParam.getEthTos(), ethParam.getEthValues());
			result.setData(sendTxInfo);
			result.setMessage(TransactionTool.SUCCESS_MSG);
			result.setCode(TransactionTool.SUCCESS);
		} catch (Exception e) {
			result.setCode(TransactionTool.FAIL);
			result.setMessage(TransactionTool.FAIL);
			e.printStackTrace();
		}
		result.setMessage(TransactionTool.SUCCESS_MSG);
		result.setCode(TransactionTool.SUCCESS);
		
		return result;
	}
	@ApiOperation(value = "批量发送tvmETH", notes = "获取批量发送tvmETH签名和参数信息")
	@PostMapping("/sendTVMEths")
	@GetMapping("/sendTVMEths")
	public Result<SendTxInfo> sendTVMEths(@RequestBody EthParam ethParam) throws Exception {
		Result<SendTxInfo> result = new Result<SendTxInfo>();
		try {
			SendTxInfo sendTxInfo = transactionTool.sendTVMEths(ethParam.getEthTos(), ethParam.getEthValues());
			result.setData(sendTxInfo);
			result.setMessage(TransactionTool.SUCCESS_MSG);
			result.setCode(TransactionTool.SUCCESS);
		} catch (Exception e) {
			result.setCode(TransactionTool.FAIL);
			result.setMessage(TransactionTool.FAIL);
			e.printStackTrace();
		}
		result.setMessage(TransactionTool.SUCCESS_MSG);
		result.setCode(TransactionTool.SUCCESS);
		
		return result;
	}
	@ApiOperation(value = "批量发送EvmERC20和ETH", notes = "获取批量发送EvmERC20和ETH签名和参数信息")
	@PostMapping("/sendEVMTransactions")
	@GetMapping("/sendEVMTransactions")
	public Result<SendTxInfo> sendEVMTransactions(@RequestBody TxParam txParam) throws Exception {
		Result<SendTxInfo> result = new Result<SendTxInfo>();
		try {
			SendTxInfo sendTxInfo = transactionTool.sendEVMTransactions(
					txParam.getErc20s(), txParam.getEthTos(), txParam.getEthValues(), txParam.getEthTos(), txParam.getEthValues());
			result.setData(sendTxInfo);
			result.setMessage(TransactionTool.SUCCESS_MSG);
			result.setCode(TransactionTool.SUCCESS);
		} catch (Exception e) {
			result.setCode(TransactionTool.FAIL);
			result.setMessage(TransactionTool.FAIL);
			e.printStackTrace();
		}
		result.setMessage(TransactionTool.SUCCESS_MSG);
		result.setCode(TransactionTool.SUCCESS);
		
		return result;
	}
	@ApiOperation(value = "批量发送TvmERC20和ETH", notes = "获取批量发送TvmERC20和ETH签名和参数信息")
	@PostMapping("/sendTVMTransactions")
	@GetMapping("/sendTVMTransactions")
	public Result<SendTxInfo> sendTVMTransactions(@RequestBody TxParam txParam) throws Exception {
		Result<SendTxInfo> result = new Result<SendTxInfo>();
		try {
			SendTxInfo sendTxInfo = transactionTool.sendTVMTransactions(
					txParam.getErc20s(), txParam.getEthTos(), txParam.getEthValues(), txParam.getEthTos(), txParam.getEthValues());
			result.setData(sendTxInfo);
			result.setMessage(TransactionTool.SUCCESS_MSG);
			result.setCode(TransactionTool.SUCCESS);
		} catch (Exception e) {
			result.setCode(TransactionTool.FAIL);
			result.setMessage(TransactionTool.FAIL);
			e.printStackTrace();
		}
		result.setMessage(TransactionTool.SUCCESS_MSG);
		result.setCode(TransactionTool.SUCCESS);
		
		return result;
	}

}