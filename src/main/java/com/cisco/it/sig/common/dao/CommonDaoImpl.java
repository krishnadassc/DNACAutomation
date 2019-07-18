package com.cisco.it.sig.common.dao;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.cisco.dnac.common.entity.DNACEntity;
import com.mongodb.WriteResult;

@Service("commonDao")
public class CommonDaoImpl implements ICommonDao {

  @Autowired
  protected MongoTemplate mongotemplate;

  private Logger logger = Logger.getLogger(CommonDaoImpl.class);

  public void create(DNACEntity entity) {
    mongotemplate.save(entity);
  }

  public void save(DNACEntity entity) {
    mongotemplate.save(entity);
  }

  public void save(DNACEntity entity, String collectionName) {
    mongotemplate.save(entity, collectionName);
  }

  public void delete(String id) {
    mongotemplate.remove(id);
  }

  public int delete(Query query, Class<?> entityClass) {
    WriteResult result = mongotemplate.remove(query, entityClass);
    return result.getN();
  }

  public int delete(Query query, Class<?> entityClass, String entityCollection) {
    WriteResult result = mongotemplate.remove(query, entityClass, entityCollection);
    return result.getN();
  }



  public List<Object> distinct(Query query, String entityCollection, String field) {
    return mongotemplate.getCollection(entityCollection).distinct(field, query.getQueryObject());
  }

  public <T> T findByQuery(Query query, Class<T> entityClass, String entityCollection) {
    return mongotemplate.findOne(query, entityClass, entityCollection);
  }

  public <T> List<T> findAll(Query query, Class<T> entityClass, String entityCollection) {
    return mongotemplate.find(query, entityClass, entityCollection);
  }

  public <T> List<T> findAll(Class<T> entityClass) {
    return mongotemplate.findAll(entityClass);
  }

  public long getCount(Query query, Class<?> entityClass, String entityCollection) {
    return mongotemplate.count(query, entityClass, entityCollection);
  }

  public boolean exists(Query query, Class<?> entityClass, String entityCollection) {
    return mongotemplate.exists(query, entityClass, entityCollection);
  }

  public DNACEntity updateEntity(Query query, Update update, Class<?> entityClass) {
    FindAndModifyOptions options = new FindAndModifyOptions();
    options.returnNew(true);
    DNACEntity mediaGatewayEntity = (DNACEntity) mongotemplate.findAndModify(query,
            update, options, entityClass);
    logger.trace("Query: " + query.toString() + "; Update: " + update.toString()
            + "; Update Status:" + (mediaGatewayEntity == null ? "No update" : "updated"));
    return mediaGatewayEntity;
  }

  public int updateAll(Query query, Update update, Class<?> entityClass) {
    WriteResult result = mongotemplate.updateMulti(query, update, entityClass);
    logger.trace("Query: " + query.toString() + "; Update: " + update.toString()
            + "; Number of updated documents: " + result.getN());
    return result.getN();
  }


  public void insertAll(List<? extends DNACEntity> entityCollection) {
    mongotemplate.insertAll(entityCollection);
  }

  public void insert(String collectionName,
    Collection<? extends DNACEntity> entityCollection) {
    mongotemplate.insert(entityCollection, collectionName);
  }

  public void removeAll(Class<? extends DNACEntity> entityClass) {
    Query query = new Query();
    mongotemplate.remove(query, entityClass);
  }

  public void setMongotemplate(MongoTemplate mongotemplate) {
    this.mongotemplate = mongotemplate;
  }

  public AggregationResults<String> aggregate(Aggregation aggregation, String collectionName,
    Class<String> class1) {
    return this.mongotemplate.aggregate(aggregation, collectionName, String.class);
  }

	public <T> T findById(ObjectId id, Class<T> targetClass, String entityCollection) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public long getEntityCount(Query query, Class<?> entityClass) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public boolean removeEntityById(DNACEntity object, String colletionName) {
		// TODO Auto-generated method stub
		return false;
	}
}
