package com.ibs.code.service.dynamic.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.SessionFactoryContainer;
import com.douglei.orm.context.Transaction;
import com.douglei.orm.context.TransactionComponent;
import com.douglei.orm.mapping.MappingTypeNameConstants;
import com.douglei.orm.mapping.handler.MappingHandleException;
import com.douglei.orm.mapping.handler.entity.AddOrCoverMappingEntity;
import com.douglei.orm.mapping.handler.entity.DeleteMappingEntity;
import com.douglei.orm.mapping.handler.entity.MappingEntity;
import com.douglei.orm.mapping.handler.entity.ParseMappingException;
import com.douglei.tools.ExceptionUtil;
import com.ibs.code.entity.BasicProperty;
import com.ibs.code.service.BasicService;
import com.ibs.components.filters.dynamic.table.DynamicTableConfigurationProperties;
import com.ibs.components.filters.dynamic.table.DynamicTableIndexContext;
import com.ibs.components.filters.dynamic.table.entity.DynamicTable;
import com.ibs.components.filters.request.header.RequestHeaderContext;
import com.ibs.components.response.ResponseContext;

/**
 * 
 * @author DougLei
 */
@TransactionComponent
public class DynamicTableService extends BasicService{
	private static final Logger logger = LoggerFactory.getLogger(DynamicTableService.class);
	
	@Autowired
	private DynamicTableConfigurationProperties dynamicTableConfig;
	
	@Autowired
	private SessionFactoryContainer container;
	
	/**
	 * 查询动态表的索引
	 * @param projectId
	 * @return
	 */
	@Transaction
	public Integer queryDynamicTableIndex(String projectId) {
		Object[] index = SessionContext.getSqlSession().uniqueQuery_("select index_ from " + dynamicTableConfig.getName() + " where project_id=?", Arrays.asList(projectId));
		if(index.length == 0) {
			return -1;
		}
		return Integer.parseInt(index[0].toString());
	}
	
	/**
	 * 系统启动时加载所有满足条件的动态表映射信息
	 * @param projectName
	 * @param mappingTemplates
	 * @throws ParseMappingException 
	 * @throws MappingHandleException 
	 * @throws DynamicTableSystemAlreadyStartException 
	 */
	@Transaction
	public void start(String projectName, String[] mappingTemplates) throws ParseMappingException, MappingHandleException {
		projectName = projectName.toUpperCase();
		dynamicTableConfig.setProjectInfo(projectName);
		
		List<DynamicTable> dynamicTables = SessionContext.getTableSession().query(DynamicTable.class, "select index_, project_id from " + dynamicTableConfig.getName());
		if(!dynamicTables.isEmpty()) {
			short limit = (short) (mappingTemplates.length * 10);
			List<MappingEntity> mappingEntities = new ArrayList<MappingEntity>(limit);
			DynamicTable dynamicTable;
			for (short i = 0; i < dynamicTables.size(); i++) {
				dynamicTable = dynamicTables.get(i);
				
				for (String mappingTemplate : mappingTemplates) {
					mappingEntities.add(new AddOrCoverMappingEntity(String.format(mappingTemplate, dynamicTable.getIndex()), MappingTypeNameConstants.TABLE));
				}
				if(mappingEntities.size() == limit || i == dynamicTables.size()-1) {
					container.get().getMappingHandler().execute(mappingEntities);
					mappingEntities.clear();
				}
				
				DynamicTableIndexContext.putIndex(dynamicTable.getProjectId(), dynamicTable.getIndex());
			}
		}
	}
	
