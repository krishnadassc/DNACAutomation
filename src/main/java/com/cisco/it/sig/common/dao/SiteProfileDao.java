package com.cisco.it.sig.common.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.cisco.dnac.common.entity.SiteEntity;

public class SiteProfileDao extends CommonDaoImpl{
	private static final String SITE_PROFILE_COLLECTION = "SiteProfile";
	
	//public CommonDaoImpl commonDao= new CommonDaoImpl();
	public void saveSiteProfile(SiteEntity siteEntity) {
		super.save(siteEntity);
	}

	public SiteEntity findBySiteId(String siteId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("SiteId").is(siteId));
		return findByQuery(query, SiteEntity.class, SITE_PROFILE_COLLECTION);
	}

	public List<SiteEntity> getSiteHierarchyBySiteId(String parentSiteId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("parentId").is(parentSiteId));
		return findAll(query, SiteEntity.class, SITE_PROFILE_COLLECTION);
	}
}
