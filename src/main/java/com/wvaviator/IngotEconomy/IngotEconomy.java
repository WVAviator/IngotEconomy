package com.wvaviator.IngotEconomy;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.apache.logging.log4j.Logger;

import com.wvaviator.IngotEconomy.Commands.BalanceCommand;
import com.wvaviator.IngotEconomy.Commands.BalanceTopCommand;
import com.wvaviator.IngotEconomy.Commands.IngotCommand;
import com.wvaviator.IngotEconomy.Commands.PayCommand;
import com.wvaviator.IngotEconomy.Database.Database;
import com.wvaviator.IngotEconomy.Players.JoinHandler;

@Mod(modid = "ingoteconomy", name = "Ingot Economy", version = "1.0.0", acceptableRemoteVersions = "*")
public class IngotEconomy {
	
	Configuration config;
	public static Logger logger;
	File modDirectory;
	public static File ingotDirectory;
	public static EventBus EVENT_BUS = new EventBus();
	
	@SubscribeEvent
	public void onPreInitialization(FMLPreInitializationEvent e) {
		
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		
		File modDirectory = new File(e.getModConfigurationDirectory().getParent());
		File ingotDirectory = new File(modDirectory.getPath() + "/IngotEconomy");		
		if (!(ingotDirectory.exists())) ingotDirectory.mkdir();
		
	}
	
	@SubscribeEvent
	public void onInitialization(FMLInitializationEvent e) {
		
		Database.createDatabase();
		MinecraftForge.EVENT_BUS.register(new JoinHandler());
		
	}
	
	@SubscribeEvent
	public void onServerStart(FMLServerStartingEvent e) {
		
		e.registerServerCommand(new PayCommand());
		e.registerServerCommand(new BalanceCommand());
		e.registerServerCommand(new BalanceTopCommand());
		e.registerServerCommand(new IngotCommand());
		
	}

}
