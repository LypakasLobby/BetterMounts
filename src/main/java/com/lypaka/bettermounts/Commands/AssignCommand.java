package com.lypaka.bettermounts.Commands;

import com.lypaka.bettermounts.BetterMounts;
import com.lypaka.bettermounts.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

public class AssignCommand {

    public AssignCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterMountsCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("assign")
                                            .then(
                                                    Commands.argument("pokemon", StringArgumentType.word())
                                                            .suggests(BetterMountsCommand.POKEMON_SUGGESTIONS)
                                                            .then(
                                                                    Commands.argument("permission", StringArgumentType.word())
                                                                            .executes(c -> {

                                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                                    if (!PermissionHandler.hasPermission(player, "bettermounts.command.admin")) {

                                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                                        return 0;

                                                                                    }

                                                                                }

                                                                                String pokemon = StringArgumentType.getString(c, "pokemon");
                                                                                String permission = StringArgumentType.getString(c, "permission");
                                                                                ConfigGetters.pokemonPermissions.put(pokemon, permission);
                                                                                BetterMounts.configManager.getConfigNode(0, "Pokemon-Specific-Permissions").setValue(ConfigGetters.pokemonPermissions);
                                                                                BetterMounts.configManager.save();
                                                                                c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully assigned permission &e" + permission + " &ato Pokemon &e" + pokemon + "&a!"), true);
                                                                                return 1;

                                                                            })
                                                            )
                                            )
                            )
            );

        }

    }

}
