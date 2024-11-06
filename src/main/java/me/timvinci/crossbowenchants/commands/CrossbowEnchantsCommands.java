package me.timvinci.crossbowenchants.commands;

import com.mojang.brigadier.context.CommandContext;
import me.timvinci.crossbowenchants.config.ConfigManager;
import me.timvinci.crossbowenchants.config.CrossbowEnchantsConfig;
import me.timvinci.crossbowenchants.util.TextStyler;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.function.Consumer;

public class CrossbowEnchantsCommands {
    public static void registerCommands() {
        CrossbowEnchantsConfig config = ConfigManager.getConfig();
        // Registering all the commands.
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, environment) -> dispatcher.register(CommandManager.literal("crossbowenchants")
            .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("disable")
                        .executes(context -> modifyProperty(context, config::setEnabled, "Crossbow Enchants successfully", false))
                )
                .then(CommandManager.literal("enable")
                        .executes(context -> modifyProperty(context, config::setEnabled, "Crossbow Enchants successfully", true))
                )
                .then(CommandManager.literal("enchantments")
                        .then(CommandManager.literal("disable")
                            .then(CommandManager.literal("flame")
                                    .executes(context -> modifyProperty(context, config::setFlameEnabled, "Flame enchantment on crossbows successfully", false))
                            )
                            .then(CommandManager.literal("infinity")
                                    .executes(context -> modifyProperty(context, config::setInfinityEnabled, "Infinity enchantment on crossbows successfully", false))
                            )
                            .then(CommandManager.literal("looting")
                                    .executes(context -> modifyProperty(context, config::setLootingEnabled, "Looting enchantment on crossbows and bows successfully", false))
                            )
                            .then(CommandManager.literal("power")
                                    .executes(context -> modifyProperty(context, config::setPowerEnabled, "Power enchantment on crossbows successfully", false))
                            )
                            .then(CommandManager.literal("punch")
                                    .executes(context -> modifyProperty(context, config::setPunchEnabled, "Punch enchantment on crossbows successfully", false))
                            )
                        )
                        .then(CommandManager.literal("enable")
                            .then(CommandManager.literal("flame")
                                    .executes(context -> modifyProperty(context, config::setFlameEnabled, "Flame enchantment on crossbows successfully", true))
                            )
                            .then(CommandManager.literal("infinity")
                                    .executes(context -> modifyProperty(context, config::setInfinityEnabled, "Infinity enchantment on crossbows successfully", true))
                            )
                            .then(CommandManager.literal("looting")
                                    .executes(context -> modifyProperty(context, config::setLootingEnabled, "Looting enchantment on crossbows and bows successfully", true))
                            )
                            .then(CommandManager.literal("power")
                                    .executes(context -> modifyProperty(context, config::setPowerEnabled, "Power enchantment on crossbows successfully", true))
                            )
                            .then(CommandManager.literal("punch")
                                    .executes(context -> modifyProperty(context, config::setPunchEnabled, "Punch enchantment on crossbows successfully", true))
                            )
                        )
                )
                .then(CommandManager.literal("features")
                        .then(CommandManager.literal("disable")
                            .then(CommandManager.literal("infinity-and-mending")
                                    .executes(context -> modifyProperty(context, config::setInfinityAndMendingEnabled, "Infinity And Mending on crossbows and bows successfully", false))
                            )
                            .then(CommandManager.literal("piercing-and-multishot")
                                    .executes(context -> modifyProperty(context, config::setPiercingAndMultishotEnabled, "Piercing And Multishot on crossbows successfully", false))
                            )
                        )
                        .then(CommandManager.literal("enable")
                                .then(CommandManager.literal("infinity-and-mending")
                                        .executes(context -> modifyProperty(context, config::setInfinityAndMendingEnabled, "Infinity And Mending on crossbows and bows successfully", true))
                                )
                                .then(CommandManager.literal("piercing-and-multishot")
                                        .executes(context -> modifyProperty(context, config::setPiercingAndMultishotEnabled, "Piercing And Multishot on crossbows successfully", true))
                                )
                        )
                )
                .then(CommandManager.literal("info")
                        .executes(context -> {
                            context.getSource().sendFeedback(ConfigManager::getConfigInfo, false);
                            return 1;
                        })
                )
                .then(CommandManager.literal("reset")
                        .executes(context -> {
                            ConfigManager.resetConfig();
                            ConfigManager.saveConfig();
                            context.getSource().sendFeedback(() -> TextStyler.styleTitleText("Crossbow Enchants settings were successfully reset to default."), true);
                            return 1;
                        })
                )
        ));
    }

    private static int modifyProperty(CommandContext<ServerCommandSource> context, Consumer<Boolean> setter, String feedbackMessage, boolean newValue) {
        setter.accept(newValue);
        context.getSource().sendFeedback(() -> TextStyler.styleEnabledDisabledText(feedbackMessage, newValue), true);
        // Saving the changes.
        if (!ConfigManager.saveConfig()) {
            context.getSource().sendFeedback(() -> TextStyler.styleError("Configuration parsing failed, couldn't save values to config file, please check this session's log/console for more info."), false);
        }

        return 1;
    }
}
