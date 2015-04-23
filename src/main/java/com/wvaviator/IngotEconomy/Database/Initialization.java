package com.wvaviator.IngotEconomy.Database;

import java.sql.Connection;
import java.sql.Statement;

public class Initialization {
	
	public static void analyzeAndCreate() {
		if (!(Data.tableExists("players"))) initializePlayersTable();
		if (!(Data.tableExists("accounts"))) initializeAccountsTable();
	}
	
	private static void initializePlayersTable() {
		String update = "CREATE TABLE players (uuid VARCHAR(40), name VARCHAR(20), PRIMARY KEY (uuid))";
		Data.sendUpdate(update);
	}
	
	private static void initializeAccountsTable() {
		String update = "CREATE TABLE accounts (uuid VARCHAR(40), balance DECIMAL NOT NULL, PRIMARY KEY (uuid))";
		Data.sendUpdate(update);
	}

}
