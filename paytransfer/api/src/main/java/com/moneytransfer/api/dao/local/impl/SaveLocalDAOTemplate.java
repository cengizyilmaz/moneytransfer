package com.moneytransfer.api.dao.local.impl;

import com.moneytransfer.api.dao.local.LocalDAOExecutor;
import com.moneytransfer.api.dao.local.LocalDAOTemplate;
import com.moneytransfer.api.dao.model.ApiModel;
import com.moneytransfer.api.util.IdGenerator;


/**
 * @author Cengiz YILMAZ
 *
 * @param <T>
 * @param <ID>
 */
public class SaveLocalDAOTemplate<T extends ApiModel,ID extends Number> extends LocalDAOTemplate<T,ID>{

	@Override
	public void execute(LocalDAOExecutor<T,ID> executor) throws Exception
	{
		T entity=(T) executor.getEntity();
		entity.setId(IdGenerator.getNextUniqueIndex());
		executor.getRepository().save(entity.getId(),entity);
		
	}

	
	
}
