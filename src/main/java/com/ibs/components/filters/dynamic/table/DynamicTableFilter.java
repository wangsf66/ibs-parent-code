package com.ibs.components.filters.dynamic.table;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.tools.web.UrlMatcher;
import com.ibs.code.service.dynamic.table.DynamicTableService;
import com.ibs.components.filters.AbstractFilter;
import com.ibs.components.filters.FilterConfigurationProperties;
import com.ibs.components.filters.request.header.RequestHeaderContext;
import com.ibs.components.response.ResponseContext;

/**
 * 
 * @author DougLei
 */
public class DynamicTableFilter extends AbstractFilter{
	
	@Autowired
	private FilterConfigurationProperties filterConfig;
	
	@Autowired
	private DynamicTableConfigurationProperties dynamicTableConfig;
	
	@Autowired
	private DynamicTableService service;
	
	private UrlMatcher pathMatcher = new UrlMatcher("/*/dynamic/table/initial|destroy");
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(!filterConfig.isEnableTokenValidateFilter()) {
			ResponseContext.addValidation("使用动态表功能, 必须启用 TokenValidateFilter", "ibs.dynamic.table.dependency.token.validate");
			outputFailureResponse(response, ResponseContext.getFinalResponse());
			return;
		}
		
		String dynamicTableName = dynamicTableConfig.getName();
		if(!pathMatcher.match(((HttpServletRequest)request).getServletPath())) {
			int index = DynamicTableIndexContext.getIndex(RequestHeaderContext.getTokenEntity().getProjectId(), service);
			if(index == -1) {
				// TODO 这块的提示信息 projectId, 后续换成project name
				ResponseContext.addValidation("项目[%s]未在[%s]表中初始化数据", "ibs.dynamic.table.uninitialized", RequestHeaderContext.getTokenEntity().getProjectId(), dynamicTableName);
				outputFailureResponse(response, ResponseContext.getFinalResponse());
				return;
			}
			DynamicTableIndexContext.setCurrentTableIndex(index);
		}
		chain.doFilter(request, response);
	}
}
