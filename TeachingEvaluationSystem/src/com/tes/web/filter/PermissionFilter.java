package com.tes.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tes.entity.Msg;
import com.tes.entity.UserInfo;

/**
 * Servlet Filter implementation class PermissionFilter
 */
@WebFilter("/manager")
public class PermissionFilter implements Filter {

    /**
     * Default constructor. 
     */
    public PermissionFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		HttpSession session = req.getSession();
		UserInfo u = (UserInfo)session.getAttribute("UserInfo");
//		System.out.println(u.getUserName());
		String url = req.getRequestURI();
//		System.out.println("请求地址: "+url);
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		if (url.indexOf("/manager/LoginServlet")>-1){
			chain.doFilter(request, response);
			return;
		}
		if (u==null){
			//返回登录
			req.getSession().removeAttribute("UserInfo");
			req.getSession().invalidate();//销毁session对象
			//返回消息，“没有权限，请先登录！”
			Msg msg = new Msg();
			msg.setFlag(0);
			msg.setContents("没有权限，请先登录！");
			req.getSession().setAttribute("errorMsg", msg);
			//跳转到error.jsp页面
			resp.sendRedirect(req.getContextPath() + "/login.jsp");
		}else {
			chain.doFilter(request, response);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
