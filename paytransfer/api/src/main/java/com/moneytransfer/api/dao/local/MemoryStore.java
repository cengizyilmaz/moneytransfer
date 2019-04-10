package com.moneytransfer.api.dao.local;

/**
 * @author Cengiz YILMAZ
 *
 * @param <S>
 * @param <R>
 * @param <T>
 */
public class MemoryStore<S,R,T> {
	private MemoryEntityManager<S, R, T> storeEntity=null;
	private static MemoryStore<?,?,?> instance=null;
	private MemoryStore (){
		storeEntity=new MemoryEntityManager<S, R, T>();
	}
	@SuppressWarnings("unchecked")
	public static <P,L,Z> MemoryStore<P,L,Z> getInstance() {
		if(null==instance) {
			instance=new MemoryStore<P,L,Z>();
		}
		return  (MemoryStore<P, L, Z>) instance;
	}
	public  void setEntity(MemoryEntityManager<S, R, T> entity) {
		storeEntity=entity;
	}
	
	public MemoryEntityManager<S, R, T> getEntity(){
		return (MemoryEntityManager<S, R, T>) storeEntity;
	}
}
