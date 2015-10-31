package com.minecraftmarket.minecraftmarket.mcommands;

import com.minecraftmarket.minecraftmarket.Market;
import com.minecraftmarket.minecraftmarket.signs.SignUpdate;
import com.minecraftmarket.minecraftmarket.util.Chat;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandSource;

public class Signs extends MarketCommand {

	public Signs() {
		super("signs", "Update recent donor signs", "", "minecraftmarket.admin");
	}

	@Override
	public void run(CommandSource sender, String[] args) {
		Market.getPlugin().getGame().getScheduler().createTaskBuilder().async().execute(new SignUpdate()).submit(Market.getPlugin());
		sender.sendMessage(Texts.of(Chat.get().prefix + getMsg("messages", "signs")));
		return;
	}

}
