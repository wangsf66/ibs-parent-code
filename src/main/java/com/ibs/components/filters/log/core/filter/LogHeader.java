package com.ibs.components.filters.log.core.filter;

import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.douglei.api.doc.annotation.ApiEntity;
import com.douglei.api.doc.annotation.ApiEntityParam;
import com.douglei.tools.StringUtil;

/**
 * 日志header
 * @author DougLei
 */
@ApiEntity
public class LogHeader {
	
	@ApiEntityParam(length=36, description="操作批次")
	private String batch;
	@ApiEntityParam(length=2, defaultValue="0", description="操作级别, 例如1表示一般操作, 2表示危险操作, 自定义值")
	private byte level;
	@ApiEntityParam(length=100, description="操作描述")
	private String description;
	
	@ApiEntityParam(length=36, description="操作的模块id")
	private String moduleId;
	@ApiEntityParam(length=50, description="操作的模块name")
	private String moduleName;
	
	@ApiEntityParam(length=36, description="操作的组件id")
	private String componentId;
	@ApiEntityParam(length=50, description="操作的组件name")
	private String componentName;
	
	@ApiEntityParam(length=36, description="操作的功能id")
	private String funcId;
	@ApiEntityParam(length=50, description="操作的功能name")
	private String funcName;
	
	@ApiEntityParam(length=2, description="操作的客户端类型, 例如1表示PC端, 2表示移动端, 自定义值")
	private byte clientType;
	@ApiEntityParam(length=100, description="操作的客户端实例名, 例如window系统的用户名", egValue="Administrator")
	private String clientInstanceName;
	@ApiEntityParam(length=20, description="操作的客户端ip", egValue="192.168.1.1")
	private String clientIp;
	@ApiEntityParam(length=50, description="操作的客户端mac")
	private String clientMac;
	
	private LogHeader() {}
	private static final LogHeader EMPTY_LOG_HEADER = new LogHeader();
	public static final LogHeader newInstance(String json) {
		if(StringUtil.isEmpty(json)) {
			return EMPTY_LOG_HEADER;
		}
		return JSONObject.parseObject(json, LogHeader.class);
	}
	
	public String getBatch() {
		if(batch == null) {
			return UUID.randomUUID().toString();
		}
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public byte getLevel() {
		return level;
	}
	public void setLevel(byte level) {
		this.level = level;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getComponentId() {
		return componentId;
	}
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public String getFuncId() {
		return funcId;
	}
	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}
	public String getFuncName() {
		return funcName;
	}
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}
	public void setClientType(byte clientType) {
		this.clientType = clientType;
	}
	public byte getClientType() {
		return clientType;
	}
	public String getClientInstanceName() {
		return clientInstanceName;
	}
	public void setClientInstanceName(String clientInstanceName) {
		this.clientInstanceName = clientInstanceName;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getClientMac() {
		return clientMac;
	}
	public void setClientMac(String clientMac) {
		this.clientMac = clientMac;
	}
}
