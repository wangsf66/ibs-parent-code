package com.ibs.code.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.SessionFactoryContainer;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.Purpose;
import com.douglei.orm.sessionfactory.validator.table.handler.ValidateHandler;
import com.ibs.code.result.DataValidationResult;
import com.ibs.components.response.ResponseContext;

/**
 * 
 * @author DougLei
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class ControllerValidateHandler {
	
	@Autowired
	private SessionFactoryContainer sessionFactoryContainer;
	
	// ------------------------------------------------------------------------------------------------------
	// 根据验证器验证数据
	private ValidateFailResult validateByValidator(Object object, ValidateFailResult result, ControllerValidator... validators) {
		if(result == null && validators.length > 0) 
			for (ControllerValidator validator : validators) {
				if((result = validator.validate(object)) != null) {
					break;
			}
		}
		return result;
	}
	
	// 返回单条数据的验证结果, 将验证未通过的数据加入到响应体 {@link Response} 的 validation 属性集合中
	private DataValidationResult returnResult(Object originObject, ValidateFailResult validationResult) {
		if(validationResult == null) 
			return DataValidationResult.SUCCESS;
		ResponseContext.addValidation(originObject, validationResult);
		return DataValidationResult.FAILURE;
	}
	
	/**
	 * 针对表对象的验证
	 * @param object
	 * @param validateMode
	 * @param validators
	 * @return 
	 */
	protected DataValidationResult validate4Table(Object object, ValidateHandler validateMode, ControllerValidator... validators){
		ValidateFailResult resutl = validateByValidator(object, sessionFactoryContainer.get().getDataValidator().validate4TableMapping(object, validateMode), validators);
		return returnResult(object, resutl);
	}
	
	/**
	 * 针对表对象的验证
	 * @param tableName
	 * @param objectMap
	 * @param validateMode
	 * @param validators
	 * @return 
	 */
	protected DataValidationResult validate4Table(String tableName, Map<String, Object> objectMap, ValidateHandler validateMode, ControllerValidator... validators){
		ValidateFailResult resutl = validateByValidator(objectMap, sessionFactoryContainer.get().getDataValidator().validate4TableMapping(tableName, objectMap, validateMode), validators);
		return returnResult(objectMap, resutl);
	}
	
	/**
	 * 针对sql对象的验证
	 * @param purpose
	 * @param namespace
	 * @param name
	 * @param object
	 * @param validators
	 * @return 
	 */
	protected DataValidationResult validate4Sql(Purpose purpose, String namespace, String name, Object object, ControllerValidator... validators){
		ValidateFailResult resutl = validateByValidator(object, sessionFactoryContainer.get().getDataValidator().validate4SqlMapping(purpose, namespace, name, object), validators);
		return returnResult(object, resutl);
	}
	
	/**
	 * 仅仅使用验证器进行验证
	 * @param data
	 * @param validators
	 * @return
	 */
	protected DataValidationResult validateByValidator(Object data, ControllerValidator... validators) {
		if(validators.length > 0) {
			ValidateFailResult result = null;
			for (ControllerValidator validator : validators) {
				if((result = validator.validate(-1, data, Collections.emptyList())) != null) {
					return returnResult(data, result);
				}
			}
		}
		return DataValidationResult.SUCCESS;
	}
	
	// ------------------------------------------------------------------------------------------------------
	// 根据验证器验证数据
	private List<ValidateFailResult> validateByValidator(List<? extends Object> objects, List<ValidateFailResult> validationResults, ControllerValidator... validators) {
		if((validationResults == null || validationResults.size() < objects.size()) && validators.length > 0) {
			boolean beforeValidationResultsNotNull = validationResults!=null;
			int index = 0, beforeValidationResultsSize = beforeValidationResultsNotNull?validationResults.size():0;
			
			ValidateFailResult vr = null;
			for (Object object : objects) {
				if(beforeValidateSuccess(index, beforeValidationResultsNotNull, validationResults)) {
					for (ControllerValidator validator : validators) {
						if((vr = validator.validate(index, object, objects))!=null) {
							vr.setIndex(index);
							if(validationResults == null) {
								validationResults = new ArrayList<ValidateFailResult>(objects.size());
							}
							validationResults.add(vr);
							break;
						}
					}
				}
				index++;
			}
			
			if(beforeValidationResultsNotNull && beforeValidationResultsSize < validationResults.size()) {
				validationResults.sort(validationResultComparator);
			}
		}
		return validationResults;
	}
	
	// ValidationResult 比较器
	private Comparator validationResultComparator = new Comparator<ValidateFailResult>() {
		@Override
		public int compare(ValidateFailResult o1, ValidateFailResult o2) {
			if(o1.getIndex() > o2.getIndex()) {
				return 1;
			}else if(o1.getIndex() < o2.getIndex()) {
				return -1;
			}
			return 0;
		}
	};
	
	// 判断当前下标的数据, 之前是否验证通过, 只有验证通过了, 才有再使用验证器验证的资格
	private boolean beforeValidateSuccess(int index, boolean beforeValidationResultsNotNull, List<ValidateFailResult> validationResults) {
		if(beforeValidationResultsNotNull) {
			for (ValidateFailResult vr : validationResults) {
				if(vr.getIndex() > index) 
					break;
				if(vr.getIndex() == index) 
					return false;
			}
		}
		return true;
	}
	
	// 返回批量数据的验证结果, 将验证未通过的数据加入到响应体 {@link Response} 的 validation 属性集合中
	private DataValidationResult returnResult(List<? extends Object> originObjects, List<ValidateFailResult> validationResults){
		if(validationResults == null || validationResults.isEmpty()) 
			return DataValidationResult.SUCCESS;
		
		boolean partialDataValidatePass = originObjects.size() > validationResults.size();// 记录数据集合中, 是否有部分数据验证通过, 如果不是部分验证通过, 那就是全部都没通过
		ValidateFailResult vr = null;
		for(int i=validationResults.size()-1;i>-1;i--) {
			vr = validationResults.get(i);
			ResponseContext.addValidation(originObjects.remove(vr.getIndex()), vr);
		}
		return partialDataValidatePass?DataValidationResult.SUCCESS:DataValidationResult.FAILURE;
	}

	/**
	 * 针对表对象的验证
	 * @param objects
	 * @param validateMode
	 * @param validators
	 * @return {@link BasicController#returnValidationResult(List, List)}
	 */
	protected DataValidationResult validate4Table(List<? extends Object> objects, ValidateHandler validateMode, ControllerValidator... validators){
		List<ValidateFailResult> validationResults = validateByValidator(objects, sessionFactoryContainer.get().getDataValidator().validate4TableMapping(objects, validateMode), validators);
		return returnResult(objects, validationResults);
	}

	/**
	 * 针对表对象的验证
	 * @param tableName 
	 * @param objects
	 * @param validateMode
	 * @param validators
	 * @return {@link BasicController#returnValidationResult(List, List)}
	 */
	protected DataValidationResult validate4Table(String tableName, List<Map<String, Object>> objectMaps, ValidateHandler validateMode, ControllerValidator... validators){
		List<ValidateFailResult> validationResults = validateByValidator(objectMaps, sessionFactoryContainer.get().getDataValidator().validate4TableMapping(tableName, objectMaps, validateMode), validators);
		return returnResult(objectMaps, validationResults);
	}
	
	/**
	 * 针对sql对象的验证
	 * @param purpose
	 * @param namespace
	 * @param name 
	 * @param objects
	 * @param validators
	 * @return {@link BasicController#returnValidationResult(List, List)}
	 */
	protected DataValidationResult validate4Sql(Purpose purpose, String namespace, String name, List<? extends Object> objects, ControllerValidator... validators){
		List<ValidateFailResult> validationResults = validateByValidator(objects, sessionFactoryContainer.get().getDataValidator().validates4SqlMapping(purpose, namespace, name, objects), validators);
		return returnResult(objects, validationResults);
	}
	
	/**
	 * 仅仅使用验证器进行验证
	 * @param datas
	 * @param validators
	 * @return
	 */
	protected DataValidationResult validateByValidator(List<Object> datas, ControllerValidator... validators) {
		if(validators.length > 0) {
			List<ValidateFailResult> validationResults = null;
			int index = 0;
			ValidateFailResult vr = null;
			for (Object data : datas) {
				for (ControllerValidator validator : validators) {
					if((vr = validator.validate(index, data, datas))!=null) {
						vr.setIndex(index);
						if(validationResults == null) {
							validationResults = new ArrayList<ValidateFailResult>(datas.size());
						}
						validationResults.add(vr);
						break;
					}
				}
				index++;
			}
			return returnResult(datas, validationResults);
		}
		return returnResult(datas, Collections.emptyList());
	}
}
