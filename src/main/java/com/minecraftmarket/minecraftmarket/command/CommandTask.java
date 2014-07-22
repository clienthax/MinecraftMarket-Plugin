package com.minecraftmarket.minecraftmarket.command;

import org.bukkit.scheduler.BukkitRunnable;

import com.minecraftmarket.minecraftmarket.Market;

public class CommandTask extends BukkitRunnable {

	Market plugin = Market.getPlugin();

	@Override
	public void run() {
        PendingCommands.removeAll();
        FetchCommand fetchCommand = new FetchCommand();
		fetchCommand.fetchPending();
		CommandExecutor.executeAllPending();
		CommandExecutor.clean();
	}

	public static void check() {
		new CommandTask().runTaskAsynchronously(Market.getPlugin());
	}
}