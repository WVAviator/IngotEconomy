package com.wvaviator.IngotEconomy.Commands;

import java.util.ArrayList;
import java.util.List;

import com.wvaviator.IngotEconomy.Chat;
import com.wvaviator.IngotEconomy.IngotConfiguration;
import com.wvaviator.IngotEconomy.API.Events.BalanceSetEvent;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class IngotCommand extends CommandBase implements ICommand {

	private List aliases;
	public IngotCommand() {
		this.aliases = new ArrayList();
		this.aliases.add("ingot");
		this.aliases.add("economy");
		this.aliases.add("econ");
		this.aliases.add("eco");
	}
	@Override
	public String getName() {
		String name = "ingot";
		return name;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		String usage = Chat.color("/ingot give <player>\n /ingot take <player>\n /ingot set <player>");
		return usage;
	}

	@Override
	public List getAliases() {
		return this.aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		
		if (args.length < 3 || args.length > 3) {
			Chat.sendChat(sender, "Incorrect number of arguments!");
			Chat.sendChat(sender, getCommandUsage(sender));
			return;
		}
		double amount = 0;
		try {
			amount = Double.parseDouble(args[2]);
		} catch (NumberFormatException e) {
			Chat.sendChat(sender, "The third argument must be a number!");
			Chat.sendChat(sender, getCommandUsage(sender));
			return;
		}
		if (amount <= 0) {
			Chat.sendChat(sender, "Negative numbers cannot be used!");
			return;
		}
		String uuid = PlayerManager.getUUID(args[1]);
		if (uuid == null) {
			Chat.sendChat(sender, Chat.playerNotFound);
			return;
		}
		Account acct = new Account(uuid);
		if (args[0].equalsIgnoreCase("set") {
			acct.set(amount);
			Chat.sendChat(sender, "You set " + acct.getName() + "'s account to " + AccountManager.formatAmount(amount) + IngotConfiguration.curName + "!";
			return;
		}
		if (args[0].equalsIgnoreCase("give")) {
			acct.add(amount);
			Chat.sendChat(sender, "You added " + AccountManager.formatAmount(amount) + " to " + acct.getName() + "'s account.";
			return;
		}
		if (args[0].equalsIgnoreCase("take")) {
			acct.subtract(amount);
			Chat.sendChat(sender, "You took " + AccountManager.formatAmount(amount) + " from " + acct.getName() + "'s account.";
			return;
		}
		Chat.sendChat(sender, "Incorrect argument!");
		Chat.sendChat(sender, getCommandUsage(sender));

	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		return sender.canUseCommand(IngotConfiguration.ingotPerm, getName());
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			String[] subcommand = {"give", "take", "set"};
			return getListOfStringsMatchingLastWord(args, subcommand);
		}
		if (args.length == 2) {
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
