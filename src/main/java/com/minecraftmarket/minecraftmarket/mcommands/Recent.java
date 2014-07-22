package com.minecraftmarket.minecraftmarket.mcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minecraftmarket.minecraftmarket.recentgui.RecentGUI;

public class Recent extends MarketCommand {

	public Recent() {
		super("recent", "Show recent donated players", "", "minecraftmarket.admin");
	}

	@Override
	public void run(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			new RecentGUI((Player) sender);
		}
		return;
	}

}
