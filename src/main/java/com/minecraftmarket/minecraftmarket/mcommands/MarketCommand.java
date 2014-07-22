package com.minecraftmarket.minecraftmarket.mcommands;

import org.bukkit.command.CommandSender;

import com.minecraftmarket.minecraftmarket.util.Chat;

public abstract class MarketCommand {

	private String command;
	private String description;
	private String args;
	private String permission;

	public MarketCommand(String command, String description, String args, String permission) {
		this.command = command;
		this.description = description;
		this.args = args;
		this.permission = permission;
	}

	public String getCommand() {
		return command;
	}

	public String getDescription() {
		return description;
	}

	public String getArgs() {
		return args;
	}

	public String getPermission() {
		return permission;
	}

	public abstract void run(CommandSender sender, String[] args);

	public String getMsg(String string) {
		return Chat.get().getLanguage().getString(string);
	}
}
