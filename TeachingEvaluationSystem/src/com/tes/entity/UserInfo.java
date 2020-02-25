package com.tes.entity;

import java.io.Serializable;

/**
* @author xl_hsj
* @version 创建时间：2020年2月22日 下午9:08:10
* @ClassName 类名称
* @Description 类描述
*/
public class UserInfo implements Serializable{
	private String user_id;
	private String user_name;
	private String user_pwd;
	private String user_role;
	private String is_enable;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public String getUser_role() {
		return user_role;
	}
	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}
	public String getIs_enable() {
		return is_enable;
	}
	public void setIs_enable(String is_enable) {
		this.is_enable = is_enable;
	}
	@Override
	public String toString() {
		return "UserInfo [user_id=" + user_id + ", user_name=" + user_name + ", user_pwd=" + user_pwd + ", user_role="
				+ user_role + ", is_enable=" + is_enable + "]";
	}
	
}
