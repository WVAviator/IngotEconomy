package com.wvaviator.IngotEconomy.API.Events;

import java.text.DecimalFormat;

import com.wvaviator.IngotEconomy.Players.PlayerManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**BalanceIncreaseEvent is fired whenever a players account increases by a certain amount.
 * This does not include transfers from one account to the other. For that, use
 * BalanceTransferEvent
 */
@Cancelable
public class BalanceIncreaseEvent extends Event {
	
	private String uuid;
	private double oldBalance;
	private double newBalance;
	private double amount;
	private DecimalFormat df = new DecimalFormat("#,###.00");

	public BalanceIncreaseEvent(String uuid, double oldBalance, double amount) {
		
		this.uuid = uuid;
		this.oldBalance = oldBalance;
		this.amount = amount;
		this.newBalance = oldBalance + amount;
		
	}
	
	/**
	 * @return The UUID of the player whose balance increased
	 */
	public String getUUID() {
		return this.uuid;
	}
	
	/**
	 * @return The name of the player whose balance increased
	 */
	public String getPlayerName() {
		return PlayerManager.getName(uuid);
	}
	
	/**
	 * @return The player whose balance increased, or null if not online
	 */
	public EntityPlayer getPlayer() {
		return PlayerManager.getOnlineFromName(getPlayerName());
	}
	
	/**
	 * @return The balance before it was increased
	 */
	public double getOldBalance() {
		return this.oldBalance;
	}
	
	/**
	 * @return The new balance after being increased
	 */
	public double getNewBalance() {
		return this.newBalance;
	}
	
	/**
	 * @return The amount the balance has increased by
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
	 * @return Formatted string of the amount the balance was increased by
	 */
	public String getFormattedAmountIncreased() {
		return df.format(this.amount);
	}
	
}
