package com.minecraftmarket.minecraftmarket.recentgui;

import com.google.common.collect.Lists;
import com.minecraftmarket.minecraftmarket.Api;
import com.minecraftmarket.minecraftmarket.Market;
import com.minecraftmarket.minecraftmarket.json.JSONArray;
import com.minecraftmarket.minecraftmarket.json.JSONObject;
import com.minecraftmarket.minecraftmarket.util.Json;
import com.minecraftmarket.minecraftmarket.util.Log;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;

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

			ItemStack skull = Market.getPlugin().getGame().getRegistry().createBuilder(ItemStack.Builder.class).itemType(ItemTypes.SKULL).build();
			skull.offer(Keys.DISPLAY_NAME, Text.of(TextColors.GOLD, "Username: ", TextColors.GREEN, user));
			ArrayList<String> lore = Lists.newArrayList();
			lore.add("");
			lore.add(TextColors.GOLD + "Package: " + TextColors.GREEN + packageName);
			lore.add("");
			lore.add(TextColors.GOLD + "Date: " + TextColors.GREEN + date);
			lore.add("");
			lore.add(TextColors.GOLD + "Amount: " + TextColors.GREEN + amount + currency);
			skull.toContainer().set(Keys.ITEM_LORE.getQuery(), lore);
			GameProfile gameProfile = Market.getPlugin().getGame().getServiceManager().provide(UserStorageService.class).get().get(user).get().getProfile();
			skull.toContainer().set(Keys.REPRESENTED_PLAYER, gameProfile);
			return skull;
		} catch (Exception e) {
			if (!e.getMessage().contains("not found")) {
				Log.log(e);
			}
			return null;
		}

	}
}
