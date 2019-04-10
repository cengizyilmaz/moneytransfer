package com.moneytransfer.api.dao.local;

import com.moneytransfer.api.dao.model.ApiModel;

/* This class is the abstract class for the DAO based operations.
 *
 * Every DAO Executors has state and every state there is a specific method execution done.
 * DAO Executors can have next Executor after the Execution finishes.
 *   
 */
public abstract class LocalDAOTemplate<T extends ApiModel, ID extends Number> {

	protected Class<T> type = null;

	public abstract void execute(LocalDAOExecutor<T, ID> executor) throws Exception;
	
	public Class<T> getType() {
		return type;
	}

	public void setType(Class<T> classType) {
		type=classType;
	}
	
}
