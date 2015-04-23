package com.wvaviator.IngotEconomy.Database;

import java.math.BigDecimal;

public class AccountsResult {
	
	private double account;
	private String uuid;
	
	public AccountsResult(String uuid, double balance) {
		this.account = balance;
		this.uuid = uuid;
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public double getBalance() {
		return this.account;
	}

}
