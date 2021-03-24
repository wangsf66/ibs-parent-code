package com.ibs.components.filters.log.core.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;

import com.alibaba.fastjson.JSONObject;
import com.ibs.components.filters.log.entity.LogOperation;
import com.ibs.components.filters.request.header.RequestHeaderContext;
import com.ibs.spring.eureka.cloud.feign.RestTemplateWrapper;
import com.ibs.spring.eureka.cloud.feign.APIServer;

/**
 * 日志持久化处理器
 * @author DougLei
 */
public class LogPersistenceHandler {
	
	@Autowired
	private RestTemplateWrapper restTemplate;

	@Async
	void processing(String token, LogOperation log) {
		restTemplate.exchange(new APIServer() {
			
			@Override
			public String getName() {
				return "保存日志API";
			}
			
			@Override
			public String getUrl() {
				return "http://ibs-log/log/add";
			}

			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers = super.getHeaders();
				headers.add(RequestHeaderContext.tokenKey, token);
				return headers;
			}
			
		}, JSONObject.toJSONString(log), String.class);
	}
}
