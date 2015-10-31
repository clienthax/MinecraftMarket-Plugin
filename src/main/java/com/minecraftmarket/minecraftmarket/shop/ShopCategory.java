package com.minecraftmarket.minecraftmarket.shop;

import java.util.List;

import com.google.common.collect.Lists;
import org.spongepowered.api.item.inventory.Inventory;

public class ShopCategory {

	private static List<ShopCategory> categories = Lists.newArrayList();
	private int ID;
	private int slot;
	private String name;
	private Inventory inventory;

	public ShopCategory(String name, int ID, int slot, Inventory inventory) {
		this.name = name;
		this.ID = ID;
		this.slot = slot;
		this.inventory = inventory;
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public int getSlot() {
		return slot;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void create() {
		if (!categories.contains(this)) categories.add(this);
	}

	public static ShopCategory getCategoryByID(int ID) {
		for (ShopCategory category : categories) {
			if (category.getID() == ID) {
				return category;
			}
		}
		return null;
	}

	public void remove() {
		categories.remove(this);
	}

	public static List<ShopCategory> getCategories() {
		return categories;
	}

	public static void removeAll() {
		for (ShopCategory gc : categories) {
			gc.getInventory().clear();
		}
		categories.clear();
	}

	public static ShopCategory getCategoryBySlot(int slot) {
		for (ShopCategory category : categories) {
			if (category.getSlot() == slot) {
				return category;
			}
		}
		return null;
	}

}
