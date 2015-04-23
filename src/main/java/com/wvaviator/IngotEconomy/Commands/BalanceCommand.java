package com.wvaviator.IngotEconomy.Commands;

import java.util.ArrayList;
import java.util.List;

import com.wvaviator.IngotEconomy.Chat;
import com.wvaviator.IngotEconomy.IngotConfiguration;
import com.wvaviator.IngotEconomy.Account.Account;
import com.wvaviator.IngotEconomy.Players.PlayerManager;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class BalanceCommand extends CommandBase implements ICommand {

	private List aliases;
	public BalanceCommand() {
		this.aliases = new ArrayList();
		this.aliases.add("balance");
		this.aliases.add("bal");
		this.aliases.add("money");
	}
	@Override
	public String getName() {
		String name = "balance";
		return name;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		String usage = Chat.color("/balance <player>");
		return usage;
	}

	@Override
	public List getAliases() {
		return this.aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		
		if (!(sender instanceof EntityPlayer)) {
			if (args.length == 0) {
				Chat.sendChat(sender, Chat.noConsole);
				return;
			}
			
			if (args.length > 1) {
				Chat.sendChat(sender, getCommandUsage(sender));
				return;
			}
			
			String uuid = PlayerManager.getUUID(args[0]);
			if (uuid == null) {
				Chat.sendChat(sender, Chat.playerNotFound);
				return;
			}
			Account acct = new Account(uuid);
			double amount = acct.getBalance();
			Chat.sendChat(sender, "The balance for " + acct.getName() + " is " + amount);
		}
		
		if (args.length > 1) {
			Chat.sendChat(sender, getCommandUsage(sender));
			return;
		}
		
		EntityPlayer player = (EntityPlayer) sender;
		String uuid = null;
		
		if (args.length == 0) uuid = player.getUniqueID().toString();
		if (args.length == 1) {
			uuid = PlayerManager.getUUID(args[0]);
			if (uuid == null) {
				Chat.sendChat(player, Chat.playerNotFound);
				return;
			}
		}
		
		Account acct = new Account(uuid);
		double amount = acct.getBalance();
		Chat.sendChat(player, "The balance for " + acct.getName() + " is " + amount);
		
		

	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		return sender.canUseCommand(IngotConfiguration.balPerm, getName());
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
