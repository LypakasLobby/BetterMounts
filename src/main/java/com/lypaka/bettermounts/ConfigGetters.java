package com.lypaka.bettermounts;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.Map;

public class ConfigGetters {

    public static String mainPermission;
    public static String message;
    public static Map<String, String> pokemonPermissions;

    public static void load() throws ObjectMappingException {

        mainPermission = BetterMounts.configManager.getConfigNode(0, "Main-Permission").getString();
        message = BetterMounts.configManager.getConfigNode(0, "Message").getString();
        pokemonPermissions = BetterMounts.configManager.getConfigNode(0, "Pokemon-Specific-Permissions").getValue(new TypeToken<Map<String, String>>() {});

    }

}
