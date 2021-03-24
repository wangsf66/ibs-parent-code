package com.ibs.code.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.context.SessionContext;
import com.douglei.tools.ExceptionUtil;
import com.douglei.tools.reflect.IntrospectorUtil;
import com.ibs.code.controller.BasicController;
import com.ibs.code.entity.BasicProperty;
import com.ibs.components.response.ResponseContext;

/**
 * 
 * @author DougLei
 */
public abstract class BasicService extends ServiceValidateHandler{
	private static final Logger logger = LoggerFactory.getLogger(BasicService.class);
	
	// -------------------------------------------------------------------------------------------------------------
	// table session save
	protected short tableSessionSave(Object data, BasicProperty... basicProperties) {
		return tableSessionSave(null, data, basicProperties);
	}
	
	// table session save
	protected void tableSessionSave(List<? extends Object> datas, BasicProperty... basicProperties) {
		tableSessionSave(null, datas, basicProperties);
	}
	
	// table session save
	protected void tableSessionSave(String code, List<? extends Object> datas, BasicProperty... basicProperties) {
		datas.forEach(data -> tableSessionSave(code, data, basicProperties));
	}
	
	// table session save
	protected short tableSessionSave(String tableName, Object data, BasicProperty... basicProperties) {
		try {
			setBasicPropertyValues(data, true, basicProperties);
			if(tableName == null) {
				SessionContext.getTableSession().save(data);
			}else {
				SessionContext.getTableSession().save(tableName, (Map<String,Object>)data);
			}
			ResponseContext.addData(data);
			return 1;
		} catch (Exception e) {
			logger.error("保存数据时出现异常: {}", ExceptionUtil.getStackTrace(e));
			ResponseContext.addError(data, e);
			return -1;
		}
	}
	
	// -------------------------------------------------------------------------------------------------------------
	// table session update
	protected short tableSessionUpdate(Object data, BasicProperty... basicProperties) {
		return tableSessionUpdate(null, data, false, basicProperties);
	}
	
	// table session update
	protected void tableSessionUpdate(List<? extends Object> datas, BasicProperty... basicProperties) {
		tableSessionUpdate(null, datas, false, basicProperties);
	}
	
	// table session update
	protected void tableSessionUpdate(String code, List<? extends Object> datas, BasicProperty... basicProperties) {
		tableSessionUpdate(code, datas, false, basicProperties);
	}
	
	// table session update
	protected short tableSessionUpdate(String code, Object data, BasicProperty... basicProperties) {
		return tableSessionUpdate(code, data, false, basicProperties);
	}
	
	
	// table session update
	protected short tableSessionUpdate(Object data, boolean updateNullValue, BasicProperty... basicProperties) {
		return tableSessionUpdate(null, data, updateNullValue, basicProperties);
	}
	
	// table session update
	protected void tableSessionUpdate(List<? extends Object> datas, boolean updateNullValue, BasicProperty... basicProperties) {
		tableSessionUpdate(null, datas, updateNullValue, basicProperties);
	}
	
	// table session update
	protected void tableSessionUpdate(String code, List<? extends Object> datas, boolean updateNullValue, BasicProperty... basicProperties) {
		datas.forEach(data -> tableSessionUpdate(code, data, updateNullValue, basicProperties));
	}
	
	// table session update
	protected short tableSessionUpdate(String tableName, Object data, boolean updateNullValue, BasicProperty... basicProperties) {
		try {
			setBasicPropertyValues(data, false, basicProperties);
			if(tableName == null) {
				SessionContext.getTableSession().update(data, updateNullValue);
			}else {
				SessionContext.getTableSession().update(tableName, (Map<String,Object>)data, updateNullValue);
			}
			ResponseContext.addData(data);
			return 1;
		} catch (Exception e) {
			logger.error("修改数据时出现异常: {}", ExceptionUtil.getStackTrace(e));
			ResponseContext.addError(data, e);
			return -1;
		}
	}
	
