package com.ibs.components.filters.log.core.post;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;

import com.ibs.components.filters.log.core.filter.LogContext;
import com.ibs.components.filters.log.entity.LogOperation;
import com.ibs.components.response.Response;
import com.ibs.components.response.ResponseSuccess;

/**
 * log后置拦截, 主要是记录响应体
 * 自定义实现HttpMessageConverter, 在写出响应数据的时候, 进行日志记录
 * 对读操作不做任何处理
 * @author DougLei
 */
public abstract class LogPostInterceptor extends AbstractGenericHttpMessageConverter<Object>{
	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
	
	protected LogPostInterceptor(MediaType mediaType) {
		super(mediaType);
		setDefaultCharset(DEFAULT_CHARSET);
	}
	
	@Override
	protected void writeInternal(Object t, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		String responseBody2String = logResponse(t);
		outputMessage.getBody().write(responseBody2String.getBytes(DEFAULT_CHARSET));
	}
	
	/**
	 * 日志记录响应体
	 * @param returnValue
	 * @return 响应体字符串
	 */
	private String logResponse(Object returnValue) {
		String responseBody2String = getResponseBody2String(returnValue);
		
		if(LogContext.unEmpty()) {
			LogOperation operationLog = LogContext.getOperationLog();
			operationLog.getRequestLog().setRespBody(responseBody2String);// 记录响应体
			if(returnValue instanceof Response) {
				operationLog.setIsSuccess(((Response)returnValue).getSuccess());
			}else {
				// 如果响应对象不是 {@link IResponse}, 则默认为 {@link ResponseSuccess.SUCCESS}
				operationLog.setIsSuccess(ResponseSuccess.SUCCESS.getCode());
			}
		}
		return responseBody2String;
	}

	/**
	 * 获取响应体字符串
	 * @param returnValue
	 * @return
	 */
	protected abstract String getResponseBody2String(Object returnValue);
	
	// -----------------------------------------------------------------------------------------------------
	// 因为只负责在写出响应数据的时候做日志记录, 所以不参与任何读请求数据的操作
	// -----------------------------------------------------------------------------------------------------
	@Override
	public boolean canRead(Type type, @Nullable Class<?> contextClass, @Nullable MediaType mediaType) {
		return false;
	}

	@Override
	public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		return null;
	}

	@Override
	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		return null;
	}
}
