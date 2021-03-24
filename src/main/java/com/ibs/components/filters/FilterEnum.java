package com.ibs.components.filters;

/**
 * 过滤器枚举
 * @author DougLei
 */
public enum FilterEnum {
	
	/**
	 * 跨域过滤器
	 */
	CORS(10, new String[] {"/*"}),
	
	/**
	 * 请求头参数处理过滤器
	 */
	REQUEST_HEADER(20, new String[] {"/*"}),
	
	/**
	 * 日志过滤器
	 */
	LOG(30, new String[] {"/*"}),
	
	/**
	 * token验证过滤器
	 */
	TOKEN_VALIDATE(40, new String[] {"/*"}),
	
	/**
	 * 动态表服务过滤器
	 */
	DYNAMIC_TABLE(50, new String[] {"/*"}),
	
	/**
	 * 參數解析过滤器
	 */
	PARAMS_PARSER(70, new String[] {"/*"});
	

	private String name;// 过滤器的name
	private byte order;// 过滤器的顺序
	private String[] urlPatterns;// 过滤器要拦截的url数组

	private FilterEnum(int order, String[] urlPatterns) {
		this.name = "ibs-filter-" + name();
		this.order = (byte)order;
		this.urlPatterns = urlPatterns;
	}
	
	public String getName() {
		return name;
	}
	public byte getOrder() {
		return order;
	}
	public String[] getUrlPatterns() {
		return urlPatterns;
	}
}
