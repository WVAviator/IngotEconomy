package com.wvaviator.IngotEconomy.Account;

import java.text.DecimalFormat;
import java.util.List;

import com.wvaviator.IngotEconomy.IngotEconomy;
import com.wvaviator.IngotEconomy.API.Events.BalanceDecreaseEvent;
import com.wvaviator.IngotEconomy.API.Events.BalanceIncreaseEvent;
import com.wvaviator.IngotEconomy.API.Events.BalanceSetEvent;
import com.wvaviator.IngotEconomy.API.Events.BalanceTransferEvent;
import com.wvaviator.IngotEconomy.Database.AccountsResult;
import com.wvaviator.IngotEconomy.Database.Data;
import com.wvaviator.IngotEconomy.Players.PlayerManager;

import net.minecraft.entity.player.EntityPlayer;

public class Account {
	
	private double balance;
	private String uuid;
	private DecimalFormat df = new DecimalFormat("#,###.00");
	
	public Account(String uuid) {
		this.uuid = uuid;
		this.balance = retrieveBalance(uuid);
	}

	private double retrieveBalance(String uuid2) {
		String query = "SELECT * FROM accounts WHERE uuid = '" + uuid2 + "'";
		List<AccountsResult> result = Data.getAccountsQuery(query);
		double bal = result.get(0).getBalance();
		return bal;
	}
	
	private void save() {
		String update = "UPDATE accounts SET balance = " + this.balance + " WHERE uuid = '" + this.uuid + "'";
		Data.sendUpdate(update);
	}
	
	public void add(double amount) {
		if (IngotEconomy.EVENT_BUS.post(new BalanceIncreaseEvent(this.uuid, this.balance, amount))) return;
		this.balance += amount;
		save();
	}
	
	public void subtract(double amount) {
		if (IngotEconomy.EVENT_BUS.post(new BalanceDecreaseEvent(this.uuid, this.balance, amount))) return;
		this.balance -= amount;
		save();
	}
	
	public void set(double amount) {
		if (IngotEconomy.EVENT_BUS.post(new BalanceSetEvent(this.uuid, this.balance, amount))) return;
		this.balance = amount;
		save();
	}
	
	public boolean canAfford(double amount) {
		if (amount > this.balance) return false;
		return true;
	}
	
	public void transferToAccount(Account account, double amount) {
		if (amount > this.balance) return;
		if (IngotEconomy.EVENT_BUS.post(new BalanceTransferEvent(this.uuid, this.balance, amount, account.uuid, account.balance))) return;
		this.balance -= amount;
		account.balance += amount;
		save();
		account.save();
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public String getFormattedBalance() {
		return df.format(this.balance);
	}
	
	
	public String getName() {
		return PlayerManager.getName(this.uuid);
	}
	
	public String getUUID() {
		return this.uuid;
	}

}
