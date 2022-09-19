package com.digitalbook.service;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class MyQueryRepositoryImpl implements MyQueryRepositoryCustom {

	@PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> executeQuery(String query) {
        return entityManager.createNativeQuery(query).getResultList();
    }
}
