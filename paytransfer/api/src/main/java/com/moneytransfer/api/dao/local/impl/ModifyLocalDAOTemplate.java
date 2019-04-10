package com.moneytransfer.api.dao.local.impl;

import java.lang.reflect.Method;

import com.moneytransfer.api.dao.local.LocalDAOExecutor;
import com.moneytransfer.api.dao.local.LocalDAOTemplate;
import com.moneytransfer.api.dao.model.ApiModel;

/**
 * @author Cengiz YILMAZ
 *
 * @param <T>
 * @param <ID>
 */
public class ModifyLocalDAOTemplate<T extends ApiModel, ID extends Number> extends LocalDAOTemplate<T, ID> {

	@SuppressWarnings("unchecked")
	@Override
	public void execute(LocalDAOExecutor<T, ID> executor) throws Exception {
		T entity = (T) executor.getEntity();
		Class<T> clazz = (Class<T>) entity.getClass();
		T result = executor.getRepository().findOne((ID) entity.getId(), clazz);
		if (null != result) {
			for (Method method : clazz.getDeclaredMethods()) {
				if (method.getName().startsWith("set")) {
					String getterMethod = "g" + method.getName().substring(1, method.getName().length());
					try {
						Object paramValue = clazz.getDeclaredMethod(getterMethod) != null
								? clazz.getDeclaredMethod(getterMethod).invoke(entity)
								: null;
						if (null != paramValue) {
							method.invoke(result, paramValue);
						}
					} catch (NoSuchMethodError ex) {

					}
				}
			}
		}

	}

}
