package com.ibs.components.filters.cors;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * @author DougLei
 */
@Component
@ConfigurationProperties(prefix="ibs.parent.cors")
public class CorsConfigurationProperties {
	
	private String accessControlAllowOrigin="*";// 指定允许跨域访问的其他域名
	private String accessControlAllowMethods;// 指定跨域访问时可使用的方法
	private String accessControlAllowHeaders;// 指定跨域访问时可携带的header
	private boolean accessControlAllowCredentials;// 指定跨域访问时是否可以携带cookie
	private String accessControlMaxAge;// 指定本次预检请求的有效期, 单位为秒, 在此期间不用发出另一条预检请求
	
	public String getAccessControlAllowOrigin() {
		return accessControlAllowOrigin;
	}
	public void setAccessControlAllowOrigin(String accessControlAllowOrigin) {
		this.accessControlAllowOrigin = accessControlAllowOrigin;
	}
	public String getAccessControlAllowMethods() {
		return accessControlAllowMethods;
	}
	public void setAccessControlAllowMethods(String accessControlAllowMethods) {
		this.accessControlAllowMethods = accessControlAllowMethods;
	}
	public String getAccessControlAllowHeaders() {
		return accessControlAllowHeaders;
	}
	public void setAccessControlAllowHeaders(String accessControlAllowHeaders) {
		this.accessControlAllowHeaders = accessControlAllowHeaders;
	}
	public boolean isAccessControlAllowCredentials() {
		return accessControlAllowCredentials;
	}
	public void setAccessControlAllowCredentials(boolean accessControlAllowCredentials) {
		this.accessControlAllowCredentials = accessControlAllowCredentials;
	}
	public String getAccessControlMaxAge() {
		return accessControlMaxAge;
	}
	public void setAccessControlMaxAge(String accessControlMaxAge) {
		this.accessControlMaxAge = accessControlMaxAge;
	}
}
