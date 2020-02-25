package com.tes.manager.services;

import java.util.List;

import com.tes.common.PageBean;
import com.tes.entity.UserInfo;
import com.tes.manager.dao.LoginDao;

/**
* @author xl_hsj
* @version 创建时间：2020年2月23日 下午12:58:02
* @ClassName 类名称
* @Description 类描述
*/
public class LoginServices {

	/**
	 * 用户登录
	 * @param u
	 * @param pageCount 
	 * @param currentPage 
	 * @return
	 */
	public PageBean<UserInfo> login(UserInfo u, int currentPage, int pageCount) {
		LoginDao loginDao = new LoginDao();
		return loginDao.login(u, currentPage, pageCount);
	}
	
}
