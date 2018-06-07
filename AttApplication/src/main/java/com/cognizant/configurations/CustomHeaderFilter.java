package com.cognizant.configurations;

import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Filter to Intercept all request
 */
@Component
public class CustomHeaderFilter implements Filter {
    @Override
    public void destroy() {
        System.out.println("destroy filter. release our resources here if any");
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
                                                                                              IOException,ServletException {
        HttpServletResponse httpServletResponse=(HttpServletResponse)response;
        httpServletResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate ,pre-check=0, post-check=0, max-age=0, s-maxage=0");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setHeader("Pragma", "No-cache");
        httpServletResponse.setHeader("X-XSS-Protection","1; mode=block");
        httpServletResponse.setHeader("X-Frame-Options","DENY");
        httpServletResponse.setHeader("X-Content-Type-Options","DENY");
        
        if (httpServletResponse.containsHeader( "SET-COOKIE" ))
		{
			System.out.println(" In SET-COOKIE  *** ");	
		  String sessionid = ((HttpServletRequest) request).getSession().getId();		  
		  //httpServletResponse.setHeader( "SET-COOKIE", "JSESSIONID=" + sessionid + ";Path=/<whatever>; Secure; HttpOnly" );
		  httpServletResponse.setHeader( "SET-COOKIE", "Path=/<whatever>; Secure; HttpOnly" );
		} 
        
        chain.doFilter(request, response);      // continue execution of other filter chain.
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Init filter");
    }
}