package com.minecraftmarket.minecraftmarket.shop;

import com.minecraftmarket.minecraftmarket.Api;
import com.minecraftmarket.minecraftmarket.Market;
import com.minecraftmarket.minecraftmarket.json.JSONArray;
import com.minecraftmarket.minecraftmarket.json.JSONException;
import com.minecraftmarket.minecraftmarket.json.JSONObject;
import com.minecraftmarket.minecraftmarket.util.Chat;
import com.minecraftmarket.minecraftmarket.util.Json;
import com.minecraftmarket.minecraftmarket.util.Log;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Arrays;

public class Shop {
	static Shop instance;
	private Inventory guiHub;

	//TODO
	/*
	public void setupGUI() {
		createNewGui();
	}

	public void showGui(Player player, int num) {
		ShopCategory catetory = ShopCategory.getCategoryByID(num);
		if (catetory.getInventory() != null) {
			player.openInventory(catetory.getInventory());
			return;
		}
	}

	public void showCategories(Player player) {
		if (guiHub != null) player.openInventory(guiHub);
	}

	public void createNewGui() {
		try {
			clearCache();
			String gui = Json.getJSON(Api.getUrl() + "/gui");
			Log.response("GUI", gui);
			
			if (Json.isJson(gui)) {
				JSONObject json = new JSONObject(gui);
				JSONArray itemArray = json.optJSONArray("result");
				JSONArray categoryArray = json.optJSONArray("categories");
				int max = itemArray.length();
				for (int i = 0; i < categoryArray.length(); i++) {
					if (i == 0) {
						
						//Creating categories hub
                        String colorID = Market.getPlugin().getColor();
                        if (colorID == null) {
                            colorID = "&0";
                            Log.log("Could not retrieve color id from configurationNode.");
                        }
                        Log.log(colorID);
                        guiHub = createInventory(ChatColor.translateAlternateColorCodes('&', colorID + "Categories"), getInventorySize(categoryArray.length()));
						for (int cate = 0; cate < categoryArray.length(); cate++) {
							String name = getJsonString(categoryArray, cate, "name");
							String icon = "130";
							try {
								icon = getJsonString(categoryArray, cate, "iconid");
							} catch (Exception e) {
								icon = "130";
							}
							if (icon.equalsIgnoreCase("null") || icon.equals("")) {
								icon = "130";
							}
							guiHub.setItem(cate, Createcategory(name, icon));
						}
					}
					
					//Creating single category packages
					int id = getJsonInt(categoryArray, i, "id");
					String name = "Category: " + getJsonString(categoryArray, i, "name");
					int InvSize = itemCount(id, itemArray);
					Inventory inv = createInventory(name, getInventorySize(InvSize + 1));
					
					// Adding packages to caregory
					int placement = 0;
					for (int t = 0; t < max; t++) {
						int iid = getJsonInt(itemArray, t, "categoryid");
						if (iid == id) {
							inv.setItem(placement, createItem(t, itemArray));
							placement++;
						}
					}
					inv.setItem(inv.getSize() - 1, createCategoryPage());
					
					//Setting up new category
					ShopCategory category = new ShopCategory(name, id, i, inv);
					//Create || adding to GUI
					category.create();
				}
			}
		} catch (Exception e) {
			Log.log(e);
		}
	}

	private int itemCount(int id, JSONArray jsonResult) throws JSONException {
		int num = 0;
		int max = jsonResult.length();
		for (int t = 0; t < max; t++) {
			int iid = getJsonInt(jsonResult, t, "categoryid");
			if (id == iid) {
				num++;
			}
		}
		return num;
	}

	private void clearCache() {
		if (guiHub != null) {
			guiHub.clear();
		}
		ShopCategory.removeAll();
		ShopPackage.removeAll();
	}

	private ItemStack createItem(int i, JSONArray jsonresult) {
		if (jsonresult != null) {
			try {
				String name = getJsonString(jsonresult, i, "name");
				String cate = getJsonString(jsonresult, i, "category");
				String url = getJsonString(jsonresult, i, "url");
				String price = getJsonString(jsonresult, i, "price");
				int id = getJsonInt(jsonresult, i, "id");
				String icon;
				try {
					icon = getJsonString(jsonresult, i, "iconid");
				} catch (Exception e) {
					icon = "54";
				}
				String currency = getJsonString(jsonresult, i, "currency");
				String description = jsonresult.getJSONObject(i).getString("description");

				//Create GUI package
				ShopPackage guiPackage = new ShopPackage(id, name, currency, price, cate, description, url, icon);
				guiPackage.create();
				return guiPackage.getItem();
			} catch (Exception e) {
				Log.log(e);
			}
		}
		return null;

	}

	private static String getJsonString(JSONArray jsonresult, int i, String args0) throws JSONException {
		return jsonresult.getJSONObject(i).getString(args0);
	}

	private static int getJsonInt(JSONArray jsonresult, int i, String args0) throws JSONException {
		return jsonresult.getJSONObject(i).getInt(args0);
	}

	private ItemStack createCategoryPage() {
		ItemStack item = new ItemStack(Material.IRON_FENCE, 1);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + getMsg("shop.back-to-category"));
		im.setLore(Arrays.asList("", getMsg("shop.back-to-category-desc")));
		item.setItemMeta(im);
		return item;
	}

	private int getInventorySize(int max) {
		if (max <= 0) return 9;
		int total = (int) Math.ceil(max / 9.0);
		return total > 5 ? 54 : total * 9;
	}

	@SuppressWarnings("deprecation")
	private ItemStack Createcategory(String name, String icon) {
		int id = 130;
		byte data = 0;
		if (icon.contains(":")) {
			String[] icons = icon.split(":");
			id = Integer.parseInt(icons[0]);
			data = (byte) Integer.parseInt(icons[1]);
		} else {
			id = Integer.parseInt(icon);
		}
		ItemStack item = new ItemStack(id, 1, (short) 0, data);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + name);
		im.setLore(Arrays.asList("", getMsg("shop.open-category").replace("<category>", name)));
		item.setItemMeta(im);
		return item;
	}

	private Inventory createInventory(String name, int size) {
		Inventory inv;
		if (name.length() > 32) {
			inv = Bukkit.createInventory(null, size, name.substring(0, 31));
		} else {
			inv = Bukkit.createInventory(null, size, name);
		}
		return inv;
	}

	public static Shop getInstance() {
		if (instance == null) instance = new Shop();
		return instance;
	}
	
	private String getMsg(String string) {
		return Chat.get().getLanguage().getString(string);
	}

	private Shop() {
	}
	*/
}
