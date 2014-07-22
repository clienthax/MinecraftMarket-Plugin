package com.minecraftmarket.minecraftmarket.recentgui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class RecentTask extends BukkitRunnable {

	Inventory inv;

	public RecentTask(Inventory inv) {
		this.inv = inv;
	}

	public void run() {
		RecentUtil util = new RecentUtil();
		for (int i = 0; i < 18; i++) {
			ItemStack item = util.get(i);
			if (item != null) {
				this.inv.setItem(i, item);
			}
		}
	}
}
