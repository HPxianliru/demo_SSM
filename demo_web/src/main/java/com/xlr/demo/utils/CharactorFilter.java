package com.xlr.demo.utils;

import javax.servlet.*;
import java.io.IOException;

public class CharactorFilter implements Filter {
    String encoding=null;

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if(encoding!=null){
            request.setCharacterEncoding(encoding);
            response.setContentType("text/html;charset="+encoding);
        }
        chain.doFilter(request, response);

    }

    public void init(FilterConfig filterConfig) throws ServletException {
        encoding=filterConfig.getInitParameter("encoding");

    }

    public void destroy() {
        // TODO Auto-generated method stub
        encoding=null;
    }

}

