package com.minecraftmarket.minecraftmarket.util;

public class Init {

	public static void start() {
		Settings.get().reloadConfig();
		Settings.get().reloadLanguageConfig();
		try {
			String ver = Settings.get().getConfig().getString("version");
			if (ver == null) {
				upgrade();
				return;
			}
			if (!ver.equalsIgnoreCase("1.8.4")) {
				upgrade();
				return;
			}
			

		} catch (Exception e) {
			Log.log(e);
		}
	}

	private static void upgrade() {
		Log.log("------------------- beginning initialization to MinecraftMarket v1.8.4 -----------------");
		Settings.get().getConfig().set("version", "1.8.4");
		Log.log("   ~ Changing version");
		Settings.get().saveConfig();
		Log.log("   * Saving files..");
		Settings.get().reloadConfig();
		Log.log("------------------------------ initialization successful ------------------------------");
	}
	
}
