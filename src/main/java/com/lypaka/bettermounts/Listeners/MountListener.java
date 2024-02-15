package com.lypaka.bettermounts.Listeners;

import com.lypaka.bettermounts.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.pixelmonmod.pixelmon.api.events.RidePokemonEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;

public class MountListener {

    @SubscribeEvent
    public void onMount (RidePokemonEvent event) {

        ServerPlayerEntity player = event.player;
        PixelmonEntity pokemon = event.pixelmon;

        if (!ConfigGetters.mainPermission.equalsIgnoreCase("")) {

            if (!PermissionHandler.hasPermission(player, ConfigGetters.mainPermission)) {

                event.setCanceled(true);
                player.sendMessage(FancyText.getFormattedText(ConfigGetters.message), player.getUniqueID());
                return;

            }

        }
        for (Map.Entry<String, String> entry : ConfigGetters.pokemonPermissions.entrySet()) {

            if (entry.getKey().equalsIgnoreCase(pokemon.getLocalizedName())) {

                if (!PermissionHandler.hasPermission(player, entry.getValue())) {

                    event.setCanceled(true);
                    player.sendMessage(FancyText.getFormattedText(ConfigGetters.message), player.getUniqueID());
                    break;

                }

            }

        }

    }

}
