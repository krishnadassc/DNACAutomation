package com.cisco.dnac.scheduler.db.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.cisco.dnac.scheduler.dao.ScheduleTaskDAO;
import com.cisco.dnac.scheduler.dto.ScheduleTask;
import com.cisco.it.sig.common.dao.ICommonDao;

@Service
public class DNASchedulerDBUtil {

	@Autowired
	private ICommonDao dao;

	private static final Logger logger = Logger.getLogger(DNASchedulerDBUtil.class);

	public List<ScheduleTaskDAO> getScheduledTaskList() {
		List<ScheduleTaskDAO> list = null;
		try {
			list = dao.findAll(ScheduleTaskDAO.class);

		} catch (Exception e) {
			logger.error("Exception while getting details ", e);
		}
		return list;
	}

	public List<ScheduleTask> convertDaoToDtO(List<ScheduleTaskDAO> list) {
		List<ScheduleTask> taskList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ScheduleTaskDAO taskDao = list.get(i);
			ScheduleTask task = new ScheduleTask();

			task.setDescription(taskDao.getDescription());
			task.setTaskDetails(taskDao.getTaskDetails());
			task.setTaskName(taskDao.getTaskName());
			task.setTimeInMilliseconds(taskDao.getTimeInMilliSeconds());
			task.setId(taskDao.getId());
			task.setStatus(taskDao.getStatus());
			taskList.add(task);
		}
		return taskList;

	}

	public ScheduleTaskDAO createSchedulerTask(ScheduleTask scheduleTask) {
		ScheduleTaskDAO task = null;
		try {
			task = new ScheduleTaskDAO();
			task.setDescription(scheduleTask.getDescription());
			task.setStatus(scheduleTask.getStatus());
			task.setTaskDetails(scheduleTask.getTaskDetails());
			task.setTaskName(scheduleTask.getTaskName());
			task.setTimeInMilliSeconds(scheduleTask.getTimeInMilliseconds());
			// enataraj : Need actual id.
			dao.save(task);
			logger.info("TaskId------"+task.getId());
			

		} catch (Exception e) {
			logger.error("Exception while create task ", e);
		}
		return task;
	}

	public void updateSchedulerTask(ObjectId taskId, String status) {
		try {
			logger.info("-----------"+taskId);
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(taskId));
			dao.updateAll(query, new Update().set("status", status), ScheduleTaskDAO.class);
			logger.info("-----------"+taskId);
			logger.info("Status is updated into table : " + status);

		} catch (Exception e) {
			logger.error("Exception while update the task ", e);
		}
	}

	public void deleteScheduledTask(String taskId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(taskId));
			dao.delete(query, ScheduleTaskDAO.class);
		} catch (Exception e) {
			logger.error("Exception while delete the task from table ", e);
		}
	}

}
