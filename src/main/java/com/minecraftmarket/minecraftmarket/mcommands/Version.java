package com.minecraftmarket.minecraftmarket.mcommands;

import com.minecraftmarket.minecraftmarket.Market;
import com.minecraftmarket.minecraftmarket.util.Chat;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

public class Version extends MarketCommand {

	public Version() {
		super("version", "Minecraft Market plugin version", "", "minecraftmarket.admin");
	}

	@Override
	public void run(CommandSource sender, String[] args) {
		String v = Market.getVersion();
		sender.sendMessage(Text.of(Chat.get().prefix + " Version " + v));
	}
	
	

}
