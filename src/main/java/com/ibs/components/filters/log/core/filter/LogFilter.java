package com.ibs.components.filters.log.core.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.tools.StringUtil;
import com.douglei.tools.web.HttpUtil;
import com.ibs.components.filters.log.entity.LogOperation;
import com.ibs.components.filters.log.entity.LogRequest;
import com.ibs.components.filters.request.header.RequestHeaderContext;


/**
 * 日志过滤器
 * 因为日志要读取请求体的数据, 而直接读取request后关闭, springmvc就无法读取, 所以这里对 {@link HttpServletRequest} 进行二次封装, 保证日志读取请求体后, springmvc也能读取
 * @author DougLei
 */
public class LogFilter implements Filter{
	
	@Autowired
	private LogPersistenceHandler handler;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = new HttpServletRequest4Log((HttpServletRequest) req);
		
		logPreHandler(request, RequestHeaderContext.getToken());// 日志前置处理
		chain.doFilter(request, response);
		handler.processing(RequestHeaderContext.getToken(), LogContext.getFinalOperationLog());// 日志持久化处理
	}
	
	// -----------------------------------------------------------------------------------------
	// 前置处理
	// -----------------------------------------------------------------------------------------
	
	/**
	 * 日志前置处理
	 * @param request
	 * @param token
	 * @throws IOException 
	 */
	private void logPreHandler(HttpServletRequest request, String token) throws IOException {
		LogOperation operation = extractOperationLog(request, token);
		operation.setRequestLog(extractRequestLog(request, operation));
		LogContext.setOperationLog(operation);
	}

	/**
	 * 提取操作日志
	 * @param request
	 * @param token
	 * @return
	 */
	private LogOperation extractOperationLog(HttpServletRequest request, String token) {
		LogHeader logHeader = RequestHeaderContext.getLogHeader();
		LogOperation operation = new LogOperation(token);
			
		operation.setLanguage(RequestHeaderContext.getLanguage());
		operation.setBatch(logHeader.getBatch());
		operation.setLevel(logHeader.getLevel());
		
		operation.setModuleId(logHeader.getModuleId());
		operation.setModuleName(logHeader.getModuleName());
		
		operation.setComponentId(logHeader.getComponentId());
		operation.setComponentName(logHeader.getComponentName());
		
		operation.setFuncId(logHeader.getFuncId());
		operation.setFuncName(logHeader.getFuncName());
		
		setClientInfo(operation, request, logHeader);
		return operation;
	}
	
	// 设置客户端信息
	private void setClientInfo(LogOperation operation, HttpServletRequest request, LogHeader logHeader) {
		operation.setClientType(logHeader.getClientType());
		operation.setClientInstanceName(logHeader.getClientInstanceName());
		setClientIp(operation, request, logHeader);
		operation.setClientMac(logHeader.getClientMac()); // 设置客户端的mac地址, 要想后端获取客户端mac, 还需要向客户端发送消息, 所以这里就暂时不处理
	}

	// 设置客户端ip
	private void setClientIp(LogOperation operation, HttpServletRequest request, LogHeader logHeader) {
		String clientIp = logHeader.getClientIp();
		if(StringUtil.isEmpty(clientIp)) {
			clientIp = HttpUtil.getClientIp(request);
		}
		operation.setClientIp(clientIp);
	}

	/**
	 * 提取请求日志
	 * @param request
	 * @param operation
	 * @return
	 * @throws IOException 
	 */
	private LogRequest extractRequestLog(HttpServletRequest request, LogOperation operation) throws IOException {
		LogRequest requestLog = new LogRequest();
		requestLog.setProtocol(request.getProtocol());
		requestLog.setServerInstanceName(request.getContextPath());
		requestLog.setApiAddr(request.getServletPath());
		requestLog.setMethod(request.getMethod());
		requestLog.setReqUrl(request.getQueryString() == null?null:URLDecoder.decode(request.getQueryString(), "utf-8"));
		requestLog.setReqBody(extractRequestBody2String(request));
		return requestLog;
	}

	// 提取请求体
	private String extractRequestBody2String(HttpServletRequest request) throws IOException {
		if(request instanceof HttpServletRequest4Log) {
			return ((HttpServletRequest4Log)request).getRequestBody2String();
		}
		return HttpUtil.getRequestBody2String(request);
	}
}
