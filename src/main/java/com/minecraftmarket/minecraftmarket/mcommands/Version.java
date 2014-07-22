package com.minecraftmarket.minecraftmarket.mcommands;

import org.bukkit.command.CommandSender;

import com.minecraftmarket.minecraftmarket.Market;
import com.minecraftmarket.minecraftmarket.util.Chat;

public class Version extends MarketCommand {

	public Version() {
		super("version", "Minecraft Market plugin version", "", "minecraftmarket.admin");
	}

	@Override
	public void run(CommandSender sender, String[] args) {
		String v = Market.getPlugin().getDescription().getVersion();
		sender.sendMessage(Chat.get().prefix + " Version " + v);
	}
	
	

}
