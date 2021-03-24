package com.ibs.components.filters.log.core.post;

import org.springframework.http.MediaType;

import com.ibs.components.response.ResponseUtil;

/**
 * 
 * @author DougLei
 */
public class JsonLogPostInterceptor extends LogPostInterceptor {
	
	public JsonLogPostInterceptor() {
		super(MediaType.APPLICATION_JSON);
	}
	
	@Override
	protected String getResponseBody2String(Object returnValue) {
		return ResponseUtil.toJSONString(returnValue);
	}
}
