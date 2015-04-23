package com.wvaviator.IngotEconomy.Players;

import com.wvaviator.IngotEconomy.Account.AccountManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;

public class JoinHandler {
	
	@EventHandler
	public void onJoinWorld(EntityJoinWorldEvent e) {
		
		if (!(e.entity instanceof EntityPlayer)) return;
		EntityPlayer player = (EntityPlayer) e.entity;
		String uuid = player.getUniqueID().toString();
		String name = player.getName();
		
		if (PlayerManager.hasJoinedBefore(uuid)) {
			if (PlayerManager.hasNameChanged(uuid, name)) {
				PlayerManager.updateName(uuid, name);
			}
			return;
		}
		PlayerManager.addPlayer(uuid, name);
		AccountManager.createNewAccount(player);
	}

}
