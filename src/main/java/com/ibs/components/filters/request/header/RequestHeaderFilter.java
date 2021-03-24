package com.ibs.components.filters.request.header;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.ibs.components.filters.AbstractFilter;

/**
 * 请求头数据处理过滤器
 * @author DougLei
 */
public class RequestHeaderFilter extends AbstractFilter{

	@Override
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		RequestHeader header = RequestHeaderContext.setRequestHeader((HttpServletRequest) req);
		try {
//			I18nLanguageContext.setLanguage(header.getLanguage()); // 因为国际化需要的语言是从请求头过来的, 所以设置国际化语言的代码写到这里
			chain.doFilter(req, response);
		} finally {
//			I18nLanguageContext.removeLanguage();
		}
	}
}
