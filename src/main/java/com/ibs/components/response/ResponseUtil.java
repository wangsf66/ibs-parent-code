package com.ibs.components.response;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.douglei.tools.ExceptionUtil;

/**
 * 响应体工具类
 * @author DougLei
 */
public class ResponseUtil {
	private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);
	
	/**
	 * 转换为json字符串
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object) {
		if(object == null) {
			return null;
		}
		if(object instanceof String) {
			return object.toString();
		}
		return JSONObject.toJSONString(object, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
	}
	
	/**
	 * 输出json响应体
	 * @param resp
	 * @param response
	 */
	public static void outputJSON(HttpServletResponse resp, Object response) {
		try {
			resp.setContentType("application/json;charset=utf-8");
			resp.getOutputStream().write(toJSONString(response).getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			logger.error("在输出json响应体[{}]时出现异常: {}", toJSONString(response), ExceptionUtil.getStackTrace(e));
		}
	}

}
