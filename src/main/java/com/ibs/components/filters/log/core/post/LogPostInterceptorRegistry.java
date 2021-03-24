package com.ibs.components.filters.log.core.post;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * log后置拦截器的注册
 * @author DougLei
 */
@Configuration
public class LogPostInterceptorRegistry implements WebMvcConfigurer{
	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		for (int i=1;i<converters.size();i++) {
			if(converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
				// 要将json日志后置拦截器放到 {@link MappingJackson2HttpMessageConverter} 之前, 否则MappingJackson2HttpMessageConverter就会输出响应, 而无法使用json日志拦截器输出响应, 也就无法记录日志
				converters.add(i, new JsonLogPostInterceptor());  
				break;
			}
		}
	}
}
