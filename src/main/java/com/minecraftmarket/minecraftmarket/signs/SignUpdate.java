package com.minecraftmarket.minecraftmarket.signs;

import com.minecraftmarket.minecraftmarket.Market;
import org.spongepowered.api.scheduler.Task;

public class SignUpdate implements Runnable {
	
	public static Task task;
	private boolean first;

	public void startSignTask() {
		first = true;
		Signs.getSigns().updateJson();		
		if (task != null) task.cancel();
		task = Market.getPlugin().getGame().getScheduler().createTaskBuilder().delayTicks(600L).intervalTicks(Market.getPlugin().getInterval() * 20L).execute(this).submit(Market.getPlugin());
	}
    
	@Override
	public void run() {
		
		if (first) {
			Signs.getSigns().setup();
			first = false;
		}
		Signs.getSigns().updateJson();
		SignData.updateAllSigns();
	}

}