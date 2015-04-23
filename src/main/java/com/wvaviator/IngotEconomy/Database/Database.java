package com.wvaviator.IngotEconomy.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;

import com.wvaviator.IngotEconomy.IngotEconomy;

public class Database {
	
	static Logger logger = IngotEconomy.logger;
	static String DRIVER = "sqlite-jdbc-3.8.7.jar";
	static String DATABASE = "IngotAccounts.db";
	static File directory = IngotEconomy.ingotDirectory;
	static File driverFile = new File(directory.getPath() + "/" + DRIVER);
	static File databaseFile = new File(directory.getPath() + "/" + DATABASE);
	static Connection c;
	
	public static void createDatabase() {
		
		if (!(driverFile.exists())) {
			try {
				copyDriverFromJar();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				logger.error("Something went wrong while attempting to locate driver file");
			}
		}
		
		getConnection();
		closeConnection();
		
	}
	
	public static Connection getConnection() {
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getPath());
			
		} catch (Exception e) {
			logger.error("Exception while attempting to connect to the database!");
			e.printStackTrace();
		}
		
		return c;	
		
	}
	
	public static void closeConnection() {
		
		try {
			
			if (c.isClosed()) return;
			c.close();
			
		} catch (SQLException e) {
			logger.error("Error while attempting to close database connection!");
			e.printStackTrace();
		}
		
	}
	
	private static void copyDriverFromJar() throws FileNotFoundException {
		
		logger.info("Database driver not located in mod folder. Creating a new one...");
		
		FileOutputStream fos = new FileOutputStream(driverFile);
		InputStream is = Database.class.getResourceAsStream("/" + DRIVER);
		byte[] buffer = new byte[4096];
		int read = 0;
		
		try {
			
			read = is.read(buffer);
			while (read != -1) {
				fos.write(buffer, 0 ,read);
				read = is.read(buffer);
			}	
			fos.close();
			is.close();
			
		} catch (IOException e) {
			logger.error("Could not extract database driver!");
			e.printStackTrace();
		}
		
	}

}
