package com.minecraftmarket.minecraftmarket.util;

import com.minecraftmarket.minecraftmarket.Market;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Settings {

	private static Settings instance;
	private File signFile;
	private File languageFile;
	private FileConfiguration language;
	private FileConfiguration sign;

	private Market plugin = Market.getPlugin();

	public void LoadSettings() {
		signFile = new File(Market.getPlugin().getDataFolder(), "signs.yml");
		languageFile = new File(Market.getPlugin().getDataFolder(), "language.yml");

		Market.getPlugin().saveDefaultConfig();
		CreateFile("signs.yml", signFile, false, true);
		CreateFile("language.yml", languageFile, false, true);

		reloadConfig();
		reloadSignDatabase();
		reloadLanguageConfig();

	}

	public void saveConfig() {
		Market.getPlugin().saveConfig();
	}

	public void saveAll() {
		try {
			Market.getPlugin().saveConfig();
			sign.save(signFile);
			language.save(languageFile);
		} catch (IOException e) {
			Log.log(e);
		}
	}

	public void saveSignDatabase() {
		try {
			sign.save(signFile);
		} catch (IOException e) {
		}
	}

	public void saveLanguageFile() {
		try {
			language.save(languageFile);
		} catch (IOException e) {
		}
	}

	public FileConfiguration getConfig() {
		return Market.getPlugin().getConfig();
	}

	public FileConfiguration getSignDatabase() {
		return sign;
	}

	public FileConfiguration getLanguageFile() {
		return language;
	}

	public void reloadConfig() {
		Market.getPlugin().reloadConfig();
	}

	public void reloadSignDatabase() {
		sign = YamlConfiguration.loadConfiguration(signFile);
	}

	public void reloadLanguageConfig() {
		language = YamlConfiguration.loadConfiguration(languageFile);
	}

	private void CreateFile(String fileName, File file, boolean replace, boolean copyFromLib) {
		try {
			if (copyFromLib) {
				if (!replace && file.exists()) return;
				plugin.saveResource(fileName, replace);
				return;
			}
			if (file.exists()) {
				if (replace) {
					file.createNewFile();
					return;
				}
				return;
			}
			file.createNewFile();

		} catch (IOException e) {
			Market.getPlugin().getLogger().warning("Failed to create " + fileName);
			Log.log(e);
		}
	}

	public static Settings get() {
		if (instance == null) instance = new Settings();
		return instance;
	}

}
