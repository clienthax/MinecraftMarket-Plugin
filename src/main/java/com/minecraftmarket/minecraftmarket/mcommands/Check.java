package com.minecraftmarket.minecraftmarket.mcommands;

import com.minecraftmarket.minecraftmarket.command.CommandTask;
import com.minecraftmarket.minecraftmarket.util.Chat;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandSource;

public class Check extends MarketCommand {
	
	public Check() {
		super("check", "Force check for purchases", "", "minecraftmarket.admin");
	}

	@Override
	public void run(CommandSource sender, String[] args) {
		CommandTask.check();
		sender.sendMessage(Texts.of(Chat.get().prefix + getMsg("messages", "check")));
		return;
		
	}

}
