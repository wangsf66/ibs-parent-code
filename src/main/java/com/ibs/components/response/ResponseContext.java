package com.ibs.components.response;

import com.douglei.orm.mapping.validator.ValidateFailResult;

/**
 * 
 * @author DougLei
 */
public class ResponseContext {
	private static final ThreadLocal<Response> context = new ThreadLocal<Response>();
	
	/**
	 * 获取最终的响应体 {@link Response}
	 * @return
	 */
	public static Response getFinalResponse() {
		return getFinalResponse(false);
	}
	
	/**
	 * 获取最终的响应体 {@link Response}
	 * @param isBatch 是否批量
	 * @return
	 */
	public static Response getFinalResponse(boolean isBatch) {
		Response response = context.get();
		if(response == null) 
			return new Response(new NullPointerException("响应体为null"));
		context.remove();
		return response.setBatch(isBatch);
	}
	
	/**
	 * 获取异常响应体 {@link Response}
	 * @param ex
	 * @return
	 */
	public static Response getExceptionResponse(Exception ex) {
		Response response = getFinalResponse(false);
		response.setException(ex);
		return response;
	}
	
	/**
	 * 获取响应体 {@link Response}
	 * @return
	 */
	private static Response getResponse() {
		Response response = context.get();
		if(response == null) {
			response = new Response();
			context.set(response);
		}
		return response;
	}
	
	/**
	 * 添加正常处理的数据
	 * @param data
	 */
	public static void addData(Object data) {
		getResponse().addData(data);
	}
	
	/**
	 * 添加操作验证的数据
	 * @param originMessage 
	 * @param code
	 * @param params 
	 */
	public static void addValidation(String originMessage, String code, Object... params) {
		getResponse().addValidation(null, null, originMessage, code, params);
	}
	
	/**
	 * 添加验证的数据
	 * @param data 验证的数据实例
	 * @param result
	 */
	public static void addValidation(Object data, ValidateFailResult failResult) {
		getResponse().addValidation(data, failResult.getName(), failResult.getMessage(), failResult.getCode(), failResult.getParams());
	}
	
	/**
	 * 添加验证的数据
	 * @param data
	 * @param fieldName 验证失败的属性名
	 * @param message
	 * @param code
	 * @param params
	 */
	public static void addValidationFull(Object data, String fieldName, String message, String code, Object... params) {
		getResponse().addValidation(data, fieldName, message, code, params);
	}
	
	/**
	 * 添加操作错误的数据
	 * @param data
	 * @param exception
	 */
	public static void addError(Object data, Exception exception) {
		getResponse().addError(data, exception);
	}
	
	/**
	 * 设置警告信息
	 * @param warn
	 */
	public static void setWarn(String warn) {
		getResponse().setWarn(warn);
	}
}
