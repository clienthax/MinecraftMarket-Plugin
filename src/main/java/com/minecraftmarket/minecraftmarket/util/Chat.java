package com.minecraftmarket.minecraftmarket.util;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Chat {

	private Chat() {
	}

	static Chat instance;


	File langFile;
	FileConfiguration lang;

	public String prefix = "";

	public void SetupDefaultLanguage() {
		reloadLanguage();
		prefix = ChatColor.translateAlternateColorCodes('&', getLanguage().getString("messages.prefix"));
		if (!prefix.endsWith(" ")) prefix += " ";

	}

	public FileConfiguration getLanguage() {
		return Settings.get().getLanguageFile();
	}

	public void reloadLanguage() {
		Settings.get().reloadLanguageConfig();
	}

	public String getMsg(String string) {
		return getLanguage().getString(string);
	}

	public static String center(String str) {
		return StringUtils.stripEnd(StringUtils.center(str, 80), " ");
	}
	

	public static Chat get() {
		if (instance == null) instance = new Chat();
		return instance;
	}

}
