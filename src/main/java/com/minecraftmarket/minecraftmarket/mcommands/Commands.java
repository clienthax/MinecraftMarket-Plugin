package com.minecraftmarket.minecraftmarket.mcommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.minecraftmarket.minecraftmarket.Market;
import com.minecraftmarket.minecraftmarket.util.Chat;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class Commands implements CommandCallable {

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
	public CommandResult process(CommandSource source, String arguments) throws CommandException {
		String[] split = arguments.split(" ");
		if(split.length == 1 && split[0].equals(""))
			split = new String[0];
		onCommand(source, split);

		return CommandResult.empty();
	}

	public boolean onCommand(CommandSource sender, String[] args) {
		if (args.length == 0) {
			sendHelp(sender);
			return true;
		}
		if (args[0].equalsIgnoreCase("help")) {
			sendHelp(sender);
			return true;
		}
		ArrayList<String> a = new ArrayList<>(Arrays.asList(args));
		a.remove(0);

		for (MarketCommand mc : commands) {
			if (mc.getCommand().equalsIgnoreCase(args[0])) {
				if (!sender.hasPermission(mc.getPermission())) {
					sendMSG(sender, TextColors.DARK_RED, getMsg("messages", "no-permissions"));
					return true;
				}
				try {
					mc.run(sender, a.toArray(new String[a.size()]));
					return true;
				} catch (Exception e) {
					sendMSG(sender, Chat.get().prefix, TextColors.RED, " An error has occurred, use \"/mm help\" to view help");
					e.printStackTrace();
					return true;
				}
			}

		}
		sendMSG(sender, TextColors.RED, "Unknown command! use \"/mm help\" to view help");
		return true;
	}

	private void sendMSG(CommandSource sender, Object... msgs) {
		sender.sendMessage(Text.of(msgs));
	}

	private void sendHelp(CommandSource sender) {
		sendMSG(sender, TextColors.WHITE,"---------------- ",TextColors.DARK_GREEN,"MinecraftMarket Help ",TextColors.WHITE,"-----------------");
		sendMSG(sender, TextColors.GOLD, Market.getPlugin().getShopCommand(), " - ", TextColors.WHITE, "Show In-Game market to player");
		commands.stream().filter(mc -> !mc.getPermission().equals("") && sender.hasPermission(mc.getPermission())).forEach(mc -> sendMSG(sender, TextColors.GOLD, "/MM " + mc.getCommand() + " " + mc.getArgs() + " - ", TextColors.WHITE, mc.getDescription()));
		sendMSG(sender, TextColors.WHITE, "----------------------------------------------------");
	}

	private String getMsg(String... string) {
		ConfigurationNode node = Chat.get().getLanguage();
		for(String subnode : string) {
			node = node.getNode(subnode);
		}
		return node.getString();
	}

	@Override
	public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
		return new ArrayList<>();
	}

	@Override
	public boolean testPermission(CommandSource source) {
		return true;
	}

	@Override
	public Optional<? extends Text> getShortDescription(CommandSource source) {
		return Optional.empty();
	}

	@Override
	public Optional<? extends Text> getHelp(CommandSource source) {
		return Optional.empty();
	}

	@Override
	public Text getUsage(CommandSource source) {
		return Text.of("/mm");
	}

}
