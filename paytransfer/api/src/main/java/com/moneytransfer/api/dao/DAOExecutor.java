package com.moneytransfer.api.dao;


import java.util.Queue;

import com.moneytransfer.api.util.DAOOperationTypes;
import com.moneytransfer.api.util.ExecutionResult;
import com.moneytransfer.api.util.ExecutorState;


/**
* @author Cengiz YILMAZ
* This class is the abstract class for the DAO based operations.
*
* Every DAO Executors has state and every state there is a specific method execution done.
* DAO Executors can have next Executor after the Execution finishes.
*  
*/
public abstract class DAOExecutor {
	
	protected DAOExecutor nextExecutor;
	protected Queue<DAOOperationTypes> operationType;
	protected ExecutorState state=ExecutorState.START;
	protected ExecutionResult executionResult=ExecutionResult.SUCCESS;

	

	public ExecutionResult getExecutionResult() {
		return executionResult;
	}

	public void setExecutionResult(ExecutionResult executionResult) {
		this.executionResult = executionResult;
	}

	/*
	 * Getter for the next Executor
	 * 
	 * Return next executor for the next execution
	 */
	public DAOExecutor getNextExecutor() {
		return nextExecutor;
	}
	
	/*
	 * Setter for the next Executor
	 * 
	 * Set next executor for the next execution
	 */
	public void setNextExecutor(DAOExecutor nextExecutor) {
		this.nextExecutor = nextExecutor;
	}
	
	/*
	 * Getter for the State
	 * 
	 * Return state of the execution
	 */
	public ExecutorState getState() {
		return state;
	}
	
	/*
	 * Setter for the state
	 * 
	 * Set state of the execution
	 */
	public void setState(ExecutorState state) {
		this.state = state;
	}
	
	
	/*
	 *  Getter for the Operation Type queue
	 * 
	 * Return queue of the operation types for the executor
	 */
	public Queue<DAOOperationTypes> getOperationType() {
		return operationType;
	}
	
	/*
	 * Setter for the Operation Type queue
	 * 
	 * Set queue of the operation types for the executor
	 */
	public void setOperationType(Queue<DAOOperationTypes> operationType) {
		this.operationType = operationType;
	}
	
	/*
	 *  Peek an Operation  from the Operation Type queue
	 * 
	 * Peek an Operation from  queue of the operation types for the executor
	 */
	public DAOOperationTypes peekOperationType()
	{
		return operationType.peek();
	}
	
	/*
	 * Add an Operation  from the Operation Type queue
	 * 
	 * Add an Operation from  queue of the operation types for the executor
	 */
	public void pullOperationType(DAOOperationTypes daoOperType)
	{
		
		operationType.add(daoOperType);
	}
	
	/*
	 * Abstract method for  Start state method.
	 * 
	 * If the state is START, this method will be executed
	 */
	public abstract DAOExecutor start();
	
	/*
	 *  Abstract method for  Execute state method from the operation type queue.
	 * 
	 * If the state is EXECUTE, this method will be executed
	 */
	public abstract DAOExecutor execute();
	
	/*
	 * Abstract method for  Execute state method for the specific operation type.
	 * 
	 * If the state is EXECUTE, this method will be executed
	 */
	public abstract DAOExecutor execute(DAOOperationTypes daoOperType);
	
	/*
	 * Abstract method for  End state method.
	 * 
	 * If the state is END, this method will be executed
	 */
	public abstract DAOExecutor end();
	
	/*
	 *  Abstract method for  Error state method.
	 * 
	 * If the state is ERROR, this method will be executed
	 */
	public abstract DAOExecutor error();
	
}
	


