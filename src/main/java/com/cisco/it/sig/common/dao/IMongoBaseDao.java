package com.cisco.it.sig.common.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Query;

import com.cisco.dnac.common.entity.DNACEntity;

public interface IMongoBaseDao {

	public void create(DNACEntity entity);

	public void save(DNACEntity entity);

	public void delete(String id);

	int delete(Query query, Class<?> entityClass);

	int delete(Query query, Class<?> entityClass, String entityCollection);

	public <T> T findById(ObjectId id, Class<T> targetClass, String entityCollection);

	public <T> T findByQuery(Query query, Class<T> entityClass, String entityCollection);

	<T> List<T> findAll(Query query, Class<T> entityClass, String entityCollection);

	public <T> List<T> findAll(Class<T> entityClass);

	long getCount(Query query, Class<?> entityClass, String entityCollection);

	boolean exists(Query query, Class<?> entityClass, String entityCollection);
}
