package com.tes.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class CharacterEncodingFilter
 */
@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {

    /**
     * Default constructor. 
     */
    public CharacterEncodingFilter() {
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
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		// 解决post中文乱码问题
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		// 解决get中文乱码问题
		MyRequest myrequest = new MyRequest(req); // 自己定义一个request
		chain.doFilter(myrequest, resp);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

//继承HttpServletRequestWrapper ：//sun公司提供的包装类
//自定义request,解决get中文乱码问题
class MyRequest extends HttpServletRequestWrapper { // request
	private HttpServletRequest request;

	public MyRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	// 重构方法
	public String getParameter(String name) {
		String value = this.request.getParameter(name); // 获取用户提交的数据 get 乱码
		if (value == null) {
			return null;
		}
		// 判断是否是get请求方式
		if (!request.getMethod().equals("GET")) {
			return value;
		}
		try {
			System.out.println("接收到的值：    "+value);
			value = new String(value.getBytes("iso8859-1"), this.request.getCharacterEncoding());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return value;
	}

	// 重构方法
	@Override
	public Map getParameterMap() {
		try {
			if (!this.request.getMethod().equals("GET")) {// 判断是否是get请求方式，不是get请求则直接返回
				return this.request.getParameterMap();
			}

			Map<String, String[]> map = this.request.getParameterMap(); // 接受客户端的数据
			Map<String, String[]> newmap = new HashMap();
			for (Map.Entry<String, String[]> entry : map.entrySet()) {
				String name = entry.getKey();
				String values[] = entry.getValue();

				if (values == null) {
					newmap.put(name, new String[] {});
					continue;
				}
				String newvalues[] = new String[values.length];
				for (int i = 0; i < values.length; i++) {
					String value = values[i];
					System.out.println("接收的值： "+value);
					value = new String(value.getBytes("iso8859-1"), this.request.getCharacterEncoding());
					newvalues[i] = value; // 解决乱码后封装到Map中
				}

				newmap.put(name, newvalues);
			}

			return newmap;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String[] getParameterValues(String name) {
		// TODO Auto-generated method stub
		return super.getParameterValues(name);
	}
}
