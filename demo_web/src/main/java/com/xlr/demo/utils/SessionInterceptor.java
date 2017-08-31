package com.xlr.demo.utils;

import com.xlr.demo.controller.BaseController;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;


@Repository @SuppressWarnings("all")
public class SessionInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();  
        String url = requestUri.substring(contextPath.length());  
        Enumeration names = request.getParameterNames(); 
        
        BaseController bc = (BaseController)handler ;
        bc.setRequest(request);
        bc .setResponse(response);
        bc.setSession(request.getSession());
        
        while (names.hasMoreElements()) 
        { 
        	String name = (String) names.nextElement(); 
        	String[] values = request.getParameterValues(name); 
        	for (String value : values) {
        		value = clearXss(value); 
    		} 
    	}
		if(session.getAttribute("user") == null)
		{
			 if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){  
                response.setHeader("sessionstatus", "timeout");
                response.getWriter().println(-1);
			 }else{
				 response.sendRedirect(contextPath + "/login.html");
			 }  
			 return false;
		}
		return super.preHandle(request, response, handler);
	}
	
	/** * 处理字符转义 * * 
	 * @param value 
	 * @return 
	 * */
	private String clearXss(String value) {
		if (value == null || "".equals(value)) {
			return value;
		}
		value = value.replaceAll("<", "<").replaceAll(">", ">");
		value = value.replaceAll("\\(", "(").replace("\\)", ")");
		value = value.replaceAll("'", "'");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
				"\"\"");
		value = value.replace("script", "");
		return value;
	}
	
}
