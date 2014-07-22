package com.minecraftmarket.minecraftmarket.signs;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.minecraftmarket.minecraftmarket.Market;

public class SignUpdate extends BukkitRunnable {
	
	private static BukkitTask task;
	private boolean first;

	public void startSignTask() {
		first = true;
		Signs.getSigns().updateJson();		
		if (task != null) task.cancel();
		task = this.runTaskTimer(Market.getPlugin(), 600L, Market.getPlugin().getInterval() * 20);
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
	
	public class SignJson extends BukkitRunnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
		
	}
}