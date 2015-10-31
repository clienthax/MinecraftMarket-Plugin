package com.minecraftmarket.minecraftmarket.recentgui;

import com.minecraftmarket.minecraftmarket.Market;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;

public class RecentGUI {

	private Player player;
	private Inventory inv;

	public RecentGUI(Player player) {
		this.player = player;
//TODO
//		inv = Bukkit.createInventory(null, 18, "Recent Donors");
		RecentTask task = new RecentTask(inv);
		Market.getPlugin().getGame().getScheduler().createTaskBuilder().async().execute(task).submit(Market.getPlugin());
		player.openInventory(inv);
	}

	public Player getPlayer() {
		return player;
	}

	public Inventory getInv() {
		return inv;
	}
}
