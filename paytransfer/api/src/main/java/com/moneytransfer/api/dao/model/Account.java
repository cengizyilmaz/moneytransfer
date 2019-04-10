package com.moneytransfer.api.dao.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Cengiz YILMAZ
 * Account POJO which handle the account information for the customer
 */

public class Account extends ApiModel {

	private String name;

	private Integer number;

	private BigDecimal balance;
	private String currency;
	public Account() {
		
	}
	public Account(String name,Integer number,BigDecimal balance,String currency) {
		this.name=name;
		this.number=number;
		this.balance=balance;
		this.currency=currency;
	}
	
	@JsonIgnore
	private Customer customer;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setCustomer(Customer assignCustomer) {

		customer = assignCustomer;
		customer.addAccount(this);
	}

}
