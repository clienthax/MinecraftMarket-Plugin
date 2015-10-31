package com.minecraftmarket.minecraftmarket.util;

import ninja.leaping.configurate.ConfigurationNode;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.text.format.TextColors;

import java.io.File;

public class Chat {

	private Chat() {
	}

	static Chat instance;

	public String prefix = "";

	public void SetupDefaultLanguage() {
		reloadLanguage();
		//TODO
		// prefix = TextColors.translateAlternateColorCodes('&', getLanguage().getString("messages.prefix"));
		if (!prefix.endsWith(" ")) prefix += " ";

	}

	public ConfigurationNode getLanguage() {
		return Settings.get().getLanguageFile();
	}

	public void reloadLanguage() {
		Settings.get().reloadLanguageConfig();
	}

	public String getMsg(String string) {
		return getLanguage().getNode(string).getString();
	}

	public static String center(String str) {
		return StringUtils.stripEnd(StringUtils.center(str, 80), " ");
	}
	

	public static Chat get() {
		if (instance == null) instance = new Chat();
		return instance;
	}

}
