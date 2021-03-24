package com.ibs.components.response;

import com.douglei.api.doc.annotation.ApiEntityParam;
import com.douglei.tools.i18n.Message;

/**
 * 响应数据
 * @author DougLei
 */
abstract class ResponseData {
	@ApiEntityParam(description="处理的数据", egValue="{ 处理的数据 }")
	private Object data;
	private Message message;
	
	public ResponseData() {
	}
	public ResponseData(Object data, String message, String code, Object... params) {
		this.data = data;
		this.message = new Message(message, code, params);
	}
	
	public Object getData() {
		return data;
	}
	@ApiEntityParam(description="国际化编码", egValue="api.doc.test.code")
	public String getCode() {
		return message.getCode();
	}
	@ApiEntityParam(description="国际化消息内容", egValue="这是一条中文语言的具体消息")
	public String getMessage() {
		return message.getMessage();
	}
}
