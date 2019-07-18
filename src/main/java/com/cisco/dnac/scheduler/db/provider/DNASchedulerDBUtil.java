package com.cisco.dnac.scheduler.db.provider;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

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
			SystemStateEntry systemStateEntry = dao.findByQuery(query, SystemStateEntry.class, "SystemStateEntry");
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
		criteriaOr.orOperator(Criteria.where("value").is("STATUS_IN_PROGRESS"),
				Criteria.where("value").is("STATUS_IN_SCHEDULED"));
		query.addCriteria(criteriaOr);
		return query;
	}

	public SystemStateEntry getSystemStateProperty(String property) {

		SystemStateEntry entry = null;
		try {

			Query query = new Query();
			query.addCriteria(Criteria.where("property").is(property));
			List<SystemStateEntry> list = dao.findAll(query, SystemStateEntry.class, "SystemStateEntry");

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

	public SystemStateEntry getStatus(String taskName) {
		SystemStateEntry stateEntry = getSystemStateProperty("task." + taskName);

		if (stateEntry == null) {
			stateEntry = new SystemStateEntry();
			stateEntry.setProperty("task." + taskName);
		}

		logger.debug("Current System task status " + taskName + ":" + stateEntry.getValue());
		return stateEntry;
	}

	public void updateStatus(SystemStateEntry status) {
		try {
			logger.debug("Task: " + status.getProperty() + " changed state to " + status.getValue());

			Query query = new Query();
			query.addCriteria(Criteria.where("property").is(status.getProperty()));

			SystemStateEntry systemStateEntry = dao.findByQuery(query, SystemStateEntry.class, "SystemStateEntry");

			// enataraj : may need to revisit (Modify single object)
			status.setId(systemStateEntry.getId());

			dao.save(status);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.warn("Exception Occured in ", e);
		}

	}

	public void archiveStatus(RemoteTaskRunInfo runInfo) {
		try {
			dao.save(runInfo);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.warn("Exception Occured in ", e);
		}
	}

	public boolean isTaskDisabled(String taskName) {
		SystemStateEntry state = getStatus(taskName);
		return state.isDisabled();

	}

	/*
	 * public List<SystemStateEntry> getTasksByAgentAndStatus(String agent, String
	 * status) { List<SystemStateEntry> tasks = null; ObjStore<SystemStateEntry>
	 * store = ObjStoreHelper.getStore(SystemStateEntry.class); try { String query =
	 * "nodeName == '" + agent + "' && value == '" + status + "'"; tasks =
	 * store.query(query); logger.debug("query to update agent status " + query); }
	 * catch (Exception e) { // TODO Auto-generated catch block
	 * logger.warn("Exception Occured in ", e); } return tasks; }
	 */

	public void updateSystemStateProperty(SystemStateEntry stateEntry) {

		try {
			logger.debug("Task: " + stateEntry.getProperty() + " changed state to " + stateEntry.getValue());

			Query query = new Query();
			query.addCriteria(Criteria.where("property").is(stateEntry.getProperty()));

			SystemStateEntry systemStateEntry = dao.findByQuery(query, SystemStateEntry.class, "SystemStateEntry");

			// enataraj : may need to revisit (Modify single object)
			stateEntry.setId(systemStateEntry.getId());

			dao.save(stateEntry);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.warn("Error while updating system state for property ", e);
		}

	}

	public void updateSystemStateProperty(String property, String value) {
		try {

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

			Query query = new Query();
			query.addCriteria(Criteria.where("property").is(property));

			SystemStateEntry systemStateEntry = dao.findByQuery(query, SystemStateEntry.class, "SystemStateEntry");

			stateEntry.setId(systemStateEntry.getId());
			dao.save(stateEntry);

		} catch (Exception ex) {
			logger.warn("Error while updating system state for property " + property, ex);
		}
	}

	public void deleteSystemStateProperty(String property) {
		try {
			// Delete from the cache first
			// SystemStateCache.getInstance().delete(property);

			Query query = new Query();
			query.addCriteria(Criteria.where("property").is(property));

			SystemStateEntry systemStateEntry = dao.findByQuery(query, SystemStateEntry.class, "SystemStateEntry");

			dao.delete(systemStateEntry.getId());

		} catch (Exception ex) {
			logger.warn("Error while deleting system state for property " + property, ex);
		}
	}
}
