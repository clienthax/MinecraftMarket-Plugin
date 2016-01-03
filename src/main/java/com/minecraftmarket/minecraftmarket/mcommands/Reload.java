package com.minecraftmarket.minecraftmarket.mcommands;

import com.minecraftmarket.minecraftmarket.Market;
import com.minecraftmarket.minecraftmarket.util.Chat;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

public class Reload extends MarketCommand {

	public Reload() {
		super("reload", "Reload MinecraftMarket plugin and settings", "", "minecraftmarket.admin");
	}

	@Override
	public void run(CommandSource sender, String[] args) {
		Market.getPlugin().reload();
		sender.sendMessage(Text.of(Chat.get().prefix + getMsg("messages","reload")));
		return;
	}

}
