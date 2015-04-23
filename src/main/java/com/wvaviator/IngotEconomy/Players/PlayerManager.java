package com.wvaviator.IngotEconomy.Players;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import com.wvaviator.IngotEconomy.Database.Data;
import com.wvaviator.IngotEconomy.Database.PlayersResult;

public class PlayerManager {
	
	/**
	 * Returns whether or not the player is currently stored in the Player database.
	 * The player database records every player who, at some point, has entered the world.
	 * @param uuid The string UUID of the player in question
	 * @return TRUE if the player has joined before, FALSE if the player with the provided UUID
	 * has never joined.
	 */
	public static boolean hasJoinedBefore(String uuid) {
		String query = "SELECT * FROM players WHERE uuid = '" + uuid + "'";
		List<PlayersResult> results = Data.getPlayersQuery(query);
		if (results.isEmpty()) return false;
		return true;
	}
	
	/**
	 * Locates the player within the database and compares their current username to
	 * the one stored within the database under their UUID, in order to detect if the player 
	 * has changed their username since last joining. This method does not check if the player
	 * is already in the database.
	 * @param uuid The string UUID of the player in question
	 * @param name The current name of the player
	 * @return TRUE if the name stored in the database for this player differs from the
	 * provided name, FALSE if the stored name is the same as the current name.
	 */
	public static boolean hasNameChanged(String uuid, String name) {
		String query = "SELECT * FROM players WHERE uuid = '" + uuid + "'";
		List<PlayersResult> results = Data.getPlayersQuery(query);
		String savedName = results.get(0).getName();
		if (savedName.equalsIgnoreCase(name)) return false;
		return true;
	}
	
	/**This method will update the name for the player with the provided UUID in the database.
	 * This is useful for correcting names that have been changed in the Player database.
	 * @param uuid The string UUID of the player whose name should be updated
	 * @param name The new name of the player
	 */
	public static void updateName(String uuid, String name) {
		String update = "UPDATE players SET name = '" + name + "' WHERE uuid = '" + uuid + "'";
		Data.sendUpdate(update);
	}
	
	/**Add a player into the Players database. This method does not check if the player
	 * is already in the database and will throw an error if a duplicate UUID is entered.
	 * @param uuid The string UUID of the player to add
	 * @param name The username of the player to add
	 */
	public static void addPlayer(String uuid, String name) {
		String update = "INSERT INTO players VALUES ('" + uuid + "', '" + name + "')";
		Data.sendUpdate(update);
	}
	
	/**
	 * @param name The player's username
	 * @return The string UUID of the player, NULL if the player doesn't exist or has never
	 * joined before.
	 */
	public static String getUUID(String name) {
		String query = "SELECT * FROM players WHERE UPPER(name) like UPPER('" + name + "')";
		List<PlayersResult> results = Data.getPlayersQuery(query);
		if (results.isEmpty()) return null;
		String uuid = results.get(0).getUUID();
		return uuid;
	}
	
	/**
	 * @param uuid The string UUID of the player
	 * @return The username of the player, or NULL if the player doesn't exist or has never
	 * joined before.
	 */
	public static String getName(String uuid) {
		String query = "SELECT * FROM players WHERE uuid = '" + uuid + "'";
		List<PlayersResult> results = Data.getPlayersQuery(query);
		if (results.isEmpty()) return null;
		String name = results.get(0).getName();
		return name;
	}
	
	/**
	 * @param uuid The string UUID of the player
	 * @return The EntityPlayer object with the given name, if they are online
	 */
	public static EntityPlayer getOnlineFromUUID(String uuid) {
		String name = getName(uuid);
		if (name == null) return null;
		
		EntityPlayer player = null;
		try {
			player = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(name);
		} catch (Exception e) {
			
		}
		return player;
	}
	
	/**
	 * @param name The players name
	 * @return The EntityPlayer object with the given name, if they are online
	 */
	public static EntityPlayer getOnlineFromName(String name) {
		EntityPlayer player = null;
		try {
			player = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(name);
		} catch (Exception e) {
		}
		return player;
	}

}
