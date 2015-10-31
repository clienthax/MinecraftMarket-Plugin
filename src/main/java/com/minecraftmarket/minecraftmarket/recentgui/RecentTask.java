package com.minecraftmarket.minecraftmarket.recentgui;

import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.type.OrderedInventory;

public class RecentTask implements Runnable {

	Inventory inv;

	public RecentTask(Inventory inv) {
		this.inv = inv;
	}

	public void run() {
		RecentUtil util = new RecentUtil();
		for (int i = 0; i < 18; i++) {
			ItemStack item = util.get(i);
			if (item != null) {
				((OrderedInventory)inv).set(SlotIndex.of(i), item);
			}
		}
	}
}
