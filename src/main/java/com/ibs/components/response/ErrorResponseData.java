package com.ibs.components.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.api.doc.annotation.ApiEntity;
import com.douglei.tools.ExceptionUtil;

/**
 * 错误响应体, 当程序抛出异常且被捕获时, 会返回此响应体
 * @author DougLei
 */
@ApiEntity
class ErrorResponseData extends ResponseData{
	private static final Logger logger = LoggerFactory.getLogger(ErrorResponseData.class);
	
	public ErrorResponseData(Object data, Exception exception) {
		super(data, "操作出现异常, 请联系管理员, 查看日志", "ibs.operate.exception");
		logger.error("操作出现异常: {}", ExceptionUtil.getStackTrace(exception));
	}
}
