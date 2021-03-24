package com.ibs.components.filters.log.entity;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.douglei.tools.StringUtil;
import com.ibs.code.entity.BasicEntity4Id;

/**
 * 请求日志
 * @author DougLei
 */
public class LogRequest extends BasicEntity4Id{

	private String logOperationId;// 关联的操作日志id
	
	private String protocol;// 请求的协议
	private String serverInstanceName;// 请求的服务器实例名
	private String apiAddr;// 请求的API地址
	private String method;// 请求方式
	private String reqUrl;// 请求的url数据
	private String reqBody;// 请求的body数据
	private Date reqDate;// 请求时间
	
	private String respBody;// 响应的body数据
	private Date respDate;// 响应时间
	
	private byte isSuccess;// 请求处理是否成功 {@link ResponseSuccess}
	private String description;// 请求处理结果描述
	private byte orderCode;// 排序
	
	public String getLogOperationId() {
		return logOperationId;
	}
	public void setLogOperationId(String logOperationId) {
		this.logOperationId = logOperationId;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getServerInstanceName() {
		return serverInstanceName;
	}
	public void setServerInstanceName(String serverInstanceName) {
		if(StringUtil.unEmpty(serverInstanceName)) 
			serverInstanceName = serverInstanceName.substring(1);
		this.serverInstanceName = serverInstanceName;
	}
	public String getApiAddr() {
		return apiAddr;
	}
	public void setApiAddr(String apiAddr) {
		this.apiAddr = apiAddr;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getReqUrl() {
		return reqUrl;
	}
	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}
	public String getReqBody() {
		return reqBody;
	}
	public void setReqBody(String reqBody) {
		this.reqBody = reqBody;
	}
	/**
	 * 设置上传文件的信息到请求体中
	 * @param fileInfo
	 */
	public void setFileInfo2ReqBody(Object fileInfo) {
		if(reqBody == null)
			setReqBody("[文件上传] " + JSONObject.toJSONString(fileInfo));
	}
	public Date getReqDate() {
		if(reqDate == null) {
			reqDate = new Date();
		}
		return reqDate;
	}
	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}
	public String getRespBody() {
		return respBody;
	}
	public void setRespBody(String respBody) {
		this.respBody = respBody;
	}
	public Date getRespDate() {
		if(respDate == null) {
			respDate = new Date();
		}
		return respDate;
	}
	public void setRespDate(Date respDate) {
		this.respDate = respDate;
	}
	public byte getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(byte isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public byte getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(byte orderCode) {
		this.orderCode = orderCode;
	}
}
