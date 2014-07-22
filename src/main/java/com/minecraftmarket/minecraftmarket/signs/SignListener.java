package com.minecraftmarket.minecraftmarket.signs;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;

import com.minecraftmarket.minecraftmarket.Market;
import com.minecraftmarket.minecraftmarket.util.Chat;

public class SignListener implements Listener {

	public Market plugin = Market.getPlugin();
	private Chat chat = Chat.get();

	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		if (event.getLine(0).equalsIgnoreCase("[Recent]")) {
			if (!event.getPlayer().hasPermission("minecraftmarket.admin")) {
				event.getPlayer().sendMessage(ChatColor.DARK_RED + Chat.get().getLanguage().getString(("signs.no-permissions")));
				return;
			}
			try {
				int id = Integer.parseInt(event.getLine(1));
				Location loc = event.getBlock().getLocation();
				SignData sign = SignData.getSignByLocation(loc);
				if (sign != null) {
					sign.remove();
				}
				SignData signData = new SignData(loc, id - 1);
				signData.update();
				event.getPlayer().sendMessage(chat.prefix + ChatColor.GREEN + Chat.get().getLanguage().getString(("signs.created")));
			} catch (NumberFormatException ex) {
				event.getBlock().breakNaturally();
				event.getPlayer().sendMessage(chat.prefix + ChatColor.DARK_RED + "Wrong sign format");
			}
		}

	}

	@EventHandler
	public void onSignBreak(BlockBreakEvent event) {
		SignData sign = SignData.getSignByLocation(event.getBlock().getLocation());
		if (sign != null) {
			if (event.getPlayer().hasPermission("signs.remove")) {
				sign.remove();
				event.getPlayer().sendMessage(chat.prefix + ChatColor.RED + "Sign removed");
			} else {
				event.getPlayer().sendMessage(ChatColor.DARK_RED + Chat.get().getLanguage().getString(("signs.no-permissions")));
			}
		}
	}

}
