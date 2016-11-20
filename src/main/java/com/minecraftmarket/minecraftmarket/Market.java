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
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;

import java.io.File;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;

@Plugin(name = "MinecraftMarket", id = "minecraftmarket", version = Market.version)
public class Market {
	
	private Long interval;
	private String shopCommand;
	private boolean update;
	private boolean isBoardEnabled;
	private boolean isSignEnabled;
	private boolean isGuiEnabled;
	private static Market plugin;
	private CommandTask commandTask;
	private SignUpdate signUpdate;
        private boolean requireOnline;
        private String color;
	@Inject
	public Game game;
	@Inject
	private org.slf4j.Logger logger;
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
                this.requireOnline = config.getNode("Require-Player-Online").getBoolean(true);
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
		CommandManager commandService = getGame().getCommandManager();
		commandService.register(this, new Commands(), "mm");
	}

	private void saveDefaultSettings() {
		Settings.get().LoadSettings();
	}

	private void stopTasks() {
		try {
			SignUpdate.task.cancel();
			Market.getPlugin().getGame().getScheduler().getScheduledTasks(this).forEach(Task::cancel);
		} catch (Exception e) {
			Log.log(e);
		}
	}

	public File getDataFolder() {
		File file = new File("./config/minecraftmarket/");
		file.mkdirs();
		return file;
	}
        
        public static Market getPlugin(){
            return plugin;
        }
        
        public Logger getLogger(){
            return logger;
        }
        
        public Game getGame(){
            return Sponge.getGame();
        }
        
        public String getShopCommand(){
            return this.shopCommand;
        }
        
        public boolean isGuiEnabled(){
            return this.isGuiEnabled;
        }
        
        public boolean isSignEnabled(){
            return this.isSignEnabled;
        }
        
        public static String getVersion(){
            return version;
        }
        
        public Long getInterval(){
            return interval;
        }
        
        public boolean requireOnline(){
            return requireOnline;
        }

}
