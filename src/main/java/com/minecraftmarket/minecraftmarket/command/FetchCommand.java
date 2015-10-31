package com.minecraftmarket.minecraftmarket.command;

import com.minecraftmarket.minecraftmarket.json.JSONArray;
import com.minecraftmarket.minecraftmarket.json.JSONException;
import com.minecraftmarket.minecraftmarket.json.JSONObject;

import com.minecraftmarket.minecraftmarket.Api;
import com.minecraftmarket.minecraftmarket.Market;
import com.minecraftmarket.minecraftmarket.util.Json;
import com.minecraftmarket.minecraftmarket.util.Log;

public class FetchCommand {

	public void fetchPending() {
        String pending = "";
		try {
			pending = Json.getJSON(Api.getUrl() + "/pending");
			Log.response("Pending", pending);

			if (Json.isJson(pending)) {
				JSONArray pendingArray = new JSONObject(pending).optJSONArray("result");
				for (int i = 0; i < pendingArray.length(); i++) {

					String username = pendingArray.getJSONObject(i).getString("username");
					JSONArray commands = pendingArray.getJSONObject(i).getJSONArray("commands");

					for (int c = 0; c < commands.length(); c++) {
						String command = commands.getJSONObject(c).getString("command");
						int id = commands.getJSONObject(c).getInt("id");
						int delay = commands.getJSONObject(c).getInt("delay");
						int slots = commands.getJSONObject(c).getInt("slots");
						boolean online = forBoolean(commands.getJSONObject(c).getInt("online"));
						new PendingCommands(id, command, username, slots, delay, online);
					}
				}
			}
			
			String expiry = Json.getJSON(Api.getUrl() + "/expiry");
			Log.response("Expiry", expiry);

			if (Json.isJson(expiry)) {
				JSONArray expiryArray = new JSONObject(expiry).optJSONArray("result");
				for (int i = 0; i < expiryArray.length(); i++) {

					String username = expiryArray.getJSONObject(i).getString("username");
					JSONArray commands = expiryArray.getJSONObject(i).getJSONArray("commands");

					for (int c = 0; c < commands.length(); c++) {
						String command = commands.getJSONObject(c).getString("command");
						int id = commands.getJSONObject(c).getInt("id");
						int delay = commands.getJSONObject(c).getInt("delay");
						int slots = commands.getJSONObject(c).getInt("slots");
						boolean online = forBoolean(commands.getJSONObject(c).getInt("online"));
						new PendingCommands(id, command, username, slots, delay, online);
					}
				}
			}
		} catch (JSONException e) {
			Log.log(e);
		}
	}

	private boolean forBoolean(int num) {
		return num != 1;
	}
}
