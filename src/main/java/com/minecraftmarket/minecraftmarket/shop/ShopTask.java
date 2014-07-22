package com.minecraftmarket.minecraftmarket.shop;

import org.bukkit.scheduler.BukkitRunnable;

public class ShopTask extends BukkitRunnable {

	public void run() {
		Shop.getInstance().setupGUI();
	}

}
