package com.studioreina.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.studioreina.repository.custom.ServiceRepositoryCustom;


@Repository
@Transactional(readOnly = true)
public class ServiceRepositoryImpl implements ServiceRepositoryCustom {

	@PersistenceContext
    EntityManager entityManager;
	
	@Override
	public boolean isExist(String name) {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery("SELECT s FROM Service s WHERE s.name = :name")
				.setParameter("name", name)
				.setMaxResults(1);
		try {			
			query.getSingleResult();
			return true;
		} catch (NoResultException nre) {
			return false;
		}
		
	}
}
