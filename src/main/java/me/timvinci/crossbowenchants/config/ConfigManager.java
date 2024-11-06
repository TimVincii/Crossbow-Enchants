package me.timvinci.crossbowenchants.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.ParsingException;
import com.electronwill.nightconfig.core.io.WritingMode;
import me.timvinci.crossbowenchants.CrossbowEnchants;
import me.timvinci.crossbowenchants.util.TextStyler;
import me.timvinci.crossbowenchants.util.Reference;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import java.nio.file.Path;

public class ConfigManager {

    private static final Path CONFIG_FILE_PATH = FabricLoader.getInstance().getConfigDir().resolve("crossbowenchants.toml");
    private static final CrossbowEnchantsConfig config = new CrossbowEnchantsConfig();

    public static void loadConfig() {
        // Building the CommentedFileConfig.
        CommentedFileConfig fileConfig = buildFileConfig();
        // Trying to load the config, and catching any parsing issues.
        try {
            fileConfig.load();
        }
        catch (ParsingException exception) {
            // Adding more info to the parsing exception, then rethrowing it.
            String exceptionMessage = Reference.MOD_NAME + ": Config parsing failed, please validate the config properties in .\\config\\crossbowenchants.toml. Exception message: " + exception.getMessage();
            throw new ParsingException(exceptionMessage);
        }

        // Creating the config file if it doesn't exist or is empty.
        if (!fileConfig.getFile().exists() || fileConfig.isEmpty()) {
            fileConfig.close();
            saveConfig();
        } else {
            // Reading from the config file it if exists.
            config.setEnabled(getBooleanProperty(fileConfig, "Enabled"));
            config.setFlameEnabled(getBooleanProperty(fileConfig, "FlameEnabled"));
            config.setInfinityEnabled(getBooleanProperty(fileConfig, "InfinityEnabled"));
            config.setLootingEnabled(getBooleanProperty(fileConfig, "LootingEnabled"));
            config.setPowerEnabled(getBooleanProperty(fileConfig, "PowerEnabled"));
            config.setPunchEnabled(getBooleanProperty(fileConfig, "PunchEnabled"));
            config.setInfinityAndMendingEnabled(getBooleanProperty(fileConfig, "InfinityAndMendingEnabled"));
            config.setPiercingAndMultishotEnabled(getBooleanProperty(fileConfig, "PiercingAndMultishotEnabled"));

            fileConfig.close();
        }
    }

    public static void saveConfig() {
        // Building the CommentedFileConfig.
        CommentedFileConfig fileConfig = buildFileConfig();
        // Trying to load the config, and catching any parsing issues.
        try {
            fileConfig.load();
        }
        catch (ParsingException exception) {
            // Adding more info to the parsing exception, then rethrowing it.
            String exceptionMessage = Reference.MOD_NAME + ": Config parsing failed, please validate the config properties in .\\config\\crossbowenchants.toml. Exception message: " + exception.getMessage();
            throw new ParsingException(exceptionMessage);
        }

        // Setting the properties alongside their default values and their comments.
        fileConfig.set("Enabled", config.isEnabled());
        fileConfig.setComment("Enabled", "A toggle for the global functionality of Crossbow Enchants.");

        fileConfig.set("FlameEnabled", config.isFlameEnabled());
        fileConfig.setComment("FlameEnabled", "A toggle for the functionality of the Flame enchantment on the crossbow.");

        fileConfig.set("InfinityEnabled", config.isInfinityEnabled());
        fileConfig.setComment("InfinityEnabled", "A toggle for the functionality of the Infinity enchantment on the crossbow.");

        fileConfig.set("LootingEnabled", config.isLootingEnabled());
        fileConfig.setComment("LootingEnabled", "A toggle for the functionality of the Looting enchantment on crossbows and bows.");

        fileConfig.set("PowerEnabled", config.isPowerEnabled());
        fileConfig.setComment("PowerEnabled", "A toggle for the functionality of the Power enchantment on the crossbow.");

        fileConfig.set("PunchEnabled", config.isPunchEnabled());
        fileConfig.setComment("PunchEnabled", "A toggle for the functionality of the Punch enchantment on the crossbow.");

        fileConfig.set("InfinityAndMendingEnabled", config.isInfinityAndMendingEnabled());
        fileConfig.setComment("InfinityAndMendingEnabled", "A toggle for the compatibility of Infinity and Mending on crossbows and bows.");

        fileConfig.set("PiercingAndMultishotEnabled", config.isPiercingAndMultishotEnabled());
        fileConfig.setComment("PiercingAndMultishotEnabled", "A toggle for the compatibility of Piercing and Multishot on crossbows.");

        // Saving and closing the file config.
        fileConfig.save();
        fileConfig.close();
    }

    public static void resetConfig() {
        // Resetting the values to their default, which is 'true' in all cases.
        config.setEnabled(true);
        config.setFlameEnabled(true);
        config.setInfinityEnabled(true);
        config.setLootingEnabled(true);
        config.setPowerEnabled(true);
        config.setPunchEnabled(true);
        config.setInfinityAndMendingEnabled(true);
        config.setPiercingAndMultishotEnabled(true);
    }

    public static Text getConfigInfo() {
        // Returning a text filled with the state of every property.
        return TextStyler.styleTitleText("Crossbow Enchants [" + Reference.MOD_VERSION + ']')
                .append(TextStyler.styleBooleanText("\nEnabled:", config.isEnabled()))
                .append(TextStyler.styleBooleanText("\nFlame Enabled:", config.isFlameEnabled()))
                .append(TextStyler.styleBooleanText("\nInfinity Enabled:", config.isInfinityEnabled()))
                .append(TextStyler.styleBooleanText("\nLooting Enabled:", config.isLootingEnabled()))
                .append(TextStyler.styleBooleanText("\nPower Enabled:", config.isPowerEnabled()))
                .append(TextStyler.styleBooleanText("\nPunch Enabled:", config.isPunchEnabled()))
                .append(TextStyler.styleBooleanText("\nInfinity-And-Mending Enabled:", config.isInfinityAndMendingEnabled()))
                .append(TextStyler.styleBooleanText("\nPiercing-And-Multishot Enabled:", config.isPiercingAndMultishotEnabled()));
    }

    public static CrossbowEnchantsConfig getConfig() {
        return config;
    }

    private static boolean getBooleanProperty(CommentedFileConfig fileConfig, String path) {
        // Checking if the config file contains the path.
        if (fileConfig.contains(path)) {
            // Reading and returning the property.
            return fileConfig.get(path);
        }
        else {
            // Logging any missing properties.
            CrossbowEnchants.LOGGER.error("Configuration property '{}' is missing from the config file, using default value 'true' instead.", path);
        }

        // Returning 'true', the default value of all properties.
        return true;
    }

    private static CommentedFileConfig buildFileConfig() {
        // Building the CommentedFileConfig with some specific settings.
        return CommentedFileConfig.builder(CONFIG_FILE_PATH)
                .sync()
                .autosave()
                .preserveInsertionOrder()
                .writingMode(WritingMode.REPLACE)
                .build();
    }
}