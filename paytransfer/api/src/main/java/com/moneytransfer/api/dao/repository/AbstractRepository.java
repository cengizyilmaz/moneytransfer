package com.moneytransfer.api.dao.repository;

import java.util.Collection;

import com.moneytransfer.api.dao.local.MemoryEntityManager;
import com.moneytransfer.api.dao.local.MemoryStore;
import com.moneytransfer.api.dao.model.ApiModel;

/* This class is the abstract Repository class for the Data access operations
 *
 * This class called according to the operation request for the data access.
 * Eclipselink ORM is loaded using the EntityManager and the operation is called from the EntityManager object.
 *  
 */
public class AbstractRepository<T, ID extends Number> {

	private MemoryEntityManager<String, ID, T> entityManager = null;

	public MemoryEntityManager<String, ID, T> getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(MemoryEntityManager<String, ID, T> entityManager) {
		this.entityManager = entityManager;
	}

	/*
	 * Returns the class definition name for the generic repository object.
	 * 
	 */
	protected Class<T> getTypeClass() {
		return null;
	}

	/*
	 * Calls to save the object from the DAO access as a generic structure.
	 * 
	 * Calls the Entity Manager persist method for the generic class of the
	 * Repository. The method is called for the generic class to persist the object
	 */
	public <S extends T> void save(ID id, S entity) throws IllegalArgumentException {
		try {

			MemoryStore.<String, ID, T>getInstance().getEntity().persist(entity.getClass().getCanonicalName(), id,
					entity);
		}

		catch (IllegalArgumentException ex2) {
			throw ex2;
		}

	}

	/*
	 * Calls to get the list of object from the DAO access as a generic structure.
	 * 
	 * Calls the Entity Manager find method for the generic class of the Repository.
	 * The method is called for the generic class access for the object and return
	 * one Generic object, e.g. UserContacts
	 */
	public T findOne(ID id, Class<T> clazz) {

		T result = null;
		try {

			result = (T) MemoryStore.<String, ID, T>getInstance().getEntity().find(clazz.getCanonicalName(), id);
		}

		catch (Exception ex2) {

		}

		return result;

	}

	public Collection<T> find(Class<T> clazz) {
		Collection<T> result = null;
		try {

			result = MemoryStore.<String, ID, T>getInstance().getEntity().find(clazz.getCanonicalName());
		}

		catch (Exception ex2) {

		}

		return result;
	}

	public T delete(T entity) throws IllegalArgumentException {
		if (entity instanceof ApiModel) {
			try {
				return MemoryStore.<String, ID, T>getInstance().getEntity().delete(entity);

			} catch (IllegalArgumentException ex2) {
				throw ex2;
			}
			
		}
		return null;

	}
}
