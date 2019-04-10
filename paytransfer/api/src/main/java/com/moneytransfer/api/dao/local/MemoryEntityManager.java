package com.moneytransfer.api.dao.local;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.moneytransfer.api.dao.model.ApiModel;

/**
 * @author C00720849
 *
 * @param <S>
 * @param <R>
 * @param <T>
 */
public class MemoryEntityManager<S, R, T> {

	private Map<S, Map<R, T>> store = new ConcurrentHashMap<S, Map<R, T>>();

	public void persist(S clazz, R id, T entity) {
		Map<R, T> clazzEntity = null;
		if (store.containsKey(clazz)) {
			clazzEntity = store.get(clazz);
		} else {
			clazzEntity = new ConcurrentHashMap<R, T>();
			store.put(clazz, clazzEntity);
		}
		if (!clazzEntity.containsKey(id)) {
			clazzEntity.put(id, entity);
		}

	}

	public T find(S clazz, R id) {
		Map<R, T> clazzEntity = null;
		if (store.containsKey(clazz)) {
			clazzEntity = store.get(clazz);
		}
		if (null != clazzEntity && clazzEntity.containsKey(id)) {
			return clazzEntity.get(id);
		}
		return null;

	}

	public Collection<T> find(S clazz) {
		Map<R, T> clazzEntity = null;
		if (store.containsKey(clazz)) {
			clazzEntity = store.get(clazz);
		}
		if (null != clazzEntity) {
			return clazzEntity.values();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public T delete(T entity) {
		Class<T> clazz = (Class<T>) entity.getClass();
		Map<R, T> clazzEntity = null;
		if (store.containsKey(clazz.getCanonicalName())) {
			clazzEntity = store.get(clazz.getCanonicalName());
		}
		if (null != clazzEntity && clazzEntity.containsKey(((ApiModel) entity).getId())) {
			clazzEntity.remove(((ApiModel) entity).getId());
			if (clazzEntity.size() == 0) {
				store.remove(clazzEntity);
			
			}
			return entity;
		}
		return null;
	}

}
