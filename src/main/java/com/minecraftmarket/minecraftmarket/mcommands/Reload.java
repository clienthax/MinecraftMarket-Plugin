package com.minecraftmarket.minecraftmarket.mcommands;

import org.bukkit.command.CommandSender;

import com.minecraftmarket.minecraftmarket.Market;
import com.minecraftmarket.minecraftmarket.util.Chat;

public class Reload extends MarketCommand {

	public Reload() {
		super("reload", "Reload MinecraftMarket plugin and settings", "", "minecraftmarket.admin");
	}

	@Override
	public void run(CommandSender sender, String[] args) {
		Market.getPlugin().reload();
		sender.sendMessage(Chat.get().prefix + getMsg("messages.reload"));
		return;
	}

}
