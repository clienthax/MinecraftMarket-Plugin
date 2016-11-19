package com.minecraftmarket.minecraftmarket.signs;

import com.minecraftmarket.minecraftmarket.Market;
import com.minecraftmarket.minecraftmarket.json.JSONException;
import com.minecraftmarket.minecraftmarket.util.Chat;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class SignListener {

	public Market plugin = Market.getPlugin();
	private Chat chat = Chat.get();
	
	@Listener
	public void onBlockPlace(ChangeBlockEvent.Place event){
		if(event.getTransactions().get(0).getFinal().getState().getType() == BlockTypes.SKULL) {
			SignData.updateAllSigns();
		}
	}

	@Listener
	public void onSignChange(final ChangeSignEvent event) {
		if (event.getCause().first(Player.class).isPresent()) {
			Player player = event.getCause().first(Player.class).get();
			org.spongepowered.api.data.manipulator.mutable.tileentity.SignData signDataSponge = event.getText();

			if (signDataSponge.getValue(Keys.SIGN_LINES).isPresent()) {
				String line0 = TextSerializers.PLAIN.serialize(signDataSponge.getValue(Keys.SIGN_LINES).get().get(0));
				String line1 = TextSerializers.PLAIN.serialize(signDataSponge.getValue(Keys.SIGN_LINES).get().get(1));
				String line2 = TextSerializers.PLAIN.serialize(signDataSponge.getValue(Keys.SIGN_LINES).get().get(2));
				String line3 = TextSerializers.PLAIN.serialize(signDataSponge.getValue(Keys.SIGN_LINES).get().get(3));

				if (line0.equalsIgnoreCase("[Recent]")) {

					if (!player.hasPermission("minecraftmarket.admin")) {
						player.sendMessage(Text.of(TextColors.DARK_RED, Chat.get().getLanguage().getNode("signs", "no-permissions").getString()));
						return;
					}

					int id = 0;

					try {
						id = Integer.parseInt(line1);
					} catch (NumberFormatException ex) {
						//TODO
						//event.getBlock().breakNaturally();
						player.sendMessage(Text.of(chat.prefix, TextColors.DARK_RED, "Wrong sign format"));
					}

					Location<World> loc = event.getTargetTile().getLocation();
					SignData sign = SignData.getSignByLocation(loc);

					if (sign != null) {
						sign.remove();
					}

					SignData signData;

					try {
						if (Signs.getJsonArray().getJSONObject(id - 1) != null) {
							signData = new SignData(loc, id - 1);

							try {
								signData.update();
							} catch (JSONException e) {
								e.printStackTrace();
							}
							player.sendMessage(Text.of(chat.prefix, TextColors.GREEN, Chat.get().getLanguage().getNode("signs", "created").getString()));
						}
					} catch (JSONException e1) {
						event.getTargetTile().getLocation().setBlock(BlockTypes.AIR.getDefaultState());
						player.sendMessage(Text.of(chat.prefix, TextColors.DARK_RED, "Invalid sign format! Couldn't find any purchases with id specified!"));
					}

				}
			}
		}

	}

	@Listener
	public void onSignBreak(ChangeBlockEvent.Break event) {
		if (event.getCause().first(Player.class).isPresent()) {
			Player player = event.getCause().first(Player.class).get();
			SignData sign = SignData.getSignByLocation(event.getTransactions().get(0).getFinal().getLocation().get());
			if (sign != null) {
				if (player.hasPermission("minecraftmarket.admin")) {
					sign.remove();
					player.sendMessage(Text.of(chat.prefix, TextColors.RED, "Sign removed"));
				} else {
					player.sendMessage(Text.of(TextColors.DARK_RED, Chat.get().getLanguage().getNode("signs", "no-permissions").getString()));
				}
			}
		}
	}


	
}
