package com.minecraftmarket.minecraftmarket.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;
import com.minecraftmarket.minecraftmarket.util.Log;

public class PendingCommands {

	private static List<PendingCommands> pending = Lists.newArrayList();
	private int id;
	private String command;
	private String username;
	private Player player;
	private int slots;
	private int delay;
	private boolean online;

	public PendingCommands(int id, String command, String username, int slots, int delay, boolean online) {
		this.id = id;
		this.command = command;
		this.username = username;
		this.player = Bukkit.getServer().getPlayerExact(username);
		this.slots = slots;
		this.online = online;
		this.delay = delay;

		if (!containsPending(id)) {
			pending.add(this);
		}
	}

	public int getId() {
		return id;
	}

	public Player getPlayer() {
		return player;
	}

	public boolean isOnline() {
		return online;
	}

	public String getUsername() {
		return username;
	}

	public int getDelay() {
		return delay;
	}

	public String getCommand() {
		return command;
	}

	public int getSlots() {
		return slots;
	}

	public static List<PendingCommands> getAll() {
		return pending;
	}

	public static void remove(int id) {
		try {
			pending.remove(getById(id));
		} catch (Exception e) {
			Log.log(e);
		}
	}

	public static PendingCommands getById(int id) {
		for (PendingCommands pc : getAll()) {
			if (pc.getId() == id) {
				return pc;
			}
		}
		return null;
	}

    public static void removeAll() {
        pending.clear();
    }

	public static boolean containsPending(int id) {
		return getById(id) != null;
	}

}
