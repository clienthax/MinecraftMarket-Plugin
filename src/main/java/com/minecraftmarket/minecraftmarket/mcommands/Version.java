package com.minecraftmarket.minecraftmarket.mcommands;

import com.minecraftmarket.minecraftmarket.Market;
import com.minecraftmarket.minecraftmarket.util.Chat;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandSource;

public class Version extends MarketCommand {

	public Version() {
		super("version", "Minecraft Market plugin version", "", "minecraftmarket.admin");
	}

	@Override
	public void run(CommandSource sender, String[] args) {
		String v = Market.getVersion();
		sender.sendMessage(Texts.of(Chat.get().prefix + " Version " + v));
	}
	
	

}
