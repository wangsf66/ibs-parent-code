package com.ibs.components.filters.dynamic.table;

import java.util.HashMap;
import java.util.Map;

import com.ibs.code.service.dynamic.table.DynamicTableService;

/**
 * 
 * @author DougLei
 */
public class DynamicTableIndexContext {
	private static final Map<String, Integer> cache = new HashMap<String, Integer>(8);
	private static final ThreadLocal<Integer> currentTableIndex = new ThreadLocal<Integer>();
	
	/**
	 * 给当前线程set tableIndex
	 * @param currentTableIndex_
	 */
	static void setCurrentTableIndex(Integer currentTableIndex_) {
		currentTableIndex.set(currentTableIndex_);
	}
	
	/**
	 * 从当前线程中获取tableIndex
	 * @return
	 */
	public static Integer getCurrentTableIndex() {
		return currentTableIndex.get();
	}
	
	// -----------------------------------------------------------------------------------------
	/**
	 * 获取tableIndex
	 * @param projectId
	 * @param service
	 * @return
	 */
	public static Integer getIndex(String projectId, DynamicTableService service) {
		Integer index = null;
		if(cache.isEmpty() || (index = cache.get(projectId)) == null) {
			index = service.queryDynamicTableIndex(projectId);
			if(index > -1) {
				putIndex(projectId, index);
			}
		}
		return index;
	}

	/**
	 * 
	 * @param projectId
	 * @param index
	 */
	public static void putIndex(String projectId, Integer index) {
		cache.put(projectId, index);
	}
	
	/**
	 * 移除指定projectId的tableIndex
	 * @param projectId
	 */
	public static void removeIndex(String projectId) {
		if(!cache.isEmpty()) 
			cache.remove(projectId);
	}
}
