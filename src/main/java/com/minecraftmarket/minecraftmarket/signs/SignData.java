package com.minecraftmarket.minecraftmarket.signs;

import com.google.common.collect.Lists;
import com.minecraftmarket.minecraftmarket.Market;
import com.minecraftmarket.minecraftmarket.json.JSONException;
import com.minecraftmarket.minecraftmarket.util.Chat;
import com.minecraftmarket.minecraftmarket.util.Settings;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class SignData {

	private static List<SignData> signs = Lists.newArrayList();
	private Location<World> location;
	private Integer number;
	private String date;
	private String username;
	private String item; // Package name

	public SignData(Location<World> location, int number) {
		this.location = location;
		this.number = number;
		create();
	}

	public void create() {
		SignData signData = null;
		for (SignData sd : signs) {
			if (sd.getLocation() == this.location) {
				signData = sd;
			}
		}
		if (signData != null) {
			signData.remove();
		}

		signs.add(this);
		if (isSign()) {
			saveAllToConfig();
			try {
				update();
			} catch (JSONException e) {
			}
		}
	}

	public void update() throws JSONException {
		this.username = Signs.getJsonArray().getJSONObject(number).getString("username");
		this.item = Signs.getJsonArray().getJSONObject(number).getString("item");
		this.date = Signs.getJsonArray().getJSONObject(number).getString("date");
		this.date = date.split(" ")[0];

		Text[] texts = {Text.of(TextStyles.UNDERLINE, getMsg("signs.header")), Text.of(username), Text.of(item), Text.of(date) };
		ArrayList<Text> list = Lists.newArrayList(texts);
		getLocation().offer(Keys.SIGN_LINES, list);
		updateHead();
	}

	public boolean isSign() {
		return this.location.getBlockType() == BlockTypes.STANDING_SIGN || this.getLocation().getBlockType() == BlockTypes.WALL_SIGN;
	}

	public void updateHead() {
		Optional<Location<World>> skullLocation = getSkull();
		if (skullLocation.isPresent()) {
			GameProfile owner = Market.getPlugin().getGame().getServiceManager().provide(UserStorageService.class).get().get(username).get().getProfile();
			skullLocation.get().offer(Keys.REPRESENTED_PLAYER, owner);
		}
	}

	public void remove() {
		signs.remove(this);
		saveAllToConfig();
	}

	public static void saveAllToConfig() {
		List<String> listSigns = Lists.newArrayList();
		for (SignData sd : signs) {
			Location<World> loc = sd.getLocation();
			String str = loc.getExtent().getUniqueId() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ() + ":" + sd.getNumber();
			listSigns.add(str);
		}
		Settings.get().getSignDatabase().getNode("recent").setValue(listSigns);
		Settings.get().saveSignDatabase();
	}

	public static void updateAllSigns() {
		List<SignData> toRemove = Lists.newArrayList();
		for (SignData sd : signs) {
				if (sd.isSign()) {
					try {
						sd.update();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					toRemove.add(sd);
				}
		}
		for (SignData sd : toRemove) {
			sd.remove();
		}
	}

	public static SignData getSignByLocation(Location location) {
		for (SignData sd : signs) {
			if (location.getBlockX() == sd.getLocation().getBlockX() && location.getBlockY() == sd.getLocation().getBlockY() && location.getBlockZ() == sd.getLocation().getBlockZ()) {
				return sd;
			}
		}
		return null;
	}

	public Integer getPlace() {
		return this.number;
	}

	public String getUsername() {
		return this.username;
	}

	public String getItem() {
		return this.item;
	}

	public Location<World> getLocation() {
		return this.location;
	}

	public Integer getNumber() {
		return this.number;
	}

	private Optional<Location<World>> getSkull() {
		Location<World> aboveSign = location.add(0, 1, 0);
		if(aboveSign.getBlockType() == BlockTypes.SKULL)
			return Optional.of(aboveSign);
		return Optional.empty();
	}

	private String getMsg(String string) {
		String[] split = string.split(Pattern.quote("."));
		return Chat.get().getLanguage().getNode((Object[])split).getString();
	}

}
