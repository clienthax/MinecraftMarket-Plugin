package com.minecraftmarket.minecraftmarket;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;

import java.util.Optional;

public class ShopCommand {

	@Listener
	public void onStoreCommand(SendCommandEvent event) {
		Optional<Player> playerOptional = event.getCause().first(Player.class);
		if(playerOptional.isPresent() && event.getCommand().equalsIgnoreCase(Market.getPlugin().getShopCommand().replace("/",""))) {
			event.setCancelled(true);
			if (!Market.getPlugin().isGuiEnabled()) {
				return;
			} else {
				//TODO
				// Shop.getInstance().showCategories(playerOptional.get());
				return;
			}
		}
	}
}
