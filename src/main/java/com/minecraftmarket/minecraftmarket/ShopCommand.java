package com.minecraftmarket.minecraftmarket;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.minecraftmarket.minecraftmarket.shop.Shop;

public class ShopCommand implements Listener {

	@EventHandler
	public void onStoreCommand(PlayerCommandPreprocessEvent event) {
		if (event.getMessage().equalsIgnoreCase(Market.getPlugin().getShopCommand())) {
			event.setCancelled(true);
			if (!Market.getPlugin().isGuiEnabled()) {
				return;
			} else {
				Shop.getInstance().showCategories(event.getPlayer());
				return;
			}
		}
	}
}
