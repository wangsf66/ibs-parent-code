package com.ibs.components.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ibs.components.filters.cors.CorsFilter;
import com.ibs.components.filters.dynamic.table.DynamicTableFilter;
import com.ibs.components.filters.log.core.LogConfigurationProperties;
import com.ibs.components.filters.log.core.filter.LogFilter;
import com.ibs.components.filters.log.core.filter.LogPersistenceHandler;
import com.ibs.components.filters.param.parser.ParamParserFilter;
import com.ibs.components.filters.request.header.RequestHeaderFilter;
import com.ibs.components.filters.token.TokenValidateFilter;

/**
 * 过滤器注册器
 * @author DougLei
 */
@Configuration
public class FilterRegistry {
	
	@Autowired
	private LogConfigurationProperties logConfig;
	
	/**
	 * 注册cors过滤器, 默认注册
	 * @return
	 */
	@Bean
	@ConditionalOnExpression("${ibs.parent.filter.enable-cors-filter:true}")
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<CorsFilter>();
		registration.setFilter(corsFilterBean());
		registration.setName(FilterEnum.CORS.getName());
		registration.addUrlPatterns(FilterEnum.CORS.getUrlPatterns());
		registration.setOrder(FilterEnum.CORS.getOrder());
		return registration;
	}
	@Bean
	@ConditionalOnExpression("${ibs.parent.filter.enable-cors-filter:true}")
	public CorsFilter corsFilterBean() {
		return new CorsFilter();
	}
	
	/**
	 * 注册请求头参数处理过滤器, 默认注册
	 * @return
	 */
	@Bean
	@ConditionalOnExpression("${ibs.parent.filter.enable-request-header-filter:true}")
	public FilterRegistrationBean<RequestHeaderFilter> requestHeaderFilter(){
		FilterRegistrationBean<RequestHeaderFilter> registration = new FilterRegistrationBean<RequestHeaderFilter>();
		registration.setFilter(new RequestHeaderFilter());
		registration.setName(FilterEnum.REQUEST_HEADER.getName());
		registration.addUrlPatterns(FilterEnum.REQUEST_HEADER.getUrlPatterns());
		registration.setOrder(FilterEnum.REQUEST_HEADER.getOrder());
		return registration;
	}
	
	/**
	 * 注册log过滤器, 默认注册
	 * @return
	 */
	@Bean
	@ConditionalOnExpression("${ibs.parent.filter.enable-log-filter:true}")
	public FilterRegistrationBean<LogFilter> logFilter(){
		FilterRegistrationBean<LogFilter> registration = new FilterRegistrationBean<LogFilter>();
		registration.setFilter(logFilterBean());
		registration.setName(FilterEnum.LOG.getName());
		registration.addUrlPatterns(logConfig.getLogUrlPatterns());
		registration.setOrder(FilterEnum.LOG.getOrder());
		return registration;
	}
	@Bean
	@ConditionalOnExpression("${ibs.parent.filter.enable-log-filter:true}")
	public LogFilter logFilterBean() {
		return new LogFilter();
	}
	@Bean
	@ConditionalOnExpression("${ibs.parent.filter.enable-log-filter:true}")
	public LogPersistenceHandler logPersistenceHandler() {
		return new LogPersistenceHandler();
	}
	
	/**
	 * 注册token验证过滤器, 默认注册 
	 * @return
	 */
	@Bean
	@ConditionalOnExpression("${ibs.parent.filter.enable-token-validate-filter:true}")
	public FilterRegistrationBean<TokenValidateFilter> tokenValidateFilter(){
		FilterRegistrationBean<TokenValidateFilter> registration = new FilterRegistrationBean<TokenValidateFilter>();
		registration.setFilter(tokenValidateFilterBean());
		registration.setName(FilterEnum.TOKEN_VALIDATE.getName());
		registration.addUrlPatterns(FilterEnum.TOKEN_VALIDATE.getUrlPatterns());
		registration.setOrder(FilterEnum.TOKEN_VALIDATE.getOrder());
		return registration;
	}
	@Bean
	@ConditionalOnExpression("${ibs.parent.filter.enable-token-validate-filter:true}")
	public TokenValidateFilter tokenValidateFilterBean() {
		return new TokenValidateFilter();
	}
	
	/**
	 * 注册动态表过滤器, 默认不注册
	 * @return
	 */
	@Bean
	@ConditionalOnExpression("${ibs.parent.filter.enable-dynamic-table-filter:false}")
	public FilterRegistrationBean<DynamicTableFilter> dynamicTableFilter(){
		FilterRegistrationBean<DynamicTableFilter> registration = new FilterRegistrationBean<DynamicTableFilter>();
		registration.setFilter(dynamicTableFilterBean());
		registration.setName(FilterEnum.DYNAMIC_TABLE.getName());
		registration.addUrlPatterns(FilterEnum.DYNAMIC_TABLE.getUrlPatterns());
		registration.setOrder(FilterEnum.DYNAMIC_TABLE.getOrder());
		return registration;
	}
	@Bean
	@ConditionalOnExpression("${ibs.parent.filter.enable-dynamic-table-filter:false}")
	public DynamicTableFilter dynamicTableFilterBean() {
		return new DynamicTableFilter();
	}
	
	/**
	 * 参数解析过滤器
	 * @return
	 */
	@Bean
	@ConditionalOnExpression("${ibs.parent.filter.enable-param-parser-filter:false}")
	public FilterRegistrationBean<ParamParserFilter> paramsFilter(){
		FilterRegistrationBean<ParamParserFilter> registration = new FilterRegistrationBean<ParamParserFilter>();
		registration.setFilter(new ParamParserFilter());
		registration.setName(FilterEnum.PARAMS_PARSER.getName());
		registration.addUrlPatterns(FilterEnum.PARAMS_PARSER.getUrlPatterns());
		registration.setOrder(FilterEnum.PARAMS_PARSER.getOrder());
		return registration;
	}
}
