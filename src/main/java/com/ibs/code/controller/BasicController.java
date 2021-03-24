package com.ibs.code.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.tools.StringUtil;
import com.ibs.code.result.DataValidationResult;

/**
 * 
 * @author DougLei
 */
public abstract class BasicController extends ControllerValidateHandler{
	public static String deleteIdsKey = "ids";// 要删除的ids key值

	/**
	 * 获取要删除的ids值
	 * @param request
	 * @return 判断返回值 !=null 时, 可以进行实际的delete操作
	 */
	protected String getDeleteIds(HttpServletRequest request) {
		String ids = request.getParameter(deleteIdsKey);
		if(validateByValidator(ids, validateIdsNotNullWhenDelete) == DataValidationResult.SUCCESS) {
			return ids;
		}
		return null;
	}
	
	// 在删除时验证ids值不为空
	private ControllerValidator<String> validateIdsNotNullWhenDelete = new ControllerValidator<String>() {
		@Override
		public ValidateFailResult validate(int index, String ids, List<String> validateDatas) {
			if(StringUtil.isEmpty(ids)) 
				return new ValidateFailResult(BasicController.deleteIdsKey, "删除数据时, ids参数值不能为空", "ibs.data.delete.ids.notnull");
			return null;
		}
	};
}