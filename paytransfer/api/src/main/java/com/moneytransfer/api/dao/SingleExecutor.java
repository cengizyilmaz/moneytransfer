package com.moneytransfer.api.dao;

import com.moneytransfer.api.util.DAOOperationTypes;
import com.moneytransfer.api.util.ExecutorState;

/* This class is the abstract class for the DAO based operations.
 *
 * Every DAO Executors has state and every state there is a specific method execution done.
 * DAO Executors can have next Executor after the Execution finishes.
 *   
 */
public class SingleExecutor extends DAOExecutor {

	protected DAOExecutor subExecutor = null;

	public DAOExecutor getSubExecutor() {
		return subExecutor;
	}

	public void setSubExecutor(DAOExecutor subExecutor) {
		this.subExecutor = subExecutor;
	}

	@Override
	public DAOExecutor start() {

		return null;
	}

	@Override
	public DAOExecutor end() {

		return nextExecutor;
	}

	public ExecutorState executeState(ExecutorState state, DAOExecutor executor) {
		switch (state) {
		case START:
			executor.start();
			break;
		case EXECUTE:
			executor.execute();
			break;
		case END:
			executor.end();
			break;
		case ERROR:

		default:
			executor.error();
			break;

		}
		return executor.getState();
	}

	public DAOExecutor execute(ExecutorState state, DAOExecutor executor) {
		do {
			state = executeState(state, executor);
		} while (state != ExecutorState.COMPLETE && state != ExecutorState.WAIT_TRANSACTION);

		return executor.getNextExecutor();
	}

	public ExecutorState executeState(ExecutorState state, DAOExecutor executor, DAOOperationTypes daoOperType) {
		switch (state) {
		case START:
			executor.start();
			break;
		case EXECUTE:
			executor.execute(daoOperType);
			break;
		case END:
			executor.end();
			break;
		case ERROR:

		default:
			executor.error();
			break;

		}
		return executor.getState();
	}

	public DAOExecutor execute(ExecutorState state, DAOExecutor executor, DAOOperationTypes daoOperType) {
		do {
			state = executeState(state, executor, daoOperType);
		} while (state != ExecutorState.COMPLETE && state != ExecutorState.WAIT_TRANSACTION);

		return executor.getNextExecutor();
	}

	@Override
	public DAOExecutor execute() {

		do {
			subExecutor = execute(subExecutor.getState(), subExecutor);
		} while (subExecutor != null);
		return null;
	}

	@Override
	public DAOExecutor execute(DAOOperationTypes daoOperType) {

		do {
			subExecutor = execute(subExecutor.getState(), subExecutor, daoOperType);
		} while (subExecutor != null);
		return null;
	}

	@Override
	public DAOExecutor error() {

		return null;
	}

}
