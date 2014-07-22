package com.minecraftmarket.minecraftmarket.shop;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;
import com.minecraftmarket.minecraftmarket.util.Chat;
import com.minecraftmarket.minecraftmarket.util.Log;

public class ShopPackage {

	static List<ShopPackage> packages = Lists.newArrayList();

	private String name;
	private ItemStack icon;
	private String url;
	private int id;
	private String currency;
	private String description;
	private Material material;
	private ItemMeta meta;
	private String category;
	private String price;
	private String iconID;

	@SuppressWarnings("deprecation")
	public ShopPackage(int id, String name, String currency, String price, String category, String description, String url, String iconID) {
		this.iconID = iconID;
		this.material = getMaterialType();
		this.name = name;
		this.id = id;
		this.url = url;
		this.currency = currency;
		this.price = price;
		this.description = description;
		this.icon = new ItemStack(material, 1, (short) 0, getMaterialData());
		this.category = category;
		this.meta = this.icon.getItemMeta();
	}

	public void create() {
		try {
			packages.add(this);
			meta.setDisplayName(ChatColor.RESET + "ID: " + id);
			createLore();
			icon.setItemMeta(meta);
			for(Enchantment en : icon.getEnchantments().keySet()) {
				icon.removeEnchantment(en);
			}
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
		lore.add(Chat.get().getMsg("shop.item") + ChatColor.GREEN + name);
		lore.add("");
		lore.add(Chat.get().getMsg("shop.category") + ChatColor.GREEN + category);
		lore.add("");
		if (!description.equals("")) {
			lore.add(ChatColor.GOLD + Chat.get().getMsg("shop.description"));
			String[] DescSplit = ChatColor.translateAlternateColorCodes('&', description).split("\r\n");
			for (String str : DescSplit)
				lore.add(str);
			lore.add("");
		}
		lore.add(ChatColor.GOLD + Chat.get().getMsg("shop.price") + ChatColor.GREEN + "" + ChatColor.UNDERLINE + price + " " + currency);
		lore.add("");
		lore.add(ChatColor.ITALIC + Chat.get().getMsg("shop.click-here"));
		meta.setLore(lore);
	}

	private byte getMaterialData() {
		byte data = 0;
		try {
			if (this.iconID.contains(":")) {
				String[] s = this.iconID.split(":");
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
	private Material getMaterialType() {
		int iconid;
		try {
			if (this.iconID.contains(":")) {
				String[] s = this.iconID.split(":");
				iconid = Integer.parseInt(s[0]);
				return Material.getMaterial(iconid);
			}
			iconid = Integer.parseInt(iconID);
			return Material.getMaterial(iconid);
		} catch (Exception e) {
			return Material.CHEST;
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
		return icon;
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

	public Material getMaterial() {
		return material;
	}

	public ItemMeta getMeta() {
		return meta;
	}

}
