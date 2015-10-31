package com.minecraftmarket.minecraftmarket.shop;

public class ShopListener {
	//TODO
/*
	Shop gui = Shop.getInstance();

	Chat chat = Chat.get();

	public String getMsg(String string) {
		return Chat.get().getLanguage().getString(string);
	}

	@Listener
	public void onInventoryClick(InventoryClickEvent event) {
		try {
			if (event.getInventory().getTitle().contains("Category: ") || event.getInventory().getTitle().contains("Categories")) {
				event.setCancelled(true);
				if (event.getCurrentItem() == null) {
					event.setCancelled(true);
					return;
				}
				if (event.getCurrentItem().getItemMeta() == null) {
					event.setCancelled(true);
					return;
				}
			}
			if (event.getInventory().getTitle().contains("Category: ")) {
				Player player = (Player) event.getWhoClicked();
				String name = event.getCurrentItem().getItemMeta().getDisplayName();
				
				if (name.contains(getMsg("shop.back-to-category"))) {
					event.getWhoClicked().closeInventory();
					gui.showCategories((Player) event.getWhoClicked());
					event.setCancelled(true);
					return;
				}
				
				String[] id = name.split(": ");
				int id1 = Integer.parseInt(id[1]);
				String url = ShopPackage.getById(id1).getUrl();
				player.sendMessage(chat.prefix + ChatColor.GOLD + getMsg("shop.item-url") + ChatColor.DARK_AQUA + "" + ChatColor.ITALIC + ChatColor.UNDERLINE + url);
				event.getWhoClicked().closeInventory();
				event.setCancelled(true);
				return;
			}
			if (event.getInventory().getTitle().contains("Categories")) {
				int num = ShopCategory.getCategoryBySlot(event.getSlot()).getID();
				event.getWhoClicked().closeInventory();
				gui.showGui((Player) event.getWhoClicked(), num);
				event.setCancelled(true);
			}
			
		} catch (Exception e1) {
		}
	}
*/
}