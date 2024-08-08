package me.timvinci.crossbowenchants;

import me.timvinci.crossbowenchants.commands.CrossbowEnchantsCommands;
import me.timvinci.crossbowenchants.config.ConfigManager;
import me.timvinci.crossbowenchants.util.Reference;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The global class of the mod.
// Responsible for calling the load of the config and for calling the registration of the commands.
public class CrossbowEnchants implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing " + Reference.MOD_NAME + " [" + Reference.MOD_VERSION + "].");
        ConfigManager.loadConfig();
        CrossbowEnchantsCommands.registerCommands();
    }
}
