package com.ibs.code.controller.validators;

import java.util.List;

import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.tools.StringUtil;
import com.ibs.code.controller.ControllerValidator;

/**
 * 参数值不能为空的验证器
 * @author DougLei
 */
public class ParameterNotBlankValidator implements ControllerValidator<Object> {
	private String parameterName;
	public ParameterNotBlankValidator(String parameterName) {
		this.parameterName = parameterName;
	}

	@Override
	public ValidateFailResult validate(int index, Object data, List<Object> validateDatas) {
		if(StringUtil.isEmpty(data)) 
			return new ValidateFailResult(parameterName, "%s参数值不能为空", "ibs.parameter.value.notblank", parameterName);
		return null;
	}
}
