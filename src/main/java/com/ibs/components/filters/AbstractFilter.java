package com.ibs.components.filters;

import javax.servlet.Filter;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.ibs.components.filters.log.core.filter.LogContext;
import com.ibs.components.filters.log.entity.LogOperation;
import com.ibs.components.response.Response;
import com.ibs.components.response.ResponseUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractFilter implements Filter{
	
	/**
	 * 输出失败响应体
	 * @param resp
	 * @param response
	 */
	protected void outputFailureResponse(ServletResponse resp, Response response) {
		String response2String = logFailureResponse(response);
		ResponseUtil.outputJSON((HttpServletResponse)resp, response2String);
	}
	
	/**
	 * 记录失败响应体
	 * @param response
	 * @return 
	 */
	private String logFailureResponse(Response response) {
		String response2String = ResponseUtil.toJSONString(response);
		if(LogContext.unEmpty()) {
			LogOperation operationLog = LogContext.getOperationLog();
			operationLog.getRequestLog().setRespBody(response2String);// 记录响应体
			operationLog.setIsSuccess(response.getSuccess());
		}
		return response2String;
	}
}
