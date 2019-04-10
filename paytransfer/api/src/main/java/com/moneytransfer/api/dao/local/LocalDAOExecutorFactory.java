package com.moneytransfer.api.dao.local;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.moneytransfer.api.dao.local.impl.DeleteLocalDAOTemplate;
import com.moneytransfer.api.dao.local.impl.GetLocalDAOTemplate;
import com.moneytransfer.api.dao.local.impl.ListLocalDAOTemplate;
import com.moneytransfer.api.dao.local.impl.ModifyLocalDAOTemplate;
import com.moneytransfer.api.dao.local.impl.SaveLocalDAOTemplate;
import com.moneytransfer.api.dao.model.Account;
import com.moneytransfer.api.dao.model.ApiModel;
import com.moneytransfer.api.dao.model.Customer;
import com.moneytransfer.api.dao.model.Transaction;
import com.moneytransfer.api.util.DAOOperationTypes;

/**
 * @author Cengiz YILMAZ
 * This class generate the LocalDAOExecutor object according to DAO Operation type
 */
public class LocalDAOExecutorFactory {
	private static LocalDAOExecutorFactory factory = null;
	private Map<Class<? extends ApiModel>, Map<DAOOperationTypes, Class<? extends Number>>> clazzHolder = null;
	@SuppressWarnings("rawtypes")
	private Map<DAOOperationTypes, Class<? extends LocalDAOTemplate>> templateMap = null;

	private LocalDAOExecutorFactory() {
		clazzHolder = new ConcurrentHashMap<Class<? extends ApiModel>, Map<DAOOperationTypes, Class<? extends Number>>>();
		Map<DAOOperationTypes, Class<? extends Number>> mapExecutor = new HashMap<DAOOperationTypes, Class<? extends Number>>();
		mapExecutor.put(DAOOperationTypes.SAVE, Integer.class);
		mapExecutor.put(DAOOperationTypes.FIND, Integer.class);
		mapExecutor.put(DAOOperationTypes.LIST, Integer.class);
		mapExecutor.put(DAOOperationTypes.GET, Integer.class);
		mapExecutor.put(DAOOperationTypes.DELETE, Integer.class);
		mapExecutor.put(DAOOperationTypes.MODIFY, Integer.class);
		clazzHolder.put(Customer.class, mapExecutor);
		clazzHolder.put(Account.class, mapExecutor);
		clazzHolder.put(Transaction.class, mapExecutor);
		templateMap=new HashMap<>();
		templateMap.put(DAOOperationTypes.SAVE,
		 SaveLocalDAOTemplate.class);
		templateMap.put(DAOOperationTypes.LIST,
				 ListLocalDAOTemplate.class);
		templateMap.put(DAOOperationTypes.GET,
				 GetLocalDAOTemplate.class);
		templateMap.put(DAOOperationTypes.FIND,
				 ListLocalDAOTemplate.class);
		templateMap.put(DAOOperationTypes.DELETE,
				 DeleteLocalDAOTemplate.class);
		templateMap.put(DAOOperationTypes.MODIFY,
				 ModifyLocalDAOTemplate.class);

	}

	public static LocalDAOExecutorFactory getInstance() {
		if (null == factory) {
			factory = new LocalDAOExecutorFactory();
		}
		return factory;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends ApiModel, R extends Number> LocalDAOExecutor<T, R> buildExecutor(Class<T> clazzT,
			DAOOperationTypes operationType, T entity) {
		if (clazzHolder.containsKey(clazzT)) {
			Map<DAOOperationTypes, Class<? extends Number>> mapExecutor = clazzHolder.get(clazzT);
			if (mapExecutor.containsKey(operationType)) {
				LocalDAOExecutor<T, R> executor = new LocalDAOExecutor<T, R>();
				executor.setEntity(entity);
				if (templateMap.containsKey(operationType)) {
					Class<? extends LocalDAOTemplate> daoMap = templateMap.get(operationType);
					LocalDAOTemplate<T, R> template = null;
					try {
						template = (LocalDAOTemplate<T, R>) daoMap.getDeclaredConstructor().newInstance();
						template.setType(clazzT);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException | SecurityException e) {

					}
					if (null != template) {
						executor.addDAOTemplate(operationType, template);
					}
				}
				return executor;
			}

		}
		return null;
	}
}
