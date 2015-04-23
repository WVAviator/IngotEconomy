package com.wvaviator.IngotEconomy.Commands;

import java.util.ArrayList;
import java.util.List;

import com.wvaviator.IngotEconomy.Chat;
import com.wvaviator.IngotEconomy.IngotConfiguration;
import com.wvaviator.IngotEconomy.Account.Account;
import com.wvaviator.IngotEconomy.Account.AccountManager;
import com.wvaviator.IngotEconomy.Players.PlayerManager;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class PayCommand extends CommandBase implements ICommand {

	private List aliases;
	public PayCommand() {
		this.aliases = new ArrayList();
		this.aliases.add("pay");
	}
	@Override
	public String getName() {
		String name = "pay";
		return name;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		String usage = Chat.color("/pay <player> <amount>");
		return usage;
	}

	@Override
	public List getAliases() {
		return this.aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		
		if (!(sender instanceof EntityPlayer)) {
			Chat.sendChat(sender, Chat.noConsole);
			return;
		}
		
		EntityPlayer player = (EntityPlayer) sender;
		
		if (args.length != 2) {
			getCommandUsage(player);
			return;
		}
		
		String receiver = PlayerManager.getUUID(args[0]);
		if (receiver == null) {
			Chat.sendChat(player, Chat.playerNotFound);
			return;
		}
		
		double amount = 0;
		
		try {
			amount = Double.parseDouble(args[1]);
		} catch (NumberFormatException e) {
			Chat.sendChat(player, "Incorrect argument! You must use a real number!");
			return;
		}
		
		if (amount <= 0) {
			Chat.sendChat(player, "You must provide a positive, non-zero number!");
			return;
		}
		
		Account acct = new Account(player.getUniqueID().toString());
		
		if (!(acct.canAfford(amount))) {
			Chat.sendChat(player, Chat.notEnough);
			return;
		}
		
		Account receive = new Account(receiver);
		
		if (acct.equals(receive)) {
			Chat.sendChat(player, "You cannot pay yourself!");
			return;
		}
		
		acct.transferToAccount(receive, amount);
		Chat.sendChat(player, "You sent " + AccountManager.formatAmount(amount) + IngotConfiguration.curName " to " + receive.getName());
		Chat.sendToPlayer(receive.getName(), "You received " + AccountManager.formatAmount(amount) + IngotConfiguration.curName + " from " + acct.getName());
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		return sender.canUseCommand(IngotConfiguration.payPerm, getName());
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
		}
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}
	@Override
	public int compareTo(Object o) {
		return 0;
	}

}
