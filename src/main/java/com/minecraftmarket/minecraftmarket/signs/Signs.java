package com.minecraftmarket.minecraftmarket.signs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import com.minecraftmarket.minecraftmarket.json.JSONArray;
import com.minecraftmarket.minecraftmarket.json.JSONObject;
import com.minecraftmarket.minecraftmarket.Api;
import com.minecraftmarket.minecraftmarket.Market;
import com.minecraftmarket.minecraftmarket.util.Json;
import com.minecraftmarket.minecraftmarket.util.Log;
import com.minecraftmarket.minecraftmarket.util.Settings;

public class Signs implements Listener {

	public static final BlockFace[] BLOCKFACES = { BlockFace.SELF, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };

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
		new JsonUpdate().runTaskAsynchronously(Market.getPlugin());
	}
	
	public void setup(){
		for (String signInfo : Settings.get().getSignDatabase().getStringList("recent")) {
			new SignData(convertLocation(signInfo), getNumber(signInfo));
		}
	}

	public Location convertLocation(String str) {
		String[] args = str.split(":");
		World world = Bukkit.getServer().getWorld(args[0]);
		double x = convertDouble(args[1]);
		double y = convertDouble(args[2]);
		double z = convertDouble(args[3]);
		Location loc = new Location(world, x, y, z);
		return loc;
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
	
	class JsonUpdate extends BukkitRunnable {

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