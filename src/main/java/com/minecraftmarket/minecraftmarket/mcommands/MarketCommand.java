package com.minecraftmarket.minecraftmarket.mcommands;

import com.minecraftmarket.minecraftmarket.util.Chat;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.command.CommandSource;

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

	public abstract void run(CommandSource sender, String[] args);

	public String getMsg(String... string) {
		ConfigurationNode node = Chat.get().getLanguage();
		for(String subnode : string) {
			node = node.getNode(subnode);
		}
		return node.getString();
	}
}
