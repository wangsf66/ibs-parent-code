package com.ibs.components.filters.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author DougLei
 */
public class CorsFilter implements Filter{
	
	@Autowired
	private CorsConfigurationProperties corsConfig;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) resp;
		if(corsConfig.getAccessControlAllowOrigin() != null) {
			response.setHeader("Access-Control-Allow-Origin", corsConfig.getAccessControlAllowOrigin() );  
		}
		if(corsConfig.getAccessControlAllowMethods() != null) {
			response.setHeader("Access-Control-Allow-Methods", corsConfig.getAccessControlAllowMethods());  
		}
		if(corsConfig.getAccessControlAllowHeaders() != null) {
			response.setHeader("Access-Control-Allow-Headers", corsConfig.getAccessControlAllowHeaders());
		}
		if(corsConfig.isAccessControlAllowCredentials()) {
			response.setHeader("Access-Control-Allow-Credentials", "true"); // 允许cookie
		}
		if(corsConfig.getAccessControlMaxAge() != null) {
			response.setHeader("Access-Control-Max-Age", corsConfig.getAccessControlMaxAge()); 
		}
		if("OPTIONS".equals(((HttpServletRequest)req).getMethod())) {
			return;
		}
		chain.doFilter(req, resp);
	}
}
