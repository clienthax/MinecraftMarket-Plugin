package com.minecraftmarket.minecraftmarket.mcommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.Lists;
import com.minecraftmarket.minecraftmarket.Market;
import com.minecraftmarket.minecraftmarket.util.Chat;

public class Commands implements CommandExecutor {

	private static List<MarketCommand> commands = Lists.newArrayList();

	public Commands() {
		commands.add(new Apikey());
		commands.add(new Check());
		commands.add(new Reload());
		commands.add(new Recent());
		commands.add(new Signs());
		commands.add(new Version());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length == 0) {
			sendHelp(sender);
			return true;
		}
		if (args[0].equalsIgnoreCase("help")) {
			sendHelp(sender);
			return true;
		}
		ArrayList<String> a = new ArrayList<String>(Arrays.asList(args));
		a.remove(0);

		for (MarketCommand mc : commands) {
			if (mc.getCommand().equalsIgnoreCase(args[0])) {
				if (!sender.hasPermission(mc.getPermission())) {
					sendMSG(sender, ChatColor.DARK_RED + getMsg("messages.no-permissions"));
					return true;
				}
				try {
					mc.run(sender, a.toArray(new String[a.size()]));
					return true;
				} catch (Exception e) {
					sender.sendMessage(Chat.get().prefix + ChatColor.RED + " An error has occurred, use \"/mm help\" to view help");
					e.printStackTrace();
					return true;
				}
			}

		}
		sendMSG(sender, ChatColor.RED + "Unknown command! use \"/mm help\" to view help");
		return true;
	}

	private void sendMSG(CommandSender sender, String msg) {
		sender.sendMessage(msg);
	}

	private void sendHelp(CommandSender sender) {
		sendMSG(sender, ChatColor.WHITE + "---------------- " + ChatColor.DARK_GREEN + "MinecraftMarket Help " + ChatColor.WHITE + "-----------------");
		sendMSG(sender, ChatColor.GOLD + Market.getPlugin().getShopCommand() + " - " + ChatColor.WHITE + "Show In-Game market to player");
		for (MarketCommand mc : commands) {
			if (mc.getPermission() != "" || sender.hasPermission(mc.getPermission())) {
				sendMSG(sender, ChatColor.GOLD + "/MM " + mc.getCommand() + " " + mc.getArgs() + " - " + ChatColor.WHITE + mc.getDescription());

			}
		}
		sendMSG(sender, ChatColor.WHITE + "----------------------------------------------------");
	}

	private String getMsg(String string) {
		return Chat.get().getLanguage().getString(string);
	}

}
