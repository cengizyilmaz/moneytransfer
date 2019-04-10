package com.moneytransfer.api.dao.local.impl;

import java.util.ArrayList;
import java.util.List;

import com.moneytransfer.api.dao.local.LocalDAOExecutor;
import com.moneytransfer.api.dao.local.LocalDAOTemplate;
import com.moneytransfer.api.dao.model.ApiModel;

/**
 * @author Cengiz YILMAZ
 * Get template to gather the information for specific object
 * @param <T>
 * @param <ID>
 */
public class GetLocalDAOTemplate<T extends ApiModel, ID extends Number> extends LocalDAOTemplate<T, ID> {

	@SuppressWarnings("unchecked")
	@Override
	public void execute(LocalDAOExecutor<T, ID> executor) throws Exception {
		T entity = (T) executor.getEntity();
		Class<T> clazz = (Class<T>) entity.getClass();
		T result = executor.getRepository().findOne((ID) entity.getId(), clazz);
		List<T> resultList = new ArrayList<>();
		if (null != result) {
			resultList.add(result);
		}
		executor.getResultConsumer().accept(resultList);

	}
}
