package com.minecraftmarket.minecraftmarket.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.minecraftmarket.minecraftmarket.Market;

public class Log {

	private static Logger log;

	private static boolean debug = false;

	static {
		log = Market.getPlugin().getLogger();
	}

	public static void log(Level lvl, String msg) {
		log.log(lvl, msg);
	}

	public static void log(String msg) {
		log(Level.INFO, msg);
	}

	public static void log(Exception e) {
		log(Level.WARNING, e.getMessage());
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
