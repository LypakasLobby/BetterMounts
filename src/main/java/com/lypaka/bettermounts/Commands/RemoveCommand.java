package com.lypaka.bettermounts.Commands;

import com.lypaka.bettermounts.BetterMounts;
import com.lypaka.bettermounts.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.ServerPlayerEntity;

public class RemoveCommand {

    private static final SuggestionProvider<CommandSource> POKEMON_SUGGESTIONS = (context, builder) ->
            ISuggestionProvider.suggest(ConfigGetters.pokemonPermissions.keySet(), builder);

    public RemoveCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterMountsCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("remove")
                                            .then(
                                                    Commands.argument("pokemon", StringArgumentType.word())
                                                            .suggests(POKEMON_SUGGESTIONS)
                                                            .executes(c -> {

                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                    if (!PermissionHandler.hasPermission(player, "bettermounts.command.admin")) {

                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                        return 0;

                                                                    }

                                                                }

                                                                String pokemon = StringArgumentType.getString(c, "pokemon");
                                                                ConfigGetters.pokemonPermissions.entrySet().removeIf(entry -> entry.getKey().equalsIgnoreCase(pokemon));
                                                                BetterMounts.configManager.getConfigNode(0, "Pokemon-Specific-Permissions").setValue(ConfigGetters.pokemonPermissions);
                                                                BetterMounts.configManager.save();
                                                                c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully updated the Pokemon permissions map!"), true);
                                                                return 1;

                                                            })
                                            )
                            )
            );

        }

    }

}
