package com.wvaviator.IngotEconomy.Database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import com.wvaviator.IngotEconomy.IngotEconomy;

public class Data {
	
	static Logger logger = IngotEconomy.logger;
	
	public static void sendUpdate(String update) {
		
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
			
			stmt = c.createStatement();
			stmt.executeUpdate(update);
			
		} catch (SQLException e) {
			
			logger.error("Error in executing database update: " + update);
			e.printStackTrace();
			
		} finally {
			
			try {
				stmt.close();
				c.close();
			} catch (SQLException e) {
				logger.error("Error closing statement and database");
				e.printStackTrace();
			}
		}
	}
	
	public static List<PlayersResult> getPlayersQuery(String query) {
		
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			List<PlayersResult> results = new ArrayList<PlayersResult>();
			
			while(rs.next()) {
				PlayersResult pr = new PlayersResult(rs.getString("uuid"), rs.getString("name"));
				results.add(pr);
			}
			
			return results;
			
		} catch (SQLException e) {
			logger.error("Error retrieving database query: " + query);
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				c.close();
			} catch (SQLException e) {
				logger.error("Error trying to close connection");
				e.printStackTrace();
			}
		}
		
		return null;
		
	}
	
	public static List<AccountsResult> getAccountsQuery(String query) {
		
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			List<AccountsResult> results = new ArrayList<AccountsResult>();
			
			while(rs.next()) {
				AccountsResult ar = new AccountsResult(rs.getString("uuid"), rs.getDouble("balance"));
				results.add(ar);
			}
			
			return results;
			
		} catch (SQLException e) {
			logger.error("Error retrieving database query: " + query);
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				c.close();
			} catch (SQLException e) {
				logger.error("Error trying to close connection");
				e.printStackTrace();
			}
		}
		
		return null;
		
	}
	
	public static boolean tableExists(String table) {
		
		Connection c = Database.getConnection();
		DatabaseMetaData dbm = null;
		
		try {
			dbm = c.getMetaData();
			ResultSet rs = dbm.getTables(null, null, table.toUpperCase(), new String[] {"TABLE"});
			
			if (!rs.next()) {
				return false;
			}
			
			return true;
			
		} catch (SQLException e) {
			logger.error("Error when determining if " + table + " table exists!");
			e.printStackTrace();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				logger.error("Error when closing database connection");
				e.printStackTrace();
			}
		}
		return false;
	}
}
