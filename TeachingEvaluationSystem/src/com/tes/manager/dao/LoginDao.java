package com.tes.manager.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tes.common.CommonUtils;
import com.tes.common.DBUtils;
import com.tes.common.PageBean;
import com.tes.entity.UserInfo;

/**
* @author xl_hsj
* @version 创建时间：2020年2月23日 下午2:49:45
* @ClassName 类名称
* @Description 类描述
*/
public class LoginDao {

	/**
	 * 登录
	 * @param userInfo
	 * @param pageCount 
	 * @param currentPage 
	 * @return
	 */
	public PageBean<UserInfo> login(UserInfo userInfo, int currentPage, int pageCount) {
		PageBean<UserInfo> pageBean = new PageBean<UserInfo>();
		List<UserInfo> list = new ArrayList<UserInfo>();
		String sql = "select * from tb_wj_user where user_name= ? and user_pwd = ? and user_role = ? "
				+ "and is_enable = 1";//数据库操作语句		
//		System.out.println("sql:  "+sql+"\n"+userInfo.toString());
		Map<String, Object> map = DBUtils.queryMapHandler(sql, new Object[]{userInfo.getUser_name(), 
									userInfo.getUser_pwd(), userInfo.getUser_role() });
		UserInfo users = CommonUtils.toBean(map, UserInfo.class);
//		System.out.println(users.getUser_name()+"    "+users.getUser_pwd());
		list.add(users);//查询对象添加到集合
		pageBean.setPageData(list);
		return pageBean;
	}
	
	/**
	 * 查询某张表的所有记录数
	 * @param tableName  表名
	 * @param condition 查询条件值
	 * @param name 字段名
	 * @return
	 */
	public int doQueryCount(String tableName, String fieldName, String value) {

		String sql = null;
		String[] params = {};
		sql = "select count(*) from " + tableName + " where 1=1 ";
		if (value != null) {
			sql = sql + " and " + fieldName + "= ?";
			params = new String[] { value };
		}
		Map<String, Object> map = DBUtils.queryMapHandler(sql, params);
		int rs= 0;
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()){
		Map.Entry entry = (Map.Entry) it.next();
			System.out.println(entry.getKey() + " : " + entry.getValue());
			rs = Integer.valueOf(entry.getValue().toString());
		}
		return rs;
	}
	
}
