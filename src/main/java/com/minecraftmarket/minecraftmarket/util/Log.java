package com.minecraftmarket.minecraftmarket.util;

import com.minecraftmarket.minecraftmarket.Market;
import org.slf4j.Logger;

public class Log {

	private static Logger log;

	private static boolean debug = false;

	static {
		log = Market.getPlugin().getLogger();
	}

	public static void log(String msg) {
		log.info(msg);
	}

	public static void log(Exception e) {
		log.warn(e.getMessage());
		if (debug) e.printStackTrace();
	}

	public static void debug(String msg) {
		if (debug) log("[DEBUG] " + msg);
	}

	public static void log(String log, String debug) {
		log(log);
		if (Log.debug) log(debug);
	}

	public static void response(String name, String s) {
		if (debug) {
			log("   ------------- " + " MinecraftMarket Debug " + name + " -------------    ");
			log("   " + s);
			log("   --------------------------------------------------------------------   ");
			log(" ");
		}

	}

	public static void setDebugging(boolean debug) {
		Log.debug = debug;
	}

}
