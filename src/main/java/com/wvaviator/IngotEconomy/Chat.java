package com.wvaviator.IngotEconomy;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Chat {
	
	private static EnumChatFormatting gr = EnumChatFormatting.GREEN;
	private static EnumChatFormatting rd = EnumChatFormatting.RED;
	
	public static String noConsole = "This command must be performed by a player!";
	public static String playerNotFound = "Player not found!";
	public static String notEnough = "You don't have enough!";
	
	/**Adds color to chat messages by inserting color codes before each word.
	 * @param message The message to be colored
	 * @return The same message but with color codes
	 */
	public static String color(String message) {
		String[] words = message.split("\\s+");
		String output = "";
		for (String cycle : words) {
			
			if (cycle.charAt(0) == '/' || cycle.charAt(0) == '<') {
				output += rd;
			} else {
				output += gr;
			}			
			output += cycle;	
			output += " ";
		}
		return output;
	}
	
	/**Sends an uncolored message to a command sender. Use this when sending messages to
	 * the console to avoid large amounts of color codes in the terminal.
	 * @param sender Console sender
	 * @param message Message text to be displayed
	 */
	public static void sendChat(ICommandSender sender, String message) {
		if (sender instanceof EntityPlayer) {
			message = color(message);
		}
		ChatComponentText cct = new ChatComponentText(message);
		sender.addChatMessage(cct);
	}
	
	/**Sends a colored chat message to a player.
	 * @param player Player to message
	 * @param message Message to send
	 */
	public static void sendChat(EntityPlayer player, String message) {
		ChatComponentText cct = new ChatComponentText(color(message));
		player.addChatMessage(cct);
	}

	/**Sends a chat message to a specific player by name if they are online
	 * @param name The name of the player that should receive the message
	 * @param message The unformatted message to be sent
	 */
	public static void sendToPlayer(String name, String message) {	
		List<EntityPlayer> playerList = (List<EntityPlayer>) MinecraftServer.getServer().getConfigurationManager().playerEntityList;
		for ( EntityPlayer onlinePlayer : playerList ) {
			if (onlinePlayer.getName().equalsIgnoreCase(name)) {
				sendChat(onlinePlayer, message);
			}
		}
		
	}

}
