package com.ibs.components.filters.param.parser;

import java.util.Map;

/**
 * @author wangShuFang
 */
public class RequestParameter {
	/**
	 * 请求的url内置参数键值对
	 */
	private Map<String, Object> builtin;
	/**
	 * 请求的resource参数键值对
	 */
	private Map<String, Object> resource;
	/**
	 * 请求的parentResource参数键值对
	 */
	private Map<String, Object> parentResource;
	
	
	public Map<String, Object> getBuiltin() {
		return builtin;
	}
	void setBuiltin(Map<String, Object> builtin) {
		this.builtin = builtin;
	}
	public Map<String, Object> getResource() {
		return resource;
	}
	void setResource(Map<String, Object> resource) {
		this.resource = resource;
	}
	public Map<String, Object> getParentResource() {
		return parentResource;
	}
	void setParentResource(Map<String, Object> parentResource) {
		this.parentResource = parentResource;
	}
}
