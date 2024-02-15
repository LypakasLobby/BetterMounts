package com.lypaka.bettermounts.Listeners;

import com.lypaka.bettermounts.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

import java.util.Map;

public class InteractListener {

    @SubscribeEvent
    public void onPokemonInteract (PlayerInteractEvent.EntityInteract event) {

        if (event.getSide() == LogicalSide.CLIENT) return;
        if (event.getHand() == Hand.OFF_HAND) return;

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        if (player.getHeldItem(Hand.MAIN_HAND).getItem().getRegistryName().toString().contains("air")) {

            if (event.getTarget() instanceof PixelmonEntity) {

                PixelmonEntity pokemon = (PixelmonEntity) event.getTarget();
                if (pokemon.hasOwner() && pokemon.getOwnerUniqueId().toString().equalsIgnoreCase(player.getUniqueID().toString())) {

                    if (!ConfigGetters.mainPermission.equalsIgnoreCase("")) {

                        if (!PermissionHandler.hasPermission(player, ConfigGetters.mainPermission)) {

                            player.sendMessage(FancyText.getFormattedText(ConfigGetters.message), player.getUniqueID());
                            event.setCanceled(true);
                            return;

                        }

                    }
                    for (Map.Entry<String, String> entry : ConfigGetters.pokemonPermissions.entrySet()) {

                        if (entry.getKey().equalsIgnoreCase(pokemon.getPokemonName())) {

                            if (!PermissionHandler.hasPermission(player, entry.getValue())) {

                                player.sendMessage(FancyText.getFormattedText(ConfigGetters.message), player.getUniqueID());
                                event.setCanceled(true);
                                return;

                            }

                        }

                    }
                    player.startRiding(pokemon, true);

                }

            }

        }

    }

}
