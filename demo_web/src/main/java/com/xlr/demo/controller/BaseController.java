package com.xlr.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SessionAttributes("user")
public class BaseController {
	
	protected HttpServletRequest request ; 

	protected HttpServletResponse response ;

	protected HttpSession session ;
	
    public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}
	
	@RequestMapping("/main")
	public String index(Model model)
	{
			return "main";
	}
	
	@RequestMapping("/header")
	public String header(Model model){
		return "header";
	}
	
	@RequestMapping("/left")
	public String left(Model model){
		return "left";
	}
	@RequestMapping("/footer")
	public String footer(Model model){
		return "footer";
	}
}
