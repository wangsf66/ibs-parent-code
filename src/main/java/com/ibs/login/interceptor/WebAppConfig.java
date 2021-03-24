package com.ibs.login.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry){
		try {
	    	//registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**");
		} catch (SecurityException e) {
			e.printStackTrace();
		}
    }
}
