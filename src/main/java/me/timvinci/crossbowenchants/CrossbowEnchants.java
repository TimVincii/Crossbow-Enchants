package me.timvinci.crossbowenchants;

import me.timvinci.crossbowenchants.commands.CrossbowEnchantsCommands;
import me.timvinci.crossbowenchants.config.ConfigManager;
import me.timvinci.crossbowenchants.util.Reference;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.registry.DynamicRegistryManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Crossbow Enchants entrypoint class.
 */
public class CrossbowEnchants implements ModInitializer {
    public static DynamicRegistryManager dynamicRegistryManager;
    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing " + Reference.MOD_NAME + " [" + Reference.MOD_VERSION + "].");

        // Loading the config.
        ConfigManager.loadConfig();

        // Registering the commands.
        CrossbowEnchantsCommands.registerCommands();

        // Grabbing a reference to the registry manager on server startup.
        ServerLifecycleEvents.SERVER_STARTING.register(listener -> {
            dynamicRegistryManager = listener.getRegistryManager();
        });
    }
}