	/**
	 * 初始化指定项目的动态表
	 * @param mappingTemplates
	 * @throws Exception 
	 */
	@Transaction
	public synchronized void initial(String[] mappingTemplates) throws Exception {
		String projectId = RequestHeaderContext.getTokenEntity().getProjectId();
		String dynamicTableName = dynamicTableConfig.getName();
		if(SessionContext.getSqlSession().uniqueQuery_("select id from " + dynamicTableName + " where project_id=?", Arrays.asList(projectId)) == null) {
			try {
				Object[] count_index = SessionContext.getSqlSession().uniqueQuery_("select count(id) , max(index_)+1 from " + dynamicTableName);
				validateDynamicTableCount(Short.parseShort(count_index[0].toString())); // 验证动态表的数量
				
				DynamicTable dynamicTable = new DynamicTable();
				dynamicTable.setIndex(Integer.parseInt(count_index[1].toString()));
				if(tableSessionSave(dynamicTable, BasicProperty.CREATE_USER_ID, BasicProperty.CREATE_DATE, BasicProperty.PROJECT_ID) == 1) {
					MappingEntity[] mappingEntities = new MappingEntity[mappingTemplates.length];
					for (byte i = 0; i < mappingTemplates.length; i++) {
						mappingEntities[i] = new AddOrCoverMappingEntity(String.format(mappingTemplates[i], dynamicTable.getIndex()), MappingTypeNameConstants.TABLE);
					}
					container.get().getMappingHandler().execute(mappingEntities);
					DynamicTableIndexContext.putIndex(projectId, dynamicTable.getIndex());
				}
			} catch (Exception e) {
				DynamicTableIndexContext.removeIndex(projectId);
				logger.error("初始化projectId为[{}]的[{}]动态表时出现异常: {}", projectId, dynamicTableName, ExceptionUtil.getStackTrace(e));
				ResponseContext.addError("初始化["+dynamicTableConfig.getProjectName()+"]表时出现异常", e);
				throw e;
			}
		}else {
			ResponseContext.addValidation("项目[%s]已经存在[%s]表", "ibs.dynamic.table.exists", projectId, dynamicTableConfig.getProjectName()); // TODO 这块的提示信息 projectId, 后续换成project name
		}
	}

	// 验证动态表的数量
	private void validateDynamicTableCount(short count) throws Exception {
		if(count > dynamicTableConfig.getCountCeiling()) {
			throw new Exception("[" + dynamicTableConfig.getProjectName() + "]的动态表数量已达到上限(" + dynamicTableConfig.getCountCeiling() +"个), 请联系系统开发人员");
		}
		if(count >= dynamicTableConfig.getWarnCountCeiling()) {
			ResponseContext.setWarn(dynamicTableConfig.getProjectName()+"动态表的数量("+count+")即将达到上限("+dynamicTableConfig.getWarnCountCeiling()+"), 请注意");
		}
	}

	/**
	 * 销毁指定项目的动态表
	 * @param mappingCodes
	 * @throws Exception 
	 */
	@Transaction
	public synchronized void destroy(String[] mappingCodes) throws Exception {
		String projectId = RequestHeaderContext.getTokenEntity().getProjectId();
		String dynamicTableName = dynamicTableConfig.getName();
		DynamicTable dt = SessionContext.getSqlSession().uniqueQuery(DynamicTable.class, "select id, index_ from " + dynamicTableName + " where project_id=?", Arrays.asList(projectId));
		if(dt == null) {
			ResponseContext.addValidation("项目[%s]不存在[%s]表, 无法销毁", "ibs.dynamic.table.unexists", projectId, dynamicTableConfig.getProjectName()); // TODO 这块的提示信息 projectId, 后续换成project name
		}else {
			try {
				SessionContext.getSqlSession().executeUpdate("delete " + dynamicTableName + " where id=?", Arrays.asList(dt.getId()));
				
				MappingEntity[] mappingEntities = new MappingEntity[mappingCodes.length];
				for (byte i = 0; i < mappingCodes.length; i++) {
					mappingEntities[i] = new DeleteMappingEntity(mappingCodes[i]+dt.getIndex());
				}
				container.get().getMappingHandler().execute(mappingEntities);
				
				DynamicTableIndexContext.removeIndex(projectId);
			} catch (Exception e) {
				DynamicTableIndexContext.putIndex(projectId, dt.getIndex());
				logger.error("销毁projectId为[{}]的[{}]动态表时出现异常: {}", projectId, dynamicTableName, ExceptionUtil.getStackTrace(e));
				ResponseContext.addError("销毁projectId为["+projectId+"]的["+dynamicTableConfig.getProjectName()+"]动态表时出现异常", e);
				throw e;
			}
		}
	}
}