package com.lypaka.bettermounts.Listeners;

import com.lypaka.bettermounts.BetterMounts;
import com.pixelmonmod.pixelmon.Pixelmon;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = BetterMounts.MOD_ID)
public class ServerStartingListener {

    @SubscribeEvent
    public static void onServerStarting (FMLServerStartingEvent event) {

        Pixelmon.EVENT_BUS.register(new MountListener());
        MinecraftForge.EVENT_BUS.register(new InteractListener());

    }

}
