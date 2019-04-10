package com.moneytransfer.api.dao.local.impl;

import java.util.ArrayList;
import java.util.List;

import com.moneytransfer.api.dao.local.LocalDAOExecutor;
import com.moneytransfer.api.dao.local.LocalDAOTemplate;
import com.moneytransfer.api.dao.model.ApiModel;

/* 
 * Delete template to delete the information for specific object *   
 */
public class DeleteLocalDAOTemplate<T extends ApiModel,ID extends Number> extends LocalDAOTemplate<T,ID> {

	public void execute(LocalDAOExecutor<T,ID> executor) throws Exception
	{
		T entity=(T) executor.getEntity();
		T result=executor.getRepository().delete(entity);
		List<T> resultList = null;
		if(null!=result) {
			resultList=new ArrayList<>();
			resultList.add(result);
		}
		executor.getResultConsumer().accept(resultList);
	}
}
