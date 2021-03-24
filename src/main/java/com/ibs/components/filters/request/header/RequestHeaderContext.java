package com.ibs.components.filters.request.header;

import javax.servlet.http.HttpServletRequest;

import com.ibs.components.filters.log.core.filter.LogHeader;
import com.ibs.components.filters.token.TokenEntity;

/**
 * 
 * @author DougLei
 */
public class RequestHeaderContext {
	private static final ThreadLocal<RequestHeader> context = new ThreadLocal<RequestHeader>();
	public static final String tokenKey = "_token";
	private static final String languageKey = "_language";
	private static final String logKey = "_log";
	
	/**
	 *     设置请求头
	 * @param request
	 */
	static RequestHeader setRequestHeader(HttpServletRequest request) {
		RequestHeader header = context.get();
		if(header == null) {
			header = new RequestHeader();
			context.set(header);
		}else {
			header.clearTokenEntity();
		}
		header.setToken(request.getHeader(tokenKey));
		header.setLanguage(request.getHeader(languageKey));
		header.setLog(request.getHeader(logKey));
		return header;
	}
	
	/**
	 * 获取token值
	 * @return
	 */
	public static String getToken() {
		return context.get().getToken();
	}
	/**
	 * 获取语言
	 * @return
	 */
	public static String getLanguage() {
		return context.get().getLanguage();
	}
	/**
	 * 获取LogHeader
	 * @return
	 */
	public static LogHeader getLogHeader() {
		return context.get().getLogHeader();
	}
	
	/**
	 * 设置token实体
	 * @param tokenEntity
	 */
	public static void setTokenEntity(TokenEntity tokenEntity) {
		context.get().setTokenEntity(tokenEntity);
	}
	
	/**
	 * 获取token实体
	 * @return
	 */
	public static TokenEntity getTokenEntity() {
		return context.get().getTokenEntity();
	}
}
