package com.minecraftmarket.minecraftmarket.mcommands;

import org.bukkit.command.CommandSender;

import com.minecraftmarket.minecraftmarket.command.CommandTask;
import com.minecraftmarket.minecraftmarket.util.Chat;

public class Check extends MarketCommand {
	
	public Check() {
		super("check", "Force check for purchases", "", "minecraftmarket.admin");
	}

	@Override
	public void run(CommandSender sender, String[] args) {
		CommandTask.check();;
		sender.sendMessage(Chat.get().prefix + getMsg("messages.check"));
		return;
		
	}

}
