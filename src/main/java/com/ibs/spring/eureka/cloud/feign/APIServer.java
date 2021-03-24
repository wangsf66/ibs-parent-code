package com.ibs.spring.eureka.cloud.feign;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 * api服务
 * @author DougLei
 */
public abstract class APIServer {

	/**
	 * 获取API名称
	 * @return
	 */
	public abstract String getName();
	
	/**
	 * 获取API url
	 * @return
	 */
	public abstract String getUrl();
	
	/**
	 * 获取API头信息
	 * @return
	 */
	public HttpHeaders getHeaders() {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		return header;
	}
	
	/**
	 * 获取API请求的方式
	 * @return
	 */
	public HttpMethod getRequestMethod() {
		return HttpMethod.POST;
	}
	
	@Override
	public String toString() {
		return "APIServer [name=" + getName() + "]";
	}
}
