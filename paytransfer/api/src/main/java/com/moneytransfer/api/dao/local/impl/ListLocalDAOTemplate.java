package com.moneytransfer.api.dao.local.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.moneytransfer.api.dao.local.LocalDAOExecutor;
import com.moneytransfer.api.dao.local.LocalDAOTemplate;
import com.moneytransfer.api.dao.model.ApiModel;


/**
 * @author Cengiz YILMAZ
 * List template to gather the information for specific class
 * @param <T>
 * @param <ID>
 */
public class ListLocalDAOTemplate<T extends ApiModel, ID extends Number> extends LocalDAOTemplate<T, ID> {

	
	public void execute(LocalDAOExecutor<T, ID> executor) throws Exception {

		Collection<T> result = executor.getRepository().find(getType());
		List<T> resultList = new ArrayList<>();
		resultList.addAll(result);
		executor.getResultConsumer().accept(resultList);
	}

	
}
