package me.timvinci.crossbowenchants.commands;

import com.mojang.brigadier.context.CommandContext;
import me.timvinci.crossbowenchants.config.ConfigManager;
import me.timvinci.crossbowenchants.config.CrossbowEnchantsConfig;
import me.timvinci.crossbowenchants.util.TextStyler;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;

import java.util.function.Consumer;

/**
 * Provides and holds commanding registering logic.
 */
public class CrossbowEnchantsCommands {
    public static void registerCommands() {
        CrossbowEnchantsConfig config = ConfigManager.getConfig();
        // Registering all the commands.
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, environment) -> dispatcher.register(Commands.literal("crossbowenchants")
            .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(Commands.literal("disable")
                        .executes(context -> modifyProperty(context, config::setEnabled, "Crossbow Enchants successfully", false))
                )
                .then(Commands.literal("enable")
                        .executes(context -> modifyProperty(context, config::setEnabled, "Crossbow Enchants successfully", true))
                )
                .then(Commands.literal("enchantments")
                        .then(Commands.literal("disable")
                            .then(Commands.literal("flame")
                                    .executes(context -> modifyProperty(context, config::setFlameEnabled, "Flame enchantment on crossbows successfully", false))
                            )
                            .then(Commands.literal("infinity")
                                    .executes(context -> modifyProperty(context, config::setInfinityEnabled, "Infinity enchantment on crossbows successfully", false))
                            )
                            .then(Commands.literal("looting")
                                    .executes(context -> modifyProperty(context, config::setLootingEnabled, "Looting enchantment on crossbows and bows successfully", false))
                            )
                            .then(Commands.literal("power")
                                    .executes(context -> modifyProperty(context, config::setPowerEnabled, "Power enchantment on crossbows successfully", false))
                            )
                            .then(Commands.literal("punch")
                                    .executes(context -> modifyProperty(context, config::setPunchEnabled, "Punch enchantment on crossbows successfully", false))
                            )
                        )
                        .then(Commands.literal("enable")
                            .then(Commands.literal("flame")
                                    .executes(context -> modifyProperty(context, config::setFlameEnabled, "Flame enchantment on crossbows successfully", true))
                            )
                            .then(Commands.literal("infinity")
                                    .executes(context -> modifyProperty(context, config::setInfinityEnabled, "Infinity enchantment on crossbows successfully", true))
                            )
                            .then(Commands.literal("looting")
                                    .executes(context -> modifyProperty(context, config::setLootingEnabled, "Looting enchantment on crossbows and bows successfully", true))
                            )
                            .then(Commands.literal("power")
                                    .executes(context -> modifyProperty(context, config::setPowerEnabled, "Power enchantment on crossbows successfully", true))
                            )
                            .then(Commands.literal("punch")
                                    .executes(context -> modifyProperty(context, config::setPunchEnabled, "Punch enchantment on crossbows successfully", true))
                            )
                        )
                )
                .then(Commands.literal("features")
                        .then(Commands.literal("disable")
                            .then(Commands.literal("infinity-and-mending")
                                    .executes(context -> modifyProperty(context, config::setInfinityAndMendingEnabled, "Infinity And Mending on crossbows and bows successfully", false))
                            )
                            .then(Commands.literal("piercing-and-multishot")
                                    .executes(context -> modifyProperty(context, config::setPiercingAndMultishotEnabled, "Piercing And Multishot on crossbows successfully", false))
                            )
                        )
                        .then(Commands.literal("enable")
                                .then(Commands.literal("infinity-and-mending")
                                        .executes(context -> modifyProperty(context, config::setInfinityAndMendingEnabled, "Infinity And Mending on crossbows and bows successfully", true))
                                )
                                .then(Commands.literal("piercing-and-multishot")
                                        .executes(context -> modifyProperty(context, config::setPiercingAndMultishotEnabled, "Piercing And Multishot on crossbows successfully", true))
                                )
                        )
                )
                .then(Commands.literal("info")
                        .executes(context -> {
                            context.getSource().sendSuccess(ConfigManager::getConfigInfo, false);
                            return 1;
                        })
                )
                .then(Commands.literal("reset")
                        .executes(context -> {
                            ConfigManager.resetConfig();
                            ConfigManager.saveConfig();
                            context.getSource().sendSuccess(() -> TextStyler.styleTitleText("Crossbow Enchants settings were successfully reset to default."), true);
                            return 1;
                        })
                )
        ));
    }

    private static int modifyProperty(CommandContext<CommandSourceStack> context, Consumer<Boolean> setter, String feedbackMessage, boolean newValue) {
        setter.accept(newValue);
        context.getSource().sendSuccess(() -> TextStyler.styleEnabledDisabledText(feedbackMessage, newValue), true);
        // Saving the changes.
        if (!ConfigManager.saveConfig()) {
            context.getSource().sendSuccess(() -> TextStyler.styleError("Configuration parsing failed, couldn't save values to config file, please check this session's log/console for more info."), false);
        }

        return 1;
    }
}
