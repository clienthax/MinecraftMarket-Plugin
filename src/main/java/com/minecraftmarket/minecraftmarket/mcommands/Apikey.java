package com.minecraftmarket.minecraftmarket.mcommands;

import com.minecraftmarket.minecraftmarket.Api;
import com.minecraftmarket.minecraftmarket.util.Chat;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

public class Apikey extends MarketCommand {

	public Apikey() {
		super("apikey", "Change Apikey", "<APIKEY>", "minecraftmarket.admin");
	}

	@Override
	public void run(CommandSource sender, String[] args) {
		String Apikey = args[0];
		if (Apikey.matches("[0-9a-f]+") && Apikey.length() == 32) {
			if (Api.authApikey(args[0])) {
				sender.sendMessage(Text.of(Chat.get().prefix + "Server authenticated with Minecraft Market."));
				return;
			}

		}
		sender.sendMessage(Text.of(Chat.get().prefix + "Server did not authenticate, please check API-KEY."));
		return;
	}

}
