package com.ibs.components.filters;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * @author DougLei
 */
@Component
@ConfigurationProperties(prefix = "ibs.parent.filter")
public class FilterConfigurationProperties {
	
	/**
	 * 是否启用cors过滤器
	 * 默认为true
	 */
	private boolean enableCorsFilter=true;
	/**
	 * 是否启用请求头参数处理过滤器
	 * 默认为true
	 */
	private boolean enableRequestHeaderFilter=true;
	/**
	 * 是否启用log过滤器
	 * 默认为true
	 */
	private boolean enableLogFilter=true;
	/**
	 * 是否启用token validate过滤器
	 * 默认为true
	 */
	private boolean enableTokenValidateFilter=true;
	/**
	 * 是否启用动态表过滤器
	 * 默认为false
	 */
	private boolean enableDynamicTableFilter;
	/**
	 * 是否启用 参数解析过滤器
	 * 默认为false
	 */
	private boolean enableParamParserFilter;
	
	public boolean isEnableCorsFilter() {
		return enableCorsFilter;
	}
	public void setEnableCorsFilter(boolean enableCorsFilter) {
		this.enableCorsFilter = enableCorsFilter;
	}
	public boolean isEnableRequestHeaderFilter() {
		return enableRequestHeaderFilter;
	}
	public void setEnableRequestHeaderFilter(boolean enableRequestHeaderFilter) {
		this.enableRequestHeaderFilter = enableRequestHeaderFilter;
	}
	public boolean isEnableLogFilter() {
		return enableLogFilter;
	}
	public void setEnableLogFilter(boolean enableLogFilter) {
		this.enableLogFilter = enableLogFilter;
	}
	public boolean isEnableTokenValidateFilter() {
		return enableTokenValidateFilter;
	}
	public void setEnableTokenValidateFilter(boolean enableTokenValidateFilter) {
		this.enableTokenValidateFilter = enableTokenValidateFilter;
	}
	public boolean isEnableDynamicTableFilter() {
		return enableDynamicTableFilter;
	}
	public void setEnableDynamicTableFilter(boolean enableDynamicTableFilter) {
		this.enableDynamicTableFilter = enableDynamicTableFilter;
	}
	public boolean isEnableParamParserFilter() {
		return enableParamParserFilter;
	}
	public void setEnableParamParserFilter(boolean enableParamParserFilter) {
		this.enableParamParserFilter = enableParamParserFilter;
	}
}
