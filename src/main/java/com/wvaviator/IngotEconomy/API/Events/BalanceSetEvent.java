package com.wvaviator.IngotEconomy.API.Events;

import java.text.DecimalFormat;

import com.wvaviator.IngotEconomy.Players.PlayerManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**BalanceSetEvent is fired whenever a player's account is set to a specific amount
 * regardless of how much they had before. An example would be use of /ingot set
 */
@Cancelable
public class BalanceSetEvent extends Event{
	
	private String uuid;
	private double oldBalance;
	private double newBalance;
	private double amount;
	private DecimalFormat df = new DecimalFormat("#,###.00");

	public BalanceSetEvent(String uuid, double oldBalance, double newBalance) {
		
		this.uuid = uuid;
		this.oldBalance = oldBalance;
		this.newBalance = newBalance;
		this.amount = newBalance - oldBalance;
		
	}
	
	/**
	 * @return The UUID of the player whose balance changed
	 */
	public String getUUID() {
		return this.uuid;
	}
	
	/**
	 * @return The name of the player whose balance changed
	 */
	public String getPlayerName() {
		return PlayerManager.getName(uuid);
	}
	
	/**
	 * @return The player whose balance changed, or null if not online
	 */
	public EntityPlayer getPlayer() {
		return PlayerManager.getOnlineFromName(getPlayerName());
	}
	
	/**
	 * @return The balance before it was changed
	 */
	public double getOldBalance() {
		return this.oldBalance;
	}
	
	/**
	 * @return The new balance after being changed
	 */
	public double getNewBalance() {
		return this.newBalance;
	}
	
	/**
	 * @return The amount the balance changed (will be negative if decreased)
	 */
	public double getAmountIncreased() {
		return this.amount;
	}
	
	/**
	 * @return Formatted string of the old balance
	 */
	public String getFormattedOldBalance() {
		return df.format(this.oldBalance);
	}
	
	/**
	 * @return Formatted string of the new balance
	 */
	public String getFormattedNewBalance() {
		return df.format(this.newBalance);
	}
	
	/**
	 * @return Formatted string of the amount the balance was changed by
	 */
	public String getFormattedAmountChanged() {
		return df.format(this.amount);
	}
	
}
