package com.wvaviator.IngotEconomy;

import java.math.BigDecimal;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class IngotConfiguration {
	
	public static BigDecimal startingBalance;
	public static int payPerm;
	public static int balPerm;
	public static int balOthersPerm;
	public static int balTopPerm;
	public static int ingotPerm;
	
	public static void setupConfig(Configuration config) {
		
		config.addCustomCategoryComment("Basic Account Options", "Configure basic account information such as starting balances");
		
		Property b1 = config.get("Basic Account Options", "Default Starting Balance", 20.00);
		startingBalance = BigDecimal.valueOf(b1.getDouble());
		b1.comment = "This will determine the amount to give new players upon first joining";
		
		config.addCustomCategoryComment("Permissions", "Set permissions for each command. 1 = Any player, 2 = Creative Mode, 4 = Operator");
		
		Property p1 = config.get("Permissions", "/pay", 1);
		Property p2 = config.get("Permissions", "/balance", 1);
		Property p3 = config.get("Permissions", "/balance <others>", 1);
		Property p4 = config.get("Permissions", "/balancetop", 1);
		Property p5 = config.get("Permissions", "/ingot", 4);
		
		if (p1.getInt() < 1 || p1.getInt() > 4) p1.set(1);
		if (p2.getInt() < 1 || p2.getInt() > 4) p2.set(1);
		if (p3.getInt() < 1 || p3.getInt() > 4) p3.set(1);
		if (p4.getInt() < 1 || p4.getInt() > 4) p4.set(1);
		if (p5.getInt() < 1 || p5.getInt() > 4) p5.set(4);
		
		payPerm = p1.getInt();
		balPerm = p2.getInt();
		balOthersPerm = p3.getInt();
		balTopPerm = p4.getInt();
		ingotPerm = p5.getInt();
		
	}

}
