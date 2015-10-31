package com.minecraftmarket.minecraftmarket;

import com.google.inject.Inject;
import com.minecraftmarket.minecraftmarket.command.CommandTask;
import com.minecraftmarket.minecraftmarket.mcommands.Commands;
import com.minecraftmarket.minecraftmarket.recentgui.RecentListener;
import com.minecraftmarket.minecraftmarket.shop.ShopListener;
import com.minecraftmarket.minecraftmarket.shop.ShopTask;
import com.minecraftmarket.minecraftmarket.signs.SignListener;
import com.minecraftmarket.minecraftmarket.signs.SignUpdate;
import com.minecraftmarket.minecraftmarket.signs.Signs;
import com.minecraftmarket.minecraftmarket.util.Chat;
import com.minecraftmarket.minecraftmarket.util.Log;
import com.minecraftmarket.minecraftmarket.util.Settings;
import lombok.Getter;
import lombok.Setter;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.command.CommandService;

import java.io.File;

@Plugin(name = "MinecraftMarket", id = "minecraftmarket", version = Market.version)
public class Market {
	
    @Getter @Setter
	private Long interval;
    @Getter @Setter
	private String shopCommand;
	private boolean update;
    @Getter @Setter
	private boolean isBoardEnabled;
    @Getter @Setter
	private boolean isSignEnabled;
    @Getter @Setter
	private boolean isGuiEnabled;
    @Getter @Setter
	private static Market plugin;
    @Getter @Setter
	private CommandTask commandTask;
    @Getter @Setter
	private SignUpdate signUpdate;
    @Getter @Setter
    private String color;
	@Inject
	@Getter
	public Game game;
	@Inject
	@Getter
	private org.slf4j.Logger logger;
	@Getter
	public final static String version = "2.1.0";

	@Listener
	public void onServerStop(GameStoppingServerEvent event) {
		stopTasks();
	}

	@Listener
	public void onServerInit(GameInitializationEvent event) {
        plugin = this;
		try {
			registerCommands();
			saveDefaultSettings();
			registerEvents();
			reload();
			startTasks();
		} catch (Exception e) {
			e.printStackTrace();
			Log.log(e);
		}
	}

	public void reload() {
		try {
            Settings.get().reloadMainConfig();
            Settings.get().reloadLanguageConfig();
            
			loadConfigOptions();
			if (authApi()) {
				startGUI();
				startSignTasks();
			}
		} catch (Exception e) {
			Log.log(e);
		}
	}

	private void loadConfigOptions() {
		Chat.get().SetupDefaultLanguage();
        ConfigurationNode config = Settings.get().getMainConfig();
        Api.setApi(config.getNode("ApiKey").getString("Apikey here"));
		this.interval = Math.max(config.getNode("Interval").getLong(90L), 10L);
		this.isGuiEnabled = config.getNode("Enabled-GUI").getBoolean(true);
		this.shopCommand = config.getNode("Shop-Command").getString("/shop");
		this.update = config.getNode("auto-update").getBoolean(true);
		this.isSignEnabled = config.getNode("Enabled-signs").getBoolean(true);
		this.color = config.getNode("Color").getString("&0");
		Log.setDebugging(config.getNode("Debug").getBoolean(false));
	}

	private void registerEvents() {
		getGame().getEventManager().registerListeners(this, new ShopListener());
		getGame().getEventManager().registerListeners(this, new ShopCommand());
		getGame().getEventManager().registerListeners(this, new SignListener());
		getGame().getEventManager().registerListeners(this, new RecentListener());
	}

	private boolean authApi() {
		if (Api.authAPI(Api.getKey())) {
			getLogger().info("Using API Key: " + Api.getKey());
			return true;
		} else {
			getLogger().warn("Invalid API Key! Use \"/MM APIKEY <APIKEY>\" to setup your APIKEY");
			return false;
		}
	}

	private void startGUI() {
		if (isGuiEnabled) {
			game.getScheduler().createTaskBuilder().delayTicks(20).execute(new ShopTask()).submit(this);
		}
	}

	private void runCommandChecker() {
		commandTask = new CommandTask();
		game.getScheduler().createTaskBuilder().async().delayTicks(600L).intervalTicks(interval * 20L).execute(commandTask).submit(this);
	}

	private void startSignTasks() {
		if (isSignEnabled()) {
			Signs.getSigns().setup();
			signUpdate = new SignUpdate();
			signUpdate.startSignTask();
		}
	}

	private void startTasks() {
		runCommandChecker();
	}

	private void registerCommands() {
		CommandService commandService = getGame().getCommandDispatcher();
		commandService.register(this, new Commands(), "mm");
	}

	private void saveDefaultSettings() {
		Settings.get().LoadSettings();
	}

	private void stopTasks() {
		try {
			SignUpdate.task.cancel();
			Market.getPlugin().getGame().getScheduler().getScheduledTasks(this).forEach(org.spongepowered.api.service.scheduler.Task::cancel);
		} catch (Exception e) {
			Log.log(e);
		}
	}

	public File getDataFolder() {
		File file = new File("./config/minecraftmarket/");
		file.mkdirs();
		return file;
	}

}
