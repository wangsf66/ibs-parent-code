package com.ibs.components.filters.log.entity;

import java.util.Date;

import com.ibs.code.entity.BasicEntity4Id;
import com.ibs.components.response.ResponseSuccess;

/**
 * 操作日志
 * @author DougLei
 */
public class LogOperation extends BasicEntity4Id{
	private static final long serialVersionUID = -8965177844388627162L;
	
	private String language;// 语言
	private String batch;// 操作批次
	private byte level;// 级别
	private String description;// 操作描述
	
	private String token;// token
	
	private String moduleId;// 操作的模块id
	private String moduleName;// 操作的模块name
	
	private String componentId;// 操作的组件id
	private String componentName;// 操作的组件name
	
	private String funcId;// 操作的功能id
	private String funcName;// 操作的功能name
	
	private byte isSuccess = ResponseSuccess.SUCCESS.getCode();// 操作是否成功 {@link ResponseSuccess}
	
	private byte clientType;// 操作的客户端类型
	private String clientInstanceName;// 操作的客户端实例名
	private String clientIp;// 操作的客户端ip
	private String clientMac;// 操作的客户端mac
	
	private String userId;// 操作人id
	private String userName;// 操作人name
	private Date operDate;// 操作时间
	
	private LogRequest requestLog;// 请求日志
	
	public LogOperation() {
	}
	public LogOperation(String token) {
		this.token = token;
	}

	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getBatch() {
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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
	public byte getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(byte isSuccess) {
		if(this.isSuccess != isSuccess) {
			if(this.isSuccess > isSuccess) {
				if(this.isSuccess != ResponseSuccess.PARTIAL_SUCCESS.getCode() || isSuccess != ResponseSuccess.SUCCESS.getCode()) {
					this.isSuccess = isSuccess;
				}
			}else {
				if(this.isSuccess == ResponseSuccess.SUCCESS.getCode() && isSuccess == ResponseSuccess.PARTIAL_SUCCESS.getCode()) {
					this.isSuccess = isSuccess;
				}
			}
		}
		if(requestLog != null) requestLog.setIsSuccess(isSuccess);
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getOperDate() {
		if(operDate == null) {
			return new Date();
		}
		return operDate;
	}
	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}
	public LogRequest getRequestLog() {
		return requestLog;
	}
	public void setRequestLog(LogRequest logRequest) {
		this.requestLog = logRequest;
		this.requestLog.setLogOperationId(getId());
	}
}