	// -------------------------------------------------------------------------------------------------------------
	// 给data设置基础属性的值
	@SuppressWarnings("unchecked")
	protected Object setBasicPropertyValues(Object data, boolean isSave, BasicProperty... basicProperties) {
		if(basicProperties.length == 0) {
			if(isSave) {
				basicProperties = BasicProperty.allPropertiesOnSave;
			}else {
				basicProperties = BasicProperty.allPropertiesOnUpdate;
			}
		}
		if(basicProperties[0] != BasicProperty.NONE) {
			if(data instanceof Map) {
				Map<String, Object> dataMap = (Map<String, Object>) data;
				for (BasicProperty basicProperty : basicProperties) {
					dataMap.put(basicProperty.getColumnName(), basicProperty.getValue());
				}
			}else {
				Map<String, Object> pv = new HashMap<String, Object>(isSave?16:4);
				for (BasicProperty basicProperty : basicProperties) {
					pv.put(basicProperty.getPropertyName(), basicProperty.getValue());
				}
				IntrospectorUtil.setValues(pv, data);
			}
		}
		return data;
	}
	
	// -------------------------------------------------------------------------------------------------------------
	// sql session execute update
	protected int sqlSessionExecuteUpdate(String namespace, String name, Object data) {
		try {
			int result = SessionContext.getSQLSession().executeUpdate(namespace, name, data);
			ResponseContext.addData(data);
			return result;
		} catch (Exception e) {
			logger.error("执行sql时出现异常: {}", ExceptionUtil.getStackTrace(e));
			ResponseContext.addError(data, e);
			return -1;
		}
	}
	
	// sql session execute update
	protected void sqlSessionExecuteUpdate(String namespace, String name, List<? extends Object> datas) {
		datas.forEach(data -> sqlSessionExecuteUpdate(namespace, name, data));
	}
	
	// -------------------------------------------------------------------------------------------------------------
	// sql session execute procedure
	protected int sqlSessionExecuteProcedure(String namespace, String name, Object data) {
		try {
			data = SessionContext.getSQLSession().executeProcedure(namespace, name, data);
			ResponseContext.addData(data);
			return 1;
		} catch (Exception e) {
			logger.error("执行sql存储过程时出现异常: {}", ExceptionUtil.getStackTrace(e));
			ResponseContext.addError(data, e);
			return -1;
		}
	}
	
	// sql session execute procedure
	protected void sqlSessionExecuteProcedure(String namespace, String name, List<? extends Object> datas) {
		datas.forEach(data -> sqlSessionExecuteProcedure(namespace, name, data));
	}
	
	// -------------------------------------------------------------------------------------------------------------
	/**
	 * 根据ids删除数据
	 * @param tableName
	 * @param ids
	 */
	protected void deleteByIds(String tableName, String ids) {
		deleteByIds(tableName, "ID", ids);
	}
	
	/**
	 * 根据ids删除数据
	 * @param tableName
	 * @param primaryKeyColumnName
	 * @param ids
	 */
	protected void deleteByIds(String tableName, String primaryKeyColumnName, String ids) {
		StringBuilder sql = new StringBuilder(100);
		sql.append("delete ").append(tableName).append(" where ").append(primaryKeyColumnName);
		List<Object> parameters;
		if(ids.indexOf(",") == -1) {
			sql.append(" =?");
			parameters = new ArrayList<Object>(1);
			parameters.add(ids);
		}else {
			sql.append(" in(");
			String[] ids_ = ids.split(",");
			parameters = new ArrayList<Object>(ids_.length);
			for (byte i=0;i<ids_.length;i++) {
				parameters.add(ids_[i]);
				sql.append("?");
				if(i<ids_.length-1) sql.append(",");
			}
			sql.append(")");
		}
		SessionContext.getSqlSession().executeUpdate(sql.toString(), parameters);
		addSingleResponseData(BasicController.deleteIdsKey, ids);
	}
	
	// -------------------------------------------------------------------------------------------------------------
	/**
	 * 添加单一的响应体数据
	 * @param key
	 * @param value
	 */
	protected void addSingleResponseData(String key, Object value) {
		Map<String, Object> response = new HashMap<String, Object>(2);
		response.put(key, value);
		ResponseContext.addData(response);
	}
}