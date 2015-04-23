package com.wvaviator.IngotEconomy.API.Events;

import java.text.DecimalFormat;

import com.wvaviator.IngotEconomy.Players.PlayerManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**This event is fired when a player transfers money to another account.
 *
 */
@Cancelable
public class BalanceTransferEvent extends Event {
	
	private String uuid;
	private double oldBalance;
	private double newBalance;
	private double amount;
	private DecimalFormat df = new DecimalFormat("#,###.00");
	private String transferedTo;
	private double transferedToOldBalance;

	public BalanceTransferEvent(String uuid, double oldBalance, double amount, String transferedToUUID, double transferedToOldBalance) {
		
		this.uuid = uuid;
		this.oldBalance = oldBalance;
		this.amount = amount;
		this.newBalance = oldBalance + amount;
		this.transferedTo = transferedToUUID;
		this.transferedToOldBalance = transferedToOldBalance;
		
	}
	
	/**
	 * @return The UUID of the player who transfered the amount
	 */
	public String getUUID() {
		return this.uuid;
	}
	
	/**
	 * @return The name of the player who transfered the amount
	 */
	public String getPlayerName() {
		return PlayerManager.getName(uuid);
	}
	
	/**
	 * @return The player who transfered the amount
	 */
	public EntityPlayer getPlayer() {
		return PlayerManager.getOnlineFromName(getPlayerName());
	}
	
	/**
	 * @return The UUID of the player who received the transfer
	 */
	public String getReceiverUUID() {
		return this.transferedTo;
	}
	
	/**
	 * @return The name of the player who received the transfer
	 */
	public String getReceiverName() {
		return PlayerManager.getName(this.transferedTo);
	}
	
	/**
	 * @return The player who received the transfer, null if not online
	 */
	public EntityPlayer getReceiver() {
		return PlayerManager.getOnlineFromName(getPlayerName());
	}
	
	/**
	 * @return The balance of the player transferring before the transfer
	 */
	public double getOldBalance() {
		return this.oldBalance;
	}
	
	/**
	 * @return The new balance of the player transferring after the transfer
	 */
	public double getNewBalance() {
		return this.newBalance;
	}
	
	/**
	 * @return The amount transferred
	 */
	public double getAmountIncreased() {
		return this.amount;
	}
	
	/**
	 * @return The old balance of the receiver before receiving the transfer
	 */
	public double getReceiverOldBalance() {
		return this.transferedToOldBalance;
	}
	
	/**
	 * @return Formatted string of the old balance
	 */
	public String getFormattedOldBalance() {
		return df.format(this.oldBalance);
	}
	
	/**
	 * @return Formatted string of the receiver's old balance
	 */
	public String getFormattedReceiverOldBalance() {
		return df.format(this.transferedToOldBalance);
	}
	
	/**
	 * @return Formatted string of the new balance
	 */
	public String getFormattedNewBalance() {
		return df.format(this.newBalance);
	}
	
	/**
	 * @return Formatted string of the amount the balance was increased by
	 */
	public String getFormattedAmountIncreased() {
		return df.format(this.amount);
	}

}
