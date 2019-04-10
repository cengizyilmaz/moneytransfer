package com.moneytransfer.api.dao;

import com.moneytransfer.api.util.DAOOperationTypes;
import com.moneytransfer.api.util.ExecutorState;

/**
 * @author Cengiz YILMAZ 
 * This Singleton class is the manager class for the
 *  DAOManager
 * 
 *  Initial Executors are set in the Manager first. Executors can either
 *   set as Single or Parallel After Executors are set, the execute method
 *  is running and execute the related executors.
 *
 */
public class DAOManager {
	protected static DAOManager instance = null;
	private DAOExecutor executor;

	/*
	 * Getter for the next Executor
	 * 
	 * Return next executor for the next execution
	 */
	public DAOExecutor getExecutor() {
		return executor;
	}

	/*
	 * Getter for the next Executor
	 * 
	 * Return next executor for the next execution
	 */
	public void setExecutor(DAOExecutor _executor) {
		SingleExecutor single = new SingleExecutor();
		single.setSubExecutor(_executor);
		_executor.setState(ExecutorState.START);
		this.executor = single;
		this.executor.setState(ExecutorState.START);
	}

	/*
	 * Getter for the next Executor
	 * 
	 * Return next executor for the next execution
	 */
	public static DAOManager Instance() {
		if (instance == null) {
			instance = new DAOManager();
		}
		return instance;
	}

	/*
	 * Getter for the next Executor
	 * 
	 * Return next executor for the next execution
	 */
	public void execute(DAOOperationTypes operations, DAOExecutor executor) {
		if (executor != null) {
			do {
				if (executor.getState() == ExecutorState.START) {
					executor.start();
					executor.execute(operations);
					executor = executor.end();
				}
			} while (executor != null);

		}
	}

	/*
	 * ! \brief Getter for the next Executor
	 * 
	 * Return next executor for the next execution
	 */
	public void execute() {
		if (executor != null) {
			do {
				if (executor != null && executor.getState() == ExecutorState.START) {

					if (executor != null)
						executor.start();
					if (executor != null)
						executor.execute();
					if (executor != null)
						executor = executor.end();
				}
			} while (executor != null);

		}
	}

}
