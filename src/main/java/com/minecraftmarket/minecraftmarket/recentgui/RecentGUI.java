package com.minecraftmarket.minecraftmarket.recentgui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.minecraftmarket.minecraftmarket.Market;

public class RecentGUI {

	private Player player;
	private Inventory inv;

	public RecentGUI(Player player) {
		this.player = player;
		inv = Bukkit.createInventory(null, 18, "Recent Donors");
		RecentTask task = new RecentTask(inv);
		task.runTaskAsynchronously(Market.getPlugin());
		player.openInventory(inv);
	}

	public Player getPlayer() {
		return player;
	}

	public Inventory getInv() {
		return inv;
	}
}
