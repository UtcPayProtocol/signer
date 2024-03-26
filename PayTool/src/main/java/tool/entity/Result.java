package tool.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Result<T> implements Serializable{

	private static final long serialVersionUID = -5548534177186829713L;
	/* 错误码 */
	private String code;
	/* 错误消息 */
	private String message;
	/* 具体内容 */
	private T data;
	
	public Result() {}
	
	
	public Result(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Result(T data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<Object> result(String code,String message,T data){
		this.code = code;
		this.message = message;
		this.data = data;
		return Arrays.asList(this.code,this.message,this.data);
	}
	
	public List<Object> result(String code,String message){
		return result(code,message,this.data);
	}
	
	public List<Object> result(){
		return result(this.code,this.message,this.data);
	}
	
}
