package com.ibs.components.filters.log.core.post;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.ibs.components.filters.log.core.filter.LogContext;
import com.ibs.components.filters.log.entity.LogOperation;
import com.ibs.components.response.ResponseSuccess;

/**
 * 日志记录null返回值拦截器, 属于后置处理拦截器
 * 即如果controller返回的是null, 则需要在这里记录下日志请求的结果
 * 	针对的问题:
 * 		当调用某个controller时, 如果返回了null, 则其不会进行log后置处理中, 若再没有异常, 其日志最终的success值没有被改变, 一直使用的是对象的默认值, 所以这里专门处理
 * 		如果返回null, 这里进行判断和记录日志success的值
 * @author DougLei
 */
@ControllerAdvice
public class LogNullReturnBodyInterceptor implements ResponseBodyAdvice<Object>{

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		if(body == null && LogContext.unEmpty()) {
			LogOperation operationLog = LogContext.getOperationLog();
			operationLog.setIsSuccess(ResponseSuccess.SUCCESS.getCode());
			operationLog.getRequestLog().setDescription("本次请求的响应数据为空(null), 结果默认为成功");
		}
		return body;
	}
}
