package com.tes.entity;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

/**
* @author xl_hsj
* @version 创建时间：2020年2月22日 下午9:11:11
* @ClassName 消息类
* @Description 类描述
*/
public class Msg implements Serializable {
	private int flag;
	private String contents;
	private List<T> dataList;
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public List<T> getDataList() {
		return dataList;
	}
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	@Override
	public String toString() {
		return "Msg [flag=" + flag + ", contents=" + contents + ", dataList=" + dataList + "]";
	}
	
	
}
