package com.wvaviator.IngotEconomy.API.Events;

import java.text.DecimalFormat;

import com.wvaviator.IngotEconomy.Players.PlayerManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**BalanceDecreaseEvent is fired whenever a players account decreases by a certain amount.
 * This does not include transfers from one account to the other. For that, use
 * BalanceTransferEvent
 */
@Cancelable
public class BalanceDecreaseEvent extends Event {
	
	private String uuid;
	private double oldBalance;
	private double newBalance;
	private double amount;
	private DecimalFormat df = new DecimalFormat("#,###.00");

	public BalanceDecreaseEvent(String uuid, double oldBalance, double amount) {
		
		this.uuid = uuid;
		this.oldBalance = oldBalance;
		this.amount = amount;
		this.newBalance = oldBalance - amount;
		
	}
	
	/**
	 * @return The UUID of the player whose balance decreased
	 */
	public String getUUID() {
		return this.uuid;
	}
	
	/**
	 * @return The name of the player whose balance decreased
	 */
	public String getPlayerName() {
		return PlayerManager.getName(uuid);
	}
	
	/**
	 * @return The player whose balance decreased, or null if not online
	 */
	public EntityPlayer getPlayer() {
		return PlayerManager.getOnlineFromName(getPlayerName());
	}
	
	/**
	 * @return The balance before it was decreased
	 */
	public double getOldBalance() {
		return this.oldBalance;
	}
	
	/**
	 * @return The new balance after being decreased
	 */
	public double getNewBalance() {
		return this.newBalance;
	}
	
	/**
	 * @return The amount the balance has decreased by
	 */
	public double getAmountDecreased() {
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
	 * @return Formatted string of the amount the balance was decreased by
	 */
	public String getFormattedAmountDecreased() {
		return df.format(this.amount);
	}
	
}
