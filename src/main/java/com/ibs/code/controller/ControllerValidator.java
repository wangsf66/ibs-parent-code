package com.ibs.code.controller;

import java.util.Collections;
import java.util.List;

import com.douglei.orm.mapping.validator.ValidateFailResult;

/**
 * 
 * @author DougLei
 */
public interface ControllerValidator<VD> {
	
	/**
	 * 如果返回null, 则表示验证通过, 否则返回 {@link ValidationResult} 或其子类
	 * @param currentValidateData 当前要验证的数据
	 * @return
	 */
	default ValidateFailResult validate(VD currentValidateData) {
		return validate(-1, currentValidateData, Collections.emptyList());
	}
	
	/**
	 * 如果返回null, 则表示验证通过, 否则返回 {@link ValidationResult} 或其子类
	 * @param index 当前验证对象的下标, 如果是单个对象的话, 则该值为-1; 如果要和集合中其他数据比较, 一定只和index前的数据比较, 不要和index后的数据比较, 即 for(int i=0;i<index-1;i++)
	 * @param currentValidateData 当前要验证的数据
	 * @param validateDatas 如果验证的是集合, 则该参数值就为该集合, 如果验证的单个对象, 则该参数值为size=0的空集合
	 * @return
	 */
	ValidateFailResult validate(int index, VD currentValidateData, List<VD> validateDatas);
}
