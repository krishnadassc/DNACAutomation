package com.cisco.dnac.common.entity;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sampledoc")
public class SampleEntity implements DNACEntity{


	private static final long serialVersionUID = -7228054365612910529L;

	private ObjectId id;

	private String trId;

	private Date timeCreated;

	private int priority;


	@Id
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Date getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}

	/**
	 * @return the trId
	 */
	public String getTrId() {
		return trId;
	}

	/**
	 * @param trId
	 *            the trId to set
	 */
	public void setTrId(String trId) {
		this.trId = trId;
	}

}
