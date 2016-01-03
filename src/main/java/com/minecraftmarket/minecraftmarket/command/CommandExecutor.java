package com.minecraftmarket.minecraftmarket.command;

import com.google.common.collect.Lists;
import com.minecraftmarket.minecraftmarket.Api;
import com.minecraftmarket.minecraftmarket.Market;
import com.minecraftmarket.minecraftmarket.json.JSONException;
import com.minecraftmarket.minecraftmarket.util.Json;
import com.minecraftmarket.minecraftmarket.util.Log;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.type.OrderedInventory;

import java.util.List;
import java.util.Optional;

public class CommandExecutor implements Runnable {

	private String username;
	private Optional<Player> player;
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

		player = Market.getPlugin().getGame().getServer().getPlayer(username);
	}

	public boolean hasRequiredSlots() {
		if (slots <= 0) {
			return true;
		} else {
			if (!isPlayerOnline()) {
				return false;
			}
			int available = 0;
			for(int i = 0; i < player.get().getInventory().size(); i++) {
				if(!((OrderedInventory) player.get().getInventory()).peek(SlotIndex.of(i)).isPresent())
					available++;
			}

			return available >= slots;
		}
	}

	public boolean isPlayerOnline() {
		if (online) {
			player = Market.getPlugin().getGame().getServer().getPlayer(username);
			return player.isPresent();
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
		executed.forEach(PendingCommands::remove);
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
		Market.getPlugin().getGame().getScheduler().createTaskBuilder().async().delayTicks(delay * 20L).execute(this).submit(Market.getPlugin());
	}

	public void setPending() {
		new PendingCommands(id, command, username, slots, delay, online);
	}

	@Override
	public void run() {
		Log.log("Executing \"/" + command + "\" on behalf of " + username);
		Market.getPlugin().getGame().getCommandManager().process(Market.getPlugin().getGame().getServer().getConsole(), command);
	}

}
