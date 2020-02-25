package com.tes.entity;
/**
* @author xl_hsj
* @version ����ʱ�䣺2020��2��23�� ����5:37:16
* @ClassName ������
* @Description ������
*/
import java.util.List;

public class ReturnData<T> {
	private String code;//返回码
	private String msg;//消息
	private String count;//数量
	private List<T> data;//数据
	public ReturnData() {
		super();
	}
	public ReturnData(String code, String msg, String count, List<T> data) {
		super();
		this.code = code;
		this.msg = msg;
		this.count = count;
		this.data = data;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
	
}
