package com.ibs.spring.resolver.method.argument;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONTokener;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibs.components.filters.log.core.filter.HttpServletRequest4Log;

/**
 * 通用接口解析器
 * @author wangShuFang
 */
public class CommonParamsArgumentResolver implements HandlerMethodArgumentResolver{

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
	    return  parameter.getParameterType()==CommonParamsModel.class
	    		&& parameter.hasParameterAnnotation(CommonParams.class);
	}
	
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		try {
			//webRequest.get
			HttpServletRequest4Log request = (HttpServletRequest4Log)webRequest.getNativeRequest(HttpServletRequest.class);
			CommonParams annotations = parameter.getParameterAnnotation(CommonParams.class);
		    String requestParamsJson = request.getRequestBody2String();
		    
		    return  parseString(requestParamsJson,annotations.cls());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private CommonParamsModel parseString(String requestParamsJson,Class clazz) {
		CommonParamsModel commonParamsModel = new CommonParamsModel();
		String param = parameAssignment(requestParamsJson);
		if(requestParamsJson.startsWith("[")) {
			commonParamsModel.setList((List)JSONObject.parseArray(param, clazz));
			commonParamsModel.setIsBatch(true);
		}else {
			commonParamsModel.setList((List)JSONObject.parseArray("["+param+"]", clazz));
		}
		return commonParamsModel;
	}
	
	private  String parameAssignment(String paramJson) {
		Object json = null;
		JSONObject jsonObject = null;
		JSONArray jsonArray = null;
		String id = null;
		try {
			json = new JSONTokener(paramJson).nextValue();
			if(json instanceof JSONObject){
			    jsonObject  = (JSONObject)json;
			    id = (String)jsonObject.get("id");
			    if(id==null) {
			    	jsonObject.put("id", UUID.randomUUID().toString());
				 }
			}else if (json instanceof JSONArray){
			    jsonArray = (JSONArray)json;
			    for(int i=0;i<jsonArray.size();i++) {
			    	 id = (String)jsonArray.getJSONObject(i).get("id");
				     if(id==null) {
				    	 jsonArray.getJSONObject(i).put("id", UUID.randomUUID().toString());
					 }
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
}
