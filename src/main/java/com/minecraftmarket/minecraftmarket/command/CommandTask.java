package com.minecraftmarket.minecraftmarket.command;

import com.minecraftmarket.minecraftmarket.Market;

public class CommandTask implements Runnable {

	@Override
	public void run() {
        PendingCommands.removeAll();
        FetchCommand fetchCommand = new FetchCommand();
		fetchCommand.fetchPending();
		CommandExecutor.executeAllPending();
		CommandExecutor.clean();
	}

	public static void check() {
		Market.getPlugin().getGame().getScheduler().createTaskBuilder().async().execute(new CommandTask()).submit(Market.getPlugin());
	}
}