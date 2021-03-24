package com.ibs.components.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.douglei.api.doc.annotation.ApiEntity;
import com.douglei.api.doc.annotation.ApiEntityParam;
import com.douglei.api.doc.types.DataType;
import com.douglei.api.doc.types.ParamStructType;

/**
 * 响应
 * @author DougLei
 */
@ApiEntity
public class Response {
	private boolean isBatch;// 是否是批量操作的响应
	
	@ApiEntityParam(dataType=DataType.INTEGER, required=true, description="响应的结果, -1异常, 0失败, 1成功, 2部分成功")
	private ResponseSuccess success;
	
	@ApiEntityParam(required=true, description="处理成功的数据, 对象或集合", egValue = "[|{ 处理成功的数据 }|]")
	private List<Object> data;
	@ApiEntityParam(required=true, entityStruct=ParamStructType.OBJECT_OR_ARRAY, description="验证失败的数据, 对象或集合")
	private List<ValidationResponseData> validation;
	@ApiEntityParam(required=true, entityStruct=ParamStructType.OBJECT_OR_ARRAY, description="处理失败的数据, 对象或集合")
	private List<ErrorResponseData> error;
	@ApiEntityParam(dataType=DataType.OBJECT, required=true, entityStruct=ParamStructType.OBJECT, description="系统出现异常时的信息")
	private ExceptionResponseData exception;
	@ApiEntityParam(required=true, description="系统相关的警示信息")
	private String warn;
	
	public Response() {}
	public Response(Object data) {
		addData(data);
	}
	public Response(String message, String code, Object... params) {
		addValidation(null, null, message, code, params);
	}
	public Response(Object data, String message, String code, Object... params) {
		addValidation(data, null, message, code, params);
	}
	public Response(Exception exception) {
		setException(exception);
	}

	/**
	 * 添加正常处理的数据
	 * @param data
	 */
	void addData(Object data) {
		if(this.data == null) 
			this.data = new ArrayList<Object>();
		this.data.add(data);
	}
	
	/**
	 * 添加验证的数据
	 * @param data
	 * @param field
	 * @param originMessage
	 * @param code
	 * @param params 
	 */
	void addValidation(Object data, String field, String originMessage, String code, Object... params) {
		if(this.validation == null) 
			this.validation = new ArrayList<ValidationResponseData>();
		this.validation.add(new ValidationResponseData(data, field, originMessage, code, params));
	}
	
	/**
	 * 添加操作错误的数据
	 * @param data
	 * @param exception
	 */
	void addError(Object data, Exception exception) {
		if(this.error == null) 
			this.error = new ArrayList<ErrorResponseData>();
		this.error.add(new ErrorResponseData(data, exception));
	}
	
	/**
	 * 设置异常, 并将其他响应置空
	 * @param ex
	 * @return 
	 */
	Response setException(Exception ex) {
		data= null;
		validation= null;
		error= null;
		exception = new ExceptionResponseData(ex);
		return this;
	}
	
	/**
	 * 设置警告信息
	 * @param warn
	 */
	void setWarn(String warn) {
		this.warn = warn;
	}
	
	/**
	 * 设置是否是批量响应
	 * @param isBatch
	 * @return
	 */
	Response setBatch(boolean isBatch) {
		this.isBatch = isBatch;
		return this;
	}
	
	public byte getSuccess() {
		if(success == null) 
			correctSuccess();
		return success.getCode();
	}
	// 修正success
	private void correctSuccess() { 
		if(exception == null) {
			boolean dataEmpty = data == null || data.isEmpty();
			boolean validationEmpty = validation == null || validation.isEmpty();
			boolean errorEmpty = error == null || error.isEmpty();
			if(dataEmpty) {
				if(validationEmpty && errorEmpty) {
					this.success = ResponseSuccess.SUCCESS;
				}else {
					this.success = ResponseSuccess.FAILURE;
				}
			}else {
				if(validationEmpty && errorEmpty) {
					this.success = ResponseSuccess.SUCCESS;
				}else {
					this.success = ResponseSuccess.PARTIAL_SUCCESS;
				}
			}
		}else {
			this.success = ResponseSuccess.EXCEPTION;
		}
	}
	
	public Object getData() {
		return get(data);
	}
	public Object getValidation() {
		return get(validation);
	}
	public Object getError() {
		return get(error);
	}
	private Object get(List<?> list) {
		if(isBatch) {
			if(list == null || list.isEmpty()) 
				return Collections.emptyList();
			return list;
		}
		if(list == null || list.isEmpty()) 
			return null;
		if(list.size() > 1) 
			throw new TooManyResponseDataException();
		return list.get(0);
	}
	
	public ResponseData getException() {
		return exception;
	}
	public String getWarn() {
		return warn;
	}
}