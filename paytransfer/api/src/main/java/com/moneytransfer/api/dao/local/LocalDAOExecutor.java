package com.moneytransfer.api.dao.local;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.moneytransfer.api.dao.DAOExecutor;
import com.moneytransfer.api.dao.model.ApiModel;
import com.moneytransfer.api.dao.repository.AbstractRepository;
import com.moneytransfer.api.util.DAOOperationTypes;
import com.moneytransfer.api.util.ExecutorState;

/* This class is the abstract class for the DAO based operations.
 *
 * Every DAO Executors has state and every state there is a specific method execution done.
 * DAO Executors can have next Executor after the Execution finishes.
 *   
 */
public class LocalDAOExecutor<T extends ApiModel, ID extends Number> extends DAOExecutor {

	private AbstractRepository<T, ID> repository = null;
	private Map<DAOOperationTypes, LocalDAOTemplate<T, ID>> daoTemplates = new HashMap<DAOOperationTypes, LocalDAOTemplate<T, ID>>();
	private T entity;
	private ID id;
	private Consumer<List<T>> resultConsumer = null;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public AbstractRepository<T, ID> getRepository() {
		return repository;
	}

	public void setRepository(AbstractRepository<T, ID> repository) {
		this.repository = repository;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public LocalDAOTemplate<T, ID> getDAOTemplate(DAOOperationTypes operationTypes) {
		if (daoTemplates.containsKey(operationTypes)) {
			return daoTemplates.get(operationTypes);
		}
		return null;

	}

	public void addDAOTemplate(DAOOperationTypes operationTypes, LocalDAOTemplate<T, ID> template) {
		if (!daoTemplates.containsKey(operationTypes)) {
			daoTemplates.put(operationTypes, template);
		}
	}

	@Override
	public DAOExecutor start() {

		MemoryEntityManager<String, ID, T> entityManager = new MemoryEntityManager<String, ID, T>();
		repository = new AbstractRepository<T, ID>();
		repository.setEntityManager(entityManager);

		setState(ExecutorState.EXECUTE);
		return null;
	}

	@Override
	public DAOExecutor execute(DAOOperationTypes operations) {

		LocalDAOTemplate<T, ID> template = getDAOTemplate(operations);
		if (template != null && repository != null) {
			try {
				template.execute(this);
				setState(ExecutorState.END);
			} catch (Exception ex1) {
				setState(ExecutorState.ERROR);
				return null;
			}

		}

		setState(ExecutorState.END);

		

		return null;
	}

	@Override
	public DAOExecutor execute() {

		for (DAOOperationTypes operationType : getOperationType()) {
			execute(operationType);
		}

		setState(ExecutorState.END);
		return null;
	}

	@Override
	public DAOExecutor end() {

		
		setState(ExecutorState.COMPLETE);
		return nextExecutor;

	}

	@Override
	public DAOExecutor error() {

	
		return null;
	}



	public Consumer<List<T>> getResultConsumer() {
		return resultConsumer;
	}

	public void setResultConsumer(Consumer<List<T>> resultConsumer) {
		this.resultConsumer = resultConsumer;
	}

}
