package com.lypaka.bettermounts.Commands;

import com.lypaka.bettermounts.BetterMounts;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.Arrays;
import java.util.List;

/**
 * FUCK Brigadier
 */
@Mod.EventBusSubscriber(modid = BetterMounts.MOD_ID)
public class BetterMountsCommand {

    public static List<String> ALIASES = Arrays.asList("bettermounts", "mounts", "bmounts");
    public static final SuggestionProvider<CommandSource> POKEMON_SUGGESTIONS = (context, builder) ->
            ISuggestionProvider.suggest(PixelmonSpecies.getFormattedEnglishNameSet(), builder);

    @SubscribeEvent
    public static void onCommandRegistration (RegisterCommandsEvent event) {

        new AssignCommand(event.getDispatcher());
        new ReloadCommand(event.getDispatcher());
        new RemoveCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());

    }

}
