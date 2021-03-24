package com.ibs.code.entity;

import java.util.Date;

import com.ibs.components.filters.request.header.RequestHeaderContext;

/**
 * 基础属性
 * @author DougLei
 */
public enum BasicProperty {
	NONE,
	
	CREATE_USER_ID("createUserId"){
		@Override
		public Object getValue() {
			return RequestHeaderContext.getTokenEntity().getAccountId();
		}
	},
	CREATE_DATE("createDate"){
		@Override
		public Object getValue() {
			return new Date();
		}
	},
	
	
	LAST_UPDATE_USER_ID("lastUpdateUserId"){
		@Override
		public Object getValue() {
			return RequestHeaderContext.getTokenEntity().getAccountId();
		}
	},
	LAST_UPDATE_DATE("lastUpdateDate"){
		@Override
		public Object getValue() {
			return new Date();
		}
	},
	
	
	PROJECT_ID("projectId"){
		@Override
		public Object getValue() {
			return RequestHeaderContext.getTokenEntity().getProjectId();
		}
	},
	
	
	CUSTOMER_ID("customerId"){
		@Override
		public Object getValue() {
			return RequestHeaderContext.getTokenEntity().getCustomerId();
		}
	};
	
	private String propertyName;
	private BasicProperty() {
	}
	private BasicProperty(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public String getColumnName() {
		return name();
	}
	public Object getValue() {
		return null;
	}
	
	/**
	 * 保存时使用的所有基础属性
	 */
	public static final BasicProperty[] allPropertiesOnSave = {
			CREATE_USER_ID, CREATE_DATE,
			LAST_UPDATE_USER_ID, LAST_UPDATE_DATE,
			PROJECT_ID, CUSTOMER_ID
	};
	
	/**
	 * 修改时使用的所有基础属性
	 */
	public final static BasicProperty[] allPropertiesOnUpdate = { LAST_UPDATE_USER_ID, LAST_UPDATE_DATE };
}
