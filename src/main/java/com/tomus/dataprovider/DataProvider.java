package com.tomus.dataprovider;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public abstract class DataProvider implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	protected transient EntityManager entityManager;

	protected void beginTransaction() {
		if (!entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().begin();
		}
	}

	protected void commitTransaction() {
		if (entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().commit();
		}
	}

	protected void rollbackTransaction() {
		if (entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().rollback();
		}
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
}