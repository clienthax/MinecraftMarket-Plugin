package com.minecraftmarket.minecraftmarket.mcommands;

import com.minecraftmarket.minecraftmarket.command.CommandTask;
import com.minecraftmarket.minecraftmarket.util.Chat;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

public class Check extends MarketCommand {
	
	public Check() {
		super("check", "Force check for purchases", "", "minecraftmarket.admin");
	}

	@Override
	public void run(CommandSource sender, String[] args) {
		CommandTask.check();
		sender.sendMessage(Text.of(Chat.get().prefix + getMsg("messages", "check")));
		return;
		
	}

}
