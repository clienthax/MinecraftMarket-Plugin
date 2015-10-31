package com.minecraftmarket.minecraftmarket.util;

import com.minecraftmarket.minecraftmarket.Market;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Settings {

	private static Settings instance;
	private ConfigurationLoader<ConfigurationNode> mainFile;
	private ConfigurationLoader<ConfigurationNode> signFile;
	private ConfigurationLoader<ConfigurationNode> languageFile;
	private ConfigurationNode mainConfig;
	private ConfigurationNode languageConfig;
	private ConfigurationNode signConfig;

	public void LoadSettings() {

		mainFile = YAMLConfigurationLoader.builder().setFile(new File(Market.getPlugin().getDataFolder(), "config.yml")).build();
		signFile = YAMLConfigurationLoader.builder().setFile(new File(Market.getPlugin().getDataFolder(), "signs.yml")).build();
		languageFile = YAMLConfigurationLoader.builder().setFile(new File(Market.getPlugin().getDataFolder(), "language.yml")).build();

		reloadMainConfig();
		reloadSignDatabase();
		reloadLanguageConfig();

	}

	public void saveMainConfig() {
		try {
			mainFile.save(mainConfig);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveAll() {
		try {
			mainFile.save(mainConfig);
			signFile.save(signConfig);
			languageFile.save(languageConfig);
		} catch (IOException e) {
			e.printStackTrace();
			Log.log(e);
		}
	}

	public void saveSignDatabase() {
		try {
			signFile.save(signConfig);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveLanguageFile() {
		try {
			languageFile.save(languageConfig);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ConfigurationNode getMainConfig() {
		return mainConfig;
	}

	public ConfigurationNode getSignDatabase() {
		return signConfig;
	}

	public ConfigurationNode getLanguageFile() {
		return languageConfig;
	}

	public void reloadMainConfig() {
		try {
			mainConfig = mainFile.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reloadSignDatabase() {
		try {
			signConfig = signFile.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reloadLanguageConfig() {
		try {
			languageConfig = languageFile.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Settings get() {
		if (instance == null) instance = new Settings();
		return instance;
	}

}
