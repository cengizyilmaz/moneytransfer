package com.moneytransfer.api.dao.model;

public abstract class ApiModel {
	protected Number id;

	@SuppressWarnings("unchecked")
	public <ID extends Number> ID getId() {
		return (ID) id;
	}

	public void setId(Number id) {
		this.id = id;
	}
	
}
