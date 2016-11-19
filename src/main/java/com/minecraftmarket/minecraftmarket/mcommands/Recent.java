package com.minecraftmarket.minecraftmarket.mcommands;


import com.minecraftmarket.minecraftmarket.recentgui.RecentGUI;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;

public class Recent extends MarketCommand {

	public Recent() {
		super("recent", "Show recent donated players", "", "minecraftmarket.admin");
	}

	@Override
	public void run(CommandSource sender, String[] args) {
		if (sender instanceof Player) {
			new RecentGUI((Player) sender);
		}
		return;
	}

}
