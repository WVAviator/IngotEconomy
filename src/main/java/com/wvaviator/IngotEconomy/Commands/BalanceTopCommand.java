package com.wvaviator.IngotEconomy.Commands;

import java.util.ArrayList;
import java.util.List;

import com.wvaviator.IngotEconomy.Chat;
import com.wvaviator.IngotEconomy.IngotConfiguration;
import com.wvaviator.IngotEconomy.Account.Account;
import com.wvaviator.IngotEconomy.Account.AccountManager;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class BalanceTopCommand extends CommandBase implements ICommand {

	private List aliases;
	public BalanceTopCommand() {
		this.aliases = new ArrayList();
		this.aliases.add("balancetop");
		this.aliases.add("baltop");
		this.aliases.add("top");
		this.aliases.add("moneytop");
	}
	@Override
	public String getName() {
		String name = "balancetop";
		return name;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		String usage = Chat.color("/balancetop <page>");
		return usage;
	}

	@Override
	public List getAliases() {
		return this.aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		
		int page = 0;
		if (args.length == 0) page = 1;
		
		if (args.length > 1) {
			getCommandUsage(sender); 
			return;
		}
		
		try {
			page = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			Chat.sendChat(sender, getCommandUsage(sender));
			return;
		}
		
		List<Account> topAccounts = AccountManager.getTopAccounts(page);
		Chat.sendChat(sender, "===== Top Accounts === Page " + page + " =====");
		for (int i = ((page - 1) * 10) + 1; i < ((page * 10) + 1); i++) {
			if (topAccounts.size() == (i-1)) break;
			Account acct = topAccounts.get(i-1);
			Chat.sendChat(sender, i + ". " + acct.getName() + " - " + acct.getFormattedBalance() + IngotConfiguration.curName);
		}

	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		return sender.canUseCommand(IngotConfiguration.balTopPerm, getName());
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
