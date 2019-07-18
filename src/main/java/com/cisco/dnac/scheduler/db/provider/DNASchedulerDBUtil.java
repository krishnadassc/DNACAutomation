package com.cisco.dnac.scheduler.db.provider;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.cisco.dnac.scheduler.JDBCObjStore;
import com.cisco.dnac.scheduler.ObjStore;
import com.cisco.dnac.scheduler.dao.RemoteTaskRunInfo;
import com.cisco.dnac.scheduler.dao.SystemStateEntry;
import com.cisco.it.sig.common.dao.ICommonDao;

public class DNASchedulerDBUtil {

	@Autowired
	private ICommonDao dao;

	private static final Logger logger = Logger.getLogger(DNASchedulerDBUtil.class);

	public void cleanupTaskStatus() {
		try {
			Query query = getActiveScheduleQuery();
			SystemStateEntry systemStateEntry = dao.findByQuery(query , SystemStateEntry.class, "SystemStateEntry");
			systemStateEntry.setValue("STATUS_CANCELLED");
			dao.save(systemStateEntry);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.warn("Exception Occured in ", e);
		}
	}

	  public static Query getActiveScheduleQuery() {
		    Query query = new Query();
		    Criteria criteriaOr = new Criteria();
		    criteriaOr.orOperator(Criteria.where("value").is("STATUS_IN_SCHEDULED")
		            , Criteria.where("value").is("STATUS_IN_SCHEDULED"));
		    query.addCriteria(criteriaOr);
		    return query;
		  }
	  
	public SystemStateEntry getSystemStateProperty(String property) {

		SystemStateEntry entry = null;
		try {

			JDBCObjStore<SystemStateEntry> store = JDBCObjStoreHelper.getStore(SystemStateEntry.class);

			List<SystemStateEntry> list = store.query("property == '" + property + "'");
			if (list != null && !list.isEmpty()) {
				entry = list.get(0);
			} else {
				logger.error("SystemStateEntry not found); property=" + property);
			}
		} catch (Exception ex) {
			logger.warn("Error while reading system state for property " + property, ex);
		}
		return entry;
	}
	
	public  SystemStateEntry getStatus(String taskName)
    {
        SystemStateEntry stateEntry = getSystemStateProperty("task." + taskName);

        if (stateEntry == null)
        {
            stateEntry = new SystemStateEntry();
            stateEntry.setProperty("task." + taskName);
        }

        logger.debug("Current System task status " + taskName + ":" + stateEntry.getValue());
        return stateEntry;
    }
	
	public  void updateStatus(SystemStateEntry status)
    {
        try
        {
            logger.debug("Task: " + status.getProperty() + " changed state to " + status.getValue());
            ObjStore<SystemStateEntry> store = ObjStoreHelper.getStore(SystemStateEntry.class);
            
            // store.delete("property == '" + status.getProperty() + "'");
            // store.insert(status);
             String query = "property == '" + status.getProperty() + "'";
             store.modifySingleObject(query, status);
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
        	logger.warn("Exception Occured in " ,e);
        }

    }
    
    public  void archiveStatus(RemoteTaskRunInfo runInfo)
    {
        try
        {
        	
            ObjStore<RemoteTaskRunInfo> store = ObjStoreHelper.getStore(RemoteTaskRunInfo.class);

            store.insert(runInfo);
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
        	logger.warn("Exception Occured in " ,e);
        }
    }
    
    

    public  boolean isTaskDisabled(String taskName)
    {
        SystemStateEntry state = getStatus(taskName);
        return state.isDisabled();

    }

    public  List<SystemStateEntry> getTasksByAgentAndStatus(String agent, String status)
    {
        List<SystemStateEntry> tasks = null;
        ObjStore<SystemStateEntry> store = ObjStoreHelper.getStore(SystemStateEntry.class);
        try
        {
        	String query = "nodeName == '" + agent + "' && value == '" + status + "'";
            tasks = store.query(query);
            logger.debug("query to update agent status " + query);
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
        	logger.warn("Exception Occured in " ,e);
        }
        return tasks;
    }
    
    public  void updateSystemStateProperty(SystemStateEntry stateEntry) {
		try {
			ObjStore<SystemStateEntry> store = ObjStoreHelper
					.getStore(SystemStateEntry.class);

			// SystemStateCache.getInstance().update(stateEntry);

			store.modifySingleObject("property == '" + stateEntry.getProperty()
					+ "'", stateEntry);
		} catch (Exception ex) {
			logger.warn("Error while updating system state for property "
					+ stateEntry.getProperty(), ex);
		}
	}
    
    public void updateSystemStateProperty(String property, String value) {
		try {
			ObjStore<SystemStateEntry> store = ObjStoreHelper
					.getStore(SystemStateEntry.class);

			// ksaminat - getting the existing SystemStateEntry object, if its
			// null creating newly
			SystemStateEntry stateEntry = getSystemStateProperty(property);

			if (stateEntry == null) {
				stateEntry = new SystemStateEntry();
			}

			stateEntry.setLastUpdated(System.currentTimeMillis());
			stateEntry.setProperty(property);

			if ((value != null) && (value.length() > 255)) {
				value = value.substring(0, 255);
			}

			stateEntry.setValue(value);

			// SystemStateCache.getInstance().update(stateEntry);

			store.modifySingleObject("property == '" + property + "'",
					stateEntry);
		} catch (Exception ex) {
			logger.warn("Error while updating system state for property "
					+ property, ex);
		}
	}
    

	public  void deleteSystemStateProperty(String property) {
		try {
			// Delete from the cache first
			// SystemStateCache.getInstance().delete(property);

			JDBCObjStore<SystemStateEntry> store = JDBCObjStoreHelper
					.getStore(SystemStateEntry.class);
			store.delete("property == '" + property + "'");
		} catch (Exception ex) {
			logger.warn("Error while deleting system state for property "
					+ property, ex);
		}
	}
}
