package me.timvinci.crossbowenchants.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.ParsingException;
import com.electronwill.nightconfig.core.io.ParsingMode;
import com.electronwill.nightconfig.core.io.WritingMode;
import me.timvinci.crossbowenchants.CrossbowEnchants;
import me.timvinci.crossbowenchants.util.ColoredTextFormatter;
import me.timvinci.crossbowenchants.util.Reference;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import java.nio.file.Path;

// The mod's config manager class, powered by NightConfig.
public class ConfigManager {

    private static final Path CONFIG_FILE_PATH = FabricLoader.getInstance().getConfigDir().resolve("crossbowenchants.toml");
    private static final CrossbowEnchantsConfig config = new CrossbowEnchantsConfig();

    public static void loadConfig() {
        CommentedFileConfig fileConfig = buildFileConfig();
        try {
            fileConfig.load();
        }
        catch (ParsingException exception) {
            String exceptionMessage = Reference.MOD_NAME + ": Config parsing failed, please validate the config properties in .\\config\\crossbowenchants.toml. Exception message: " + exception.getMessage();
            throw new ParsingException(exceptionMessage);
        }

        if (!fileConfig.getFile().exists() || fileConfig.isEmpty()) {
            fileConfig.close();
            saveConfig();
        } else {
            config.setEnabled(getBooleanProperty(fileConfig, "Enabled"));
            config.setFlameEnabled(getBooleanProperty(fileConfig, "FlameEnabled"));
            config.setInfinityEnabled(getBooleanProperty(fileConfig, "InfinityEnabled"));
            config.setPowerEnabled(getBooleanProperty(fileConfig, "PowerEnabled"));
            config.setPunchEnabled(getBooleanProperty(fileConfig, "PunchEnabled"));
            config.setInfinityAndMendingEnabled(getBooleanProperty(fileConfig, "InfinityAndMendingEnabled"));
            config.setPiercingAndMultishotEnabled(getBooleanProperty(fileConfig, "PiercingAndMultishotEnabled"));
            fileConfig.close();
        }
    }

    public static void saveConfig() {
        CommentedFileConfig fileConfig = buildFileConfig();
        try {
            fileConfig.load();
        }
        catch (ParsingException exception) {
            String exceptionMessage = Reference.MOD_NAME + ": Config parsing failed, please validate the config properties in .\\config\\crossbowenchants.toml. Exception message: " + exception.getMessage();
            throw new ParsingException(exceptionMessage);
        }

        fileConfig.set("Enabled", config.isEnabled());
        fileConfig.setComment("Enabled", "A toggle for the global functionality of Crossbow Enchants.");

        fileConfig.set("FlameEnabled", config.isFlameEnabled());
        fileConfig.setComment("FlameEnabled", "A toggle for the functionality of the Flame enchantment on the crossbow.");

        fileConfig.set("InfinityEnabled", config.isInfinityEnabled());
        fileConfig.setComment("InfinityEnabled", "A toggle for the functionality of the Infinity enchantment on the crossbow.");

        fileConfig.set("PowerEnabled", config.isPowerEnabled());
        fileConfig.setComment("PowerEnabled", "A toggle for the functionality of the Power enchantment on the crossbow.");

        fileConfig.set("PunchEnabled", config.isPunchEnabled());
        fileConfig.setComment("PunchEnabled", "A toggle for the functionality of the Punch enchantment on the crossbow.");

        fileConfig.set("InfinityAndMendingEnabled", config.isInfinityAndMendingEnabled());
        fileConfig.setComment("InfinityAndMendingEnabled", "A toggle for the compatibility of Infinity and Mending on crossbows and bows.");

        fileConfig.set("PiercingAndMultishotEnabled", config.isPiercingAndMultishotEnabled());
        fileConfig.setComment("PiercingAndMultishotEnabled", "A toggle for the compatibility of Piercing and Multishot on crossbows.");

        fileConfig.save();
        fileConfig.close();
    }

    public static void resetConfig() {
        config.setEnabled(true);
        config.setFlameEnabled(true);
        config.setInfinityEnabled(true);
        config.setPowerEnabled(true);
        config.setPunchEnabled(true);
        config.setInfinityAndMendingEnabled(true);
        config.setPiercingAndMultishotEnabled(true);
    }

    public static Text getConfigInfo() {
        return ColoredTextFormatter.formatTitleText("Crossbow Enchants [" + Reference.MOD_VERSION + ']')
                .append(ColoredTextFormatter.formatBooleanText("\nEnabled:", config.isEnabled()))
                .append(ColoredTextFormatter.formatBooleanText("\nFlame Enabled:", config.isFlameEnabled()))
                .append(ColoredTextFormatter.formatBooleanText("\nInfinity Enabled:", config.isInfinityEnabled()))
                .append(ColoredTextFormatter.formatBooleanText("\nPower Enabled:", config.isPowerEnabled()))
                .append(ColoredTextFormatter.formatBooleanText("\nPunch Enabled:", config.isPunchEnabled()))
                .append(ColoredTextFormatter.formatBooleanText("\nInfinity-And-Mending Enabled:", config.isInfinityAndMendingEnabled()))
                .append(ColoredTextFormatter.formatBooleanText("\nPiercing-And-Multishot Enabled:", config.isPiercingAndMultishotEnabled()));
    }

    public static CrossbowEnchantsConfig getConfig() {
        return config;
    }

    private static boolean getBooleanProperty(CommentedFileConfig fileConfig, String path) {
        if (fileConfig.contains(path)) {
            return fileConfig.get(path);
        }
        else {
            CrossbowEnchants.LOGGER.error("Configuration property '{}' is missing from the config file, using default value 'true' instead.", path);
        }

        return true;
    }

    private static CommentedFileConfig buildFileConfig() {
        return CommentedFileConfig.builder(CONFIG_FILE_PATH)
                .sync()
                .autosave()
                .preserveInsertionOrder()
                .writingMode(WritingMode.REPLACE)
                .build();
    }
}
