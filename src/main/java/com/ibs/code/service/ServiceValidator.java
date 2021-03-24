package com.ibs.code.service;

import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.orm.sessionfactory.sessions.Session;

/**
 * 
 * @author DougLei
 */
public interface ServiceValidator<VD> {
	
	/**
	 * 如果返回null, 则表示验证通过, 否则返回 {@link ValidationResult} 或其子类
	 * @param index 当前验证对象的下标, 如果是单个对象的话, 则该值为-1, 如果要使用这个下标, 从集合中找数据的话, 一定是从后向前循环, 而不是我们常见的从前向后循环, 即 for(int i=0;i<index,i++) 或 for(int i=index-1, i>=0;i--)
	 * @param currentValidateData 当前要验证的数据
	 * @param session 
	 * @param projectId
	 * @param customerId
	 * @param databaseId
	 * @return
	 */
	ValidateFailResult validate(int index, VD currentValidateData, Session session, String projectId, String customerId, String databaseId);
}
