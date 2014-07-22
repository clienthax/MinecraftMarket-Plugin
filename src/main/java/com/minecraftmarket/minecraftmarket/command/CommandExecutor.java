package com.minecraftmarket.minecraftmarket.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import com.minecraftmarket.minecraftmarket.json.JSONException;

import com.google.common.collect.Lists;
import com.minecraftmarket.minecraftmarket.Api;
import com.minecraftmarket.minecraftmarket.Market;
import com.minecraftmarket.minecraftmarket.util.Json;
import com.minecraftmarket.minecraftmarket.util.Log;

public class CommandExecutor extends BukkitRunnable {

	private String username;
	private Player player;
	private String command;
	private int delay;
	private int slots;
	private boolean online;
	private int id;

	private static List<Integer> executed = Lists.newArrayList();

	public CommandExecutor(int id, String username, String command, int slots, int delay, boolean online) {
		this.id = id;
		this.username = username;
		this.command = command;
		this.delay = delay;
		this.online = online;
		this.slots = slots;

		player = Bukkit.getServer().getPlayerExact(username);
	}

	public boolean hasRequiredSlots() {
		if (slots <= 0) {
			return true;
		} else {
			if (!isPlayerOnline()) {
				return false;
			}
			int available = 0;
			for (ItemStack is : player.getInventory().getContents()) {
				if (is == null) {
					available++;
				}
			}
			return available >= slots;
		}
	}

	public boolean isPlayerOnline() {
		if (online) {
			player = Bukkit.getServer().getPlayerExact(username);
			return player != null;
		}
		return true;
	}

	public void executed() {
		try {
			Json.getJSON(Json.getJSON(Api.getUrl() + "/executed/" + id));
			executed.add(id);
		} catch (JSONException e) {
			Log.log(e);
		}
	}

	public static void executeAllPending() {
		for (PendingCommands pc : PendingCommands.getAll()) {
			CommandExecutor executor = new CommandExecutor(pc.getId(), pc.getUsername(), pc.getCommand(), pc.getSlots(), pc.getDelay(), pc.isOnline());
			executor.execute();
		}
	}

	public static void clean() {
		for (int id : executed) {
			PendingCommands.remove(id);
		}
	}

	public void execute() {
		if (!isPlayerOnline()) {
			Log.debug("Setting \"/" + command + "\" as pending. Player must be online to execute this command.");
			this.setPending();
			return;
		}

		if (!hasRequiredSlots()) {
			Log.debug("Setting \"/" + command + "\" as pending. Required slots for command was not met.");
			this.setPending();
			return;
		}
		executed();
		Log.debug("Command \"/" + command + "\" will be executed in " + delay + " seconds");
		this.runTaskLaterAsynchronously(Market.getPlugin(), delay * 20);
	}

	public void setPending() {
		new PendingCommands(id, command, username, slots, delay, online);
	}

	@Override
	public void run() {
		Log.log("Executing \"/" + command + "\" on behalf of " + username);
		Market.getPlugin().getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
	}

}
