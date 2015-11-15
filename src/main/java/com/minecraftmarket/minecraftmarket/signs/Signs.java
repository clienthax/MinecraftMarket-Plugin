package com.minecraftmarket.minecraftmarket.signs;

import com.google.common.reflect.TypeToken;
import com.minecraftmarket.minecraftmarket.Api;
import com.minecraftmarket.minecraftmarket.Market;
import com.minecraftmarket.minecraftmarket.json.JSONArray;
import com.minecraftmarket.minecraftmarket.json.JSONObject;
import com.minecraftmarket.minecraftmarket.util.Json;
import com.minecraftmarket.minecraftmarket.util.Log;
import com.minecraftmarket.minecraftmarket.util.Settings;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.Optional;

public class Signs {

	private static JSONArray json;

	public static Signs getSigns() {
		if (instance == null) instance = new Signs();
		return instance;
	}

	public static JSONArray getJsonArray() {
		return json;
	}

	private static Signs instance;

	public void updateJson() {
		Market.getPlugin().getGame().getScheduler().createTaskBuilder().async().execute(new JsonUpdate()).submit(Market.getPlugin());
	}
	
	public void setup(){
		try {
			List<String> recents = Settings.get().getSignDatabase().getNode("recent").getList(new TypeToken<String>() {});
			for (String signInfo : recents) {
				Optional<Location<World>> worldLocation = convertLocation(signInfo);
				if (worldLocation.isPresent())
					new SignData(worldLocation.get(), getNumber(signInfo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Optional<Location<World>> convertLocation(String str) {
		String[] args = str.split(":");
		Optional<World> world = Market.getPlugin().getGame().getServer().getWorld(args[0]);
		if(!world.isPresent())
			return Optional.empty();
		double x = convertDouble(args[1]);
		double y = convertDouble(args[2]);
		double z = convertDouble(args[3]);
		Location<World> loc = new Location(world.get(), x, y, z);
		return Optional.of(loc);
	}

	public int getNumber(String s) {
		try {
			return Integer.parseInt(s.split(":")[4]);
		} catch (Exception e) {
			return 0;
		}
	}

	private Double convertDouble(String str) {
		return Double.parseDouble(str);
	}
	
	class JsonUpdate implements Runnable {

		@Override
		public void run() {
			try {
				String recent = Json.getJSON(Api.getUrl() + "/recentdonor");
				Log.response("Recent payment", recent);
				if (!Json.isJson(recent)) {
					return;
				}
				JSONObject jsono = new JSONObject(recent);
				json = jsono.optJSONArray("result");
			} catch (Exception e) {
				Log.log(e);
			}
			
		}
		
	}

}