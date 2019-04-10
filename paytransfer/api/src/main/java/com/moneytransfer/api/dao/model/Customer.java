package com.moneytransfer.api.dao.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cengiz YILMAZ 
 * Customer POJO object which handles the customer
 *         information
 */

public class Customer extends ApiModel {

	private String name;

	private String surname;
	private String email;
	private String phonenumber;
	private String address;
	private List<Account> accounts;

	public Customer() {

	}

	public Customer(String name, String surname, String email, String phonenumber, String address) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phonenumber = phonenumber;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> account) {
		this.accounts = account;
	}

	public void addAccount(Account account) {
		if (null == accounts) {
			accounts = new ArrayList<Account>();
		}
		if (!accounts.contains(account)) {
			accounts.add(account);
		}

	}

}
