package com.minecraftmarket.minecraftmarket.mcommands;

import org.bukkit.command.CommandSender;

import com.minecraftmarket.minecraftmarket.Api;
import com.minecraftmarket.minecraftmarket.util.Chat;

public class Apikey extends MarketCommand {

	public Apikey() {
		super("apikey", "Change Apikey", "<APIKEY>", "minecraftmarket.admin");
	}

	@Override
	public void run(CommandSender sender, String[] args) {
		String Apikey = args[0];
		if (Apikey.matches("[0-9a-f]+") && Apikey.length() == 32) {
			if (Api.authApikey(args[0])) {
				sender.sendMessage(Chat.get().prefix + "Server authenticated with Minecraft Market.");
				return;
			}

		}
		sender.sendMessage(Chat.get().prefix + "Server did not authenticate, please check API-KEY.");
		return;
	}

}
