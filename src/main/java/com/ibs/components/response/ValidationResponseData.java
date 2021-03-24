package com.ibs.components.response;

import com.douglei.api.doc.annotation.ApiEntity;
import com.douglei.api.doc.annotation.ApiEntityParam;

/**
 * 验证失败响应体
 * @author DougLei
 */
@ApiEntity
class ValidationResponseData extends ResponseData{
	
	@ApiEntityParam(description="标识数据值验证失败的属性名", egValue="name")
	private String field;
	
	public ValidationResponseData(Object data, String field, String message, String code, Object... params) {
		super(data, message, code, params);
		this.field = field;
	}

	public String getField() {
		return field;
	}
}
