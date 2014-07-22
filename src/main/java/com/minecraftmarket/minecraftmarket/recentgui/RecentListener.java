package com.minecraftmarket.minecraftmarket.recentgui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RecentListener implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getName().equals("Recent Donors")) {
			event.setCancelled(true);
			return;
		}
	}
}
