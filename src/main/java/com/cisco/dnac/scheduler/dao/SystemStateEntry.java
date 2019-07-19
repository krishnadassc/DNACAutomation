
package com.cisco.dnac.scheduler.dao;

import org.springframework.data.mongodb.core.mapping.Document;

import com.cisco.dnac.common.entity.DNACEntity;
import com.cisco.dnac.scheduler.dto.SchedulerConstants;

@Document
public class SystemStateEntry implements DNACEntity{
	
	
	private static final long serialVersionUID = 1L;

	private String id;

	private String property;

	private String value;

	private long lastUpdated;

	private boolean isDisabled = false;

	private long frequency = 0L;

	private String agent;

	private String systemTaskPolicy = SchedulerConstants.DEFAULT_SYSTEM_TASK_POLICY;

	private long startTime;

	private long endTime;

	private long duration;

	private long initialDelay;

	private String agentIpAddress;

	private String nodeName;

	private String category;

	private String label;

	private String description;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSystemTaskPolicy() {
		return systemTaskPolicy;
	}

	public void setSystemTaskPolicy(String systemTaskPolicy) {
		this.systemTaskPolicy = systemTaskPolicy;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public boolean isDisabled() {
		return isDisabled;
	}

	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public SystemStateEntry() {
	}

	public long getFrequency() {
		return frequency;
	}

	public void setFrequency(long frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @param property the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		if ((value != null) && (value.length() > 255)) {
			value = value.substring(0, 255);
		}

		this.value = value;
	}

	/**
	 * @return the lastUpdated
	 */
	public long getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * @param lastUpdated the lastUpdated to set
	 */
	public void setLastUpdated(long lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getInitialDelay() {
		return initialDelay;
	}

	public void setInitialDelay(long initialDelay) {
		this.initialDelay = initialDelay;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getAgentIpAddress() {
		return agentIpAddress;
	}

	public void setAgentIpAddress(String agentIpAddress) {
		this.agentIpAddress = agentIpAddress;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public static void main(String[] args) {

	}
}
