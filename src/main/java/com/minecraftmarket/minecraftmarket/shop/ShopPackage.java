package com.minecraftmarket.minecraftmarket.shop;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.minecraftmarket.minecraftmarket.Market;
import com.minecraftmarket.minecraftmarket.util.Chat;
import com.minecraftmarket.minecraftmarket.util.Log;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class ShopPackage {

	static List<ShopPackage> packages = Lists.newArrayList();

	private String name;
	private ItemStack itemStack;
	private String url;
	private int id;
	private String currency;
	private String description;
	private ItemType baseItem;
	private String category;
	private String price;
	private String itemString;

	@SuppressWarnings("deprecation")
	public ShopPackage(int id, String name, String currency, String price, String category, String description, String url, String itemString) {
		this.itemString = itemString;
		this.baseItem = getMaterialType();
		this.name = name;
		this.id = id;
		this.url = url;
		this.currency = currency;
		this.price = price;
		this.description = description;
		this.itemStack = Market.getPlugin().getGame().getRegistry().createBuilder(ItemStack.Builder.class).itemType(getMaterialType()).quantity(1).build();
		this.category = category;
	}

	public void create() {
		try {
			packages.add(this);
			itemStack.offer(Keys.DISPLAY_NAME, Text.of(TextStyles.RESET + "ID: " + id));
			createLore();
			//TODO
			/*
			for(Enchantment en : itemStack.getEnchantments().keySet()) {
				itemStack.removeEnchantment(en);
			}*/
		} catch (Exception e) {
			Log.log(e);
		}

	}

	public void remove() {
		packages.remove(this);
	}

	public static void removeAll() {
		packages.clear();
	}

	public static List<ShopPackage> getCategories() {
		return packages;
	}

	private void createLore() {
		List<String> lore = Lists.newArrayList();
		lore.add(Chat.get().getMsg("shop.item") + TextColors.GREEN + name);
		lore.add("");
		lore.add(Chat.get().getMsg("shop.category") + TextColors.GREEN + category);
		lore.add("");
		if (!description.equals("")) {
			lore.add(TextColors.GOLD + Chat.get().getMsg("shop.description"));
			//TODO
			/*String[] DescSplit = TextColors.translateAlternateColorCodes('&', description).split("\r\n");
			for (String str : DescSplit)
				lore.add(str);
			*/
			lore.add("");
		}
		lore.add(TextColors.GOLD + Chat.get().getMsg("shop.price") + TextColors.GREEN + "" + TextStyles.UNDERLINE + price + " " + currency);
		lore.add("");
		lore.add(TextStyles.ITALIC + Chat.get().getMsg("shop.click-here"));
		itemStack.toContainer().set(Keys.ITEM_LORE.getQuery(), lore);
	}

	private byte getMaterialData() {
		byte data = 0;
		try {
			if (this.itemString.contains(":")) {
				String[] s = this.itemString.split(":");
				data = (byte) Integer.parseInt(s[1]);
				return data;
			}
			return 0;
		} catch (Exception e) {
			Log.log(e);
			return 0;
		}
	}

	@SuppressWarnings("deprecation")
	private ItemType getMaterialType() {
		Optional<ItemType> type = Market.getPlugin().getGame().getRegistry().getType(ItemType.class, itemString);
		if(type.isPresent()) {
			return type.get();
		} else {
			return ItemTypes.CHEST;
		}
	}

	public static ShopPackage getById(int id) {
		for (ShopPackage gp : packages) {
			if (gp.getId() == id) return gp;
		}
		return null;
	}

	public static List<ShopPackage> getPackages() {
		return packages;
	}

	public String getName() {
		return name;
	}

	public ItemStack getItem() {
		return itemStack;
	}

	public String getUrl() {
		return url;
	}

	public int getId() {
		return id;
	}

	public String getCurrency() {
		return currency;
	}

	public String getDescription() {
		return description;
	}

	public ItemType getBaseItem() {
		return baseItem;
	}

}
