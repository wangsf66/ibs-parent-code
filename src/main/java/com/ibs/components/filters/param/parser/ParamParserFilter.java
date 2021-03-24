package com.ibs.components.filters.param.parser;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author wangShuFang
 */
public class ParamParserFilter implements Filter{
	public static final String REQUEST_BODY_KEY="RequestBody";
	
	//内置的url参数
	private static final String BUILDIN_URL_PARAMS[] = {"_focusedId",  "_rows", "_page", "_resultType", "_sort", 
			  "_recursive", "_deep", "_pcName", 
			  "_simpleModel", "_psRefPropName",
			  "_subResourceName", "_subListRefPropName", "_subSort","_mapToObject","_onlyOneObject"
			 	   };


	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
    	Enumeration<String> parameterNames = request.getParameterNames();
    	Map<String, Object> urlParams = new HashMap<String, Object>(16);
    	if(parameterNames != null && parameterNames.hasMoreElements()){
			String key = null;
			while(parameterNames.hasMoreElements()){
				key = parameterNames.nextElement();// 获取key
				if(key.equals("_")){
					continue;
				}
				urlParams.put(key, request.getParameter(key).trim());
			}
		}
    	RequestParameter res = new  RequestParameter();
    	res.setBuiltin(analysisBuiltinParams(urlParams));
    	res.setParentResource(analysisParentResourceParams(urlParams));
    	res.setResource(urlParams); 
    	request.setAttribute(REQUEST_BODY_KEY, res);
		chain.doFilter(req, resp);
	}
	
	/**
	 * 解析出内置的url参数
	 * @param urlParams
	 * @return
	 */
	private Map<String, Object> analysisBuiltinParams(Map<String, Object> urlParams) {
		Map<String, Object> builtinParams = null;
		if(urlParams.size() > 0){
			builtinParams = new HashMap<String, Object>(16);
			Object builtinParamValue = null;
			for (String param : BUILDIN_URL_PARAMS) {
				builtinParamValue = urlParams.remove(param);
				if(builtinParamValue!=null){
					builtinParams.put(param, builtinParamValue);
				}
			}
		}else{
			builtinParams = Collections.emptyMap();
		}
		return builtinParams;
	}
	
	
	//解析出父级查询条件
	private Map<String, Object> analysisParentResourceParams(Map<String, Object> urlParams) {
		Map<String, Object> parentResourceParams = null;
		if(urlParams.size() > 0){
			parentResourceParams = new HashMap<String, Object>(urlParams.size());
			Iterator<Map.Entry<String, Object>> iter = urlParams.entrySet().iterator();
	        while (iter.hasNext()) {
	        	Map.Entry<String, Object> entry = iter.next();
	            String key = entry.getKey();
	            Object value = entry.getValue();
	            if (key.startsWith("_root.")) {
	            	parentResourceParams.put(key.replace("_root.", ""), value);
	                iter.remove();
	                urlParams.remove(key);
	            }
	        }
		}else{
			parentResourceParams = Collections.emptyMap();
		}
		return parentResourceParams;
	}
}
