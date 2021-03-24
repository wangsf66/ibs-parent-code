package com.ibs.spring.resolver.method.argument;


import java.util.List;

/**
 * 
 * @author wangShuFang
 */
public class CommonParamsModel<T> {
    private List<T> list;
    private Boolean isBatch=false;
	
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public Boolean getIsBatch() {
		return isBatch;
	}
	public void setIsBatch(Boolean isBatch) {
		this.isBatch = isBatch;
	}
}
