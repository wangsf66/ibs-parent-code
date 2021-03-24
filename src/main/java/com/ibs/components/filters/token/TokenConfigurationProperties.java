package com.ibs.components.filters.token;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.douglei.tools.web.UrlMatcher;

/**
 * 
 * @author DougLei
 */
@Component
@ConfigurationProperties(prefix="ibs.parent.token")
public class TokenConfigurationProperties {
	private UrlMatcher ignoreValidateUrlMatcher;// 忽略验证token的url匹配器
	
	public String[] getIgnoreValidateUrlPatterns() {
		return null;
	}
	public void setIgnoreValidateUrlPatterns(String[] ignoreValidateUrlPatterns) {
		this.ignoreValidateUrlMatcher = new UrlMatcher(ignoreValidateUrlPatterns);
	}
	public UrlMatcher getIgnoreValidateUrlMatcher() {
		return ignoreValidateUrlMatcher;
	}
}
