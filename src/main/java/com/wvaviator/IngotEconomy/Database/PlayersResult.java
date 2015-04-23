package com.wvaviator.IngotEconomy.Database;

public class PlayersResult {
	
	private String uuid;
	private String name;
	
	public PlayersResult(String uuid, String name) {
		this.uuid = uuid;
		this.name = name;
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public String getName() {
		return this.name;
	}

}
