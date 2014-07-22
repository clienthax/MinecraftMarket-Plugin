package com.minecraftmarket.minecraftmarket.recentgui;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.minecraftmarket.minecraftmarket.json.JSONArray;
import com.minecraftmarket.minecraftmarket.json.JSONObject;

import com.minecraftmarket.minecraftmarket.Api;
import com.minecraftmarket.minecraftmarket.util.Json;
import com.minecraftmarket.minecraftmarket.util.Log;

public class RecentUtil {

	private JSONArray json;

	public RecentUtil() {
		try {
			String recent = Json.getJSON(Api.getUrl() + "/recentdonor");
			Log.response("Recent payment", recent);
			if (!Json.isJson(recent)) {
				json = null;
			} else {
				JSONObject jsono = new JSONObject(recent);
				this.json = jsono.optJSONArray("result");
			}
		} catch (Exception e) {
			Log.log(e);
		}
	}

	@SuppressWarnings("deprecation")
	public ItemStack get(int num) {
		try {
			if (json == null) {
				return null;
			}
			String user = json.getJSONObject(num).getString("username");
			String packageName = json.getJSONObject(num).getString("item");
			String date = json.getJSONObject(num).getString("date");
			int amount = json.getJSONObject(num).getInt("price");
			String currency = " " + json.getJSONObject(num).getString("currency");
			ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 0, (byte) 3);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "Username: " + ChatColor.GREEN + user);
			meta.setLore(Arrays.asList("", ChatColor.GOLD + "Package: " + ChatColor.GREEN + packageName, "", ChatColor.GOLD + "Date: " + ChatColor.GREEN + date, "",
							ChatColor.GOLD + "Amount: "	+ ChatColor.GREEN + amount + currency));
			item.setItemMeta(meta);
			return item;
		} catch (Exception e) {
			if (!e.getMessage().contains("not found")) {
				Log.log(e);
			}
			return null;
		}

	}
}
