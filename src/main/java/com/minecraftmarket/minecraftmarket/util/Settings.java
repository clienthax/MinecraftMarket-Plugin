package com.minecraftmarket.minecraftmarket.util;

import com.google.common.io.Resources;
import com.minecraftmarket.minecraftmarket.Market;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.utils.HttpClientUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

public class Settings {

	private static Settings instance;
	private ConfigurationLoader<ConfigurationNode> mainFile;
	private ConfigurationLoader<ConfigurationNode> signFile;
	private ConfigurationLoader<ConfigurationNode> languageFile;
	private ConfigurationNode mainConfig;
	private ConfigurationNode languageConfig;
	private ConfigurationNode signConfig;

	public void LoadSettings() {
		try {
			File mainConfigFile = new File(Market.getPlugin().getDataFolder(), "config.yml");
			if (!mainConfigFile.exists()) {
				FileUtils.writeLines(mainConfigFile, Resources.readLines(new URL("https://raw.githubusercontent.com/clienthax/MinecraftMarket-Plugin/Sponge/src/main/resources/config.yml"), Charset.defaultCharset()));
			}
			File signsConfigFile = new File(Market.getPlugin().getDataFolder(), "signs.yml");
			if (!signsConfigFile.exists()) {
				FileUtils.writeLines(signsConfigFile, Resources.readLines(new URL("https://raw.githubusercontent.com/clienthax/MinecraftMarket-Plugin/Sponge/src/main/resources/signs.yml"), Charset.defaultCharset()));
			}
			File languageConfigFile = new File(Market.getPlugin().getDataFolder(), "language.yml");
			if (!languageConfigFile.exists()) {
				FileUtils.writeLines(languageConfigFile, Resources.readLines(new URL("https://raw.githubusercontent.com/clienthax/MinecraftMarket-Plugin/Sponge/src/main/resources/language.yml"), Charset.defaultCharset()));
			}

			mainFile = YAMLConfigurationLoader.builder().setFile(mainConfigFile).build();
			signFile = YAMLConfigurationLoader.builder().setFile(signsConfigFile).build();
			languageFile = YAMLConfigurationLoader.builder().setFile(languageConfigFile).build();

			reloadMainConfig();
			reloadSignDatabase();
			reloadLanguageConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}

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
