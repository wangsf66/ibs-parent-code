package com.ibs.components.filters.log.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.ibs.components.filters.FilterEnum;

/**
 * 
 * @author DougLei
 */
@Component
@ConfigurationProperties(prefix="ibs.parent.filter.log")
public class LogConfigurationProperties {
	
	/**
	 * 日志过滤器要过滤的url模式
	 */
	private String[] logUrlPatterns = FilterEnum.LOG.getUrlPatterns();
	
	public String[] getLogUrlPatterns() {
		return logUrlPatterns;
	}
	public void setLogUrlPatterns(String[] logUrlPatterns) {
		this.logUrlPatterns = logUrlPatterns;
	}
}
