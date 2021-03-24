package com.ibs.code.service;

import java.util.List;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.ibs.code.result.DataValidationResult;
import com.ibs.components.filters.request.header.RequestHeaderContext;
import com.ibs.components.response.Response;
import com.ibs.components.response.ResponseContext;

/**
 * 
 * @author DougLei
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class ServiceValidateHandler {
	
	/**
	 * 使用验证器验证数据
	 * 返回单条数据的验证结果, 将验证未通过的数据加入到响应体 {@link Response} 的 validation 属性集合中
	 * @param data
	 * @param validators
	 * @return
	 */
	protected DataValidationResult validateByValidator(Object data, ServiceValidator... validators) {
		if(validators.length > 0) {
			ValidateFailResult result = null;
			for (ServiceValidator validator : validators) {
				if((result = validator.validate(-1, data, SessionContext.getSession(), RequestHeaderContext.getTokenEntity().getProjectId(), RequestHeaderContext.getTokenEntity().getCustomerId(), RequestHeaderContext.getTokenEntity().getDatabaseId())) != null) {
					ResponseContext.addValidation(data, result);
					return DataValidationResult.FAILURE;
				}
			}
		}
		return DataValidationResult.SUCCESS;
	}
	
	/**
	 * 使用验证器验证数据
	 * 返回批量数据的验证结果, 将验证未通过的数据加入到响应体 {@link Response} 的 validation 属性集合中
	 * @param datas
	 * @param validators
	 * @return
	 */
	protected DataValidationResult validateByValidator(List<? extends Object> datas, ServiceValidator... validators) {
		if(validators.length > 0) {
			ValidateFailResult result = null;
			for(int i=datas.size()-1;i>-1;i--) {
				for (ServiceValidator validator : validators) {
					if((result = validator.validate(i, datas.get(i), SessionContext.getSession(), RequestHeaderContext.getTokenEntity().getProjectId(), RequestHeaderContext.getTokenEntity().getCustomerId(), RequestHeaderContext.getTokenEntity().getDatabaseId())) != null) {
						ResponseContext.addValidation(datas.remove(i), result);
						break;
					}
				}
			}
		}
		return datas.isEmpty()?DataValidationResult.FAILURE:DataValidationResult.SUCCESS;
	}
}
