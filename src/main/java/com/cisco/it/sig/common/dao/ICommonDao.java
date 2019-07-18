package com.cisco.it.sig.common.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.cisco.dnac.common.entity.DNACEntity;

public interface ICommonDao extends IMongoBaseDao {

  public DNACEntity updateEntity(Query query, Update update, Class<?> entityClass);

  public int updateAll(Query query, Update update, Class<?> entityClass);


  public long getEntityCount(Query query, Class<?> entityClass);

  public boolean removeEntityById(DNACEntity object, String colletionName);

  void insert(String collectionName, Collection<? extends DNACEntity> entityCollection);

  void insertAll(List<? extends DNACEntity> entityCollection);

  public void removeAll(Class<? extends DNACEntity> entityClass);

  public AggregationResults<String> aggregate(Aggregation aggregation, String mgMessage,
    Class<String> class1);

  public List<Object> distinct(Query query, String entityCollection, String field);

}
