package com.wvaviator.IngotEconomy.Account;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.wvaviator.IngotEconomy.IngotConfiguration;
import com.wvaviator.IngotEconomy.Database.AccountsResult;
import com.wvaviator.IngotEconomy.Database.Data;

import net.minecraft.entity.player.EntityPlayer;

public class AccountManager {

	private static DecimalFormat df = new DecimalFormat("#,###.00");

	public static void createNewAccount(EntityPlayer player) {
		String update = "INSERT INTO accounts VALUES ('" + player.getUniqueID().toString() + "', " + IngotConfiguration.startingBalance + ")";
		Data.sendUpdate(update);
	}

	public static String formatAmount(double amount) {
		return df.format(amount);
	}

	public static List<Account> getTopAccounts(int page) {
		
		String query = "SELECT * FROM accounts ORDER BY balance DESC";
		List<AccountsResult> topAccounts = Data.getAccountsQuery(query);
		List<Account> accounts = new ArrayList<Account>();
		
		for(int i = ((page-1)*10); i < (page*10); i++) {
			if (topAccounts.size() == i) break;
			Account acct = new Account(topAccounts.get(i).getUUID());
			accounts.add(acct);
		}
		
		return accounts;
		
	}
	
}
