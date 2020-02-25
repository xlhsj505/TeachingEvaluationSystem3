package com.tes.manager.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tes.common.CommonUtils;
import com.tes.common.Md5Utils;
import com.tes.common.PageBean;
import com.tes.entity.Msg;
import com.tes.entity.ReturnData;
import com.tes.entity.UserInfo;
import com.tes.manager.services.LoginServices;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/manager/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");//获取请参数中的action值
		System.out.println(request.getServletPath()+"   请求的action："+action+"   ");
		if ("login".equals(action)) {
			//登录业务
			login(request, response);
		} 
//		else if ("queryAllUserInfo".equals(action)) {
//			doQueryAllUserInfo(req, resp);//查询所有用户信息
//		} else if ("queryUser".equals(action)) {
//			doQueryUserByID(req, resp);//根据ID查询用户信息			
//		} else if ("updateUser".equals(action)) {
//			doUpdateUser(req, resp);			
//		} else if ("delUser".equals(action)) {
//			doDelUserByID(req, resp);
//		} else if ("addUser".equals(action)) {
//			doAddUser(req, resp);			
//		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		// 当前是第页数
		String currPage = request.getParameter("currentPage") == null ? "1" : request.getParameter("currentPage");
		int currentPage = Integer.parseInt(currPage);
		// 当前页显示的总数
		String pageC = request.getParameter("pageCount") == null ? "10" : request.getParameter("pageCount");
		int pageCount = Integer.parseInt(pageC);
		//获取登录页面传送的参数值
		Map<String, String[]> map = request.getParameterMap();
		UserInfo u = (UserInfo) CommonUtils.toBean(map, UserInfo.class);
		PageBean<UserInfo> users ;//分页类
		JSONObject jsonObject = null;//往客户端输出
		Msg msg = new Msg();//自定义的消息对象
		if(u.getUser_role().equals("1")) {
			//领导登录处理
			u.setUser_pwd(Md5Utils.getMD5(u.getUser_pwd()));
		} else if (u.getUser_role().equals("2")) {
			//教务人员登录处理
			u.setUser_pwd(Md5Utils.getMD5(u.getUser_pwd()));//密码进行md5加密
			//判断登录是否成功
			LoginServices ls = new LoginServices();
			users = ls.login(u, currentPage, pageCount);//登录
			if(users.getPageData().get(0).getUser_name() == null) {
				msg.setContents("账号或者密码错误！");
				msg.setFlag(0);
			}else {
				msg.setContents("manager/jwindex.jsp");
				msg.setFlag(1);
			}
		} else if (u.getUser_role().equals("3")) {
			//学生登录处理
		} else {
			//没有此类型用户
		}
		jsonObject = JSONObject.fromObject(msg);//把msg对象转成json对象
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
	}

}
