package com.ibs.spring.resolver.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.douglei.tools.ExceptionUtil;
import com.ibs.components.filters.log.core.filter.LogContext;
import com.ibs.components.filters.log.entity.LogOperation;
import com.ibs.components.response.Response;
import com.ibs.components.response.ResponseContext;
import com.ibs.components.response.ResponseUtil;

/**
 * 
 * @author DougLei
 */
@Configuration
public class GlobalExceptionHandler implements HandlerExceptionResolver{
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		logger.error("系统出现异常: {}", ExceptionUtil.getStackTrace(ex));
		
		// 获取响应体, 记录异常
		Response responseBody = ResponseContext.getExceptionResponse(ex);
		
		// 1.记录异常的日志信息
		if(LogContext.unEmpty()) {
			LogOperation operationLog = LogContext.getOperationLog();
			operationLog.setIsSuccess(responseBody.getSuccess());
			operationLog.getRequestLog().setDescription("系统出现异常, 请联系管理员, 查看日志");
		}
		
		// 2.给前端返回异常的响应体, 而非抛出异常
		ResponseUtil.outputJSON(response, responseBody);
		return new ModelAndView();
	}
}
