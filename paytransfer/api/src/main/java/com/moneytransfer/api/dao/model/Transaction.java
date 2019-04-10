package com.moneytransfer.api.dao.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction extends ApiModel {

	private Integer type;

	private BigDecimal amount;
	private Account from;
	private Account to;
	private LocalDateTime date;
	
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Account getFrom() {
		return from;
	}

	public void setFrom(Account from) {
		this.from = from;
	}

	public Account getTo() {
		return to;
	}

	public void setTo(Account to) {
		this.to = to;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

}
