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
		

	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		return sender.canUseCommand(IngotConfiguration.ingotPerm, getName());
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
