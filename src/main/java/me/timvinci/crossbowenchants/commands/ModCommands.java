package me.timvinci.crossbowenchants.commands;

import me.timvinci.crossbowenchants.config.ConfigManager;
import me.timvinci.crossbowenchants.util.ColoredTextFormatter;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;

// A class responsible for registering the commands, not much else to say about it.
public class ModCommands {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, environment) -> dispatcher.register(CommandManager.literal("crossbowenchants")
            .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("disable")
                        .executes(context -> {
                            ConfigManager.getConfig().setEnabled(false);
                            ConfigManager.saveConfig();
                            context.getSource().sendFeedback(() -> ColoredTextFormatter.formatBooleanTextEnabledDisabled("Crossbow Enchants successfully", false), false);
                            return 1;
                        })
                )
                .then(CommandManager.literal("enable")
                        .executes(context -> {
                            ConfigManager.getConfig().setEnabled(true);
                            ConfigManager.saveConfig();
                            context.getSource().sendFeedback(() -> ColoredTextFormatter.formatBooleanTextEnabledDisabled("Crossbow Enchants successfully", true), false);
                            return 1;
                        })
                )
                .then(CommandManager.literal("enchantments")
                        .then(CommandManager.literal("disable")
                            .then(CommandManager.literal("flame")
                                    .executes(context -> {
                                        ConfigManager.getConfig().setFlameEnabled(false);
                                        ConfigManager.saveConfig();
                                        context.getSource().sendFeedback(() -> ColoredTextFormatter.formatBooleanTextEnabledDisabled("Flame enchantment on crossbows successfully", false), false);
                                        return 1;
                                    })
                            )
                            .then(CommandManager.literal("infinity")
                                    .executes(context -> {
                                        ConfigManager.getConfig().setInfinityEnabled(false);
                                        ConfigManager.saveConfig();
                                        context.getSource().sendFeedback(() -> ColoredTextFormatter.formatBooleanTextEnabledDisabled("Infinity enchantment on crossbows successfully", false), false);
                                        return 1;
                                    })
                            )
                            .then(CommandManager.literal("power")
                                    .executes(context -> {
                                        ConfigManager.getConfig().setPowerEnabled(false);
                                        ConfigManager.saveConfig();
                                        context.getSource().sendFeedback(() -> ColoredTextFormatter.formatBooleanTextEnabledDisabled("Power enchantment on crossbows successfully", false), false);
                                        return 1;
                                    })
                            )
                            .then(CommandManager.literal("punch")
                                    .executes(context -> {
                                        ConfigManager.getConfig().setPunchEnabled(false);
                                        ConfigManager.saveConfig();
                                        context.getSource().sendFeedback(() -> ColoredTextFormatter.formatBooleanTextEnabledDisabled("Punch enchantment on crossbows successfully", false), false);
                                        return 1;
                                    })
                            )
                        )
                        .then(CommandManager.literal("enable")
                            .then(CommandManager.literal("flame")
                                .executes(context -> {
                                    ConfigManager.getConfig().setFlameEnabled(true);
                                    ConfigManager.saveConfig();
                                    context.getSource().sendFeedback(() -> ColoredTextFormatter.formatBooleanTextEnabledDisabled("Flame enchantment on crossbows successfully", true), false);
                                    return 1;
                                })
                            )
                            .then(CommandManager.literal("infinity")
                                    .executes(context -> {
                                        ConfigManager.getConfig().setInfinityEnabled(true);
                                        ConfigManager.saveConfig();
                                        context.getSource().sendFeedback(() -> ColoredTextFormatter.formatBooleanTextEnabledDisabled("Infinity enchantment on crossbows successfully", true), false);
                                        return 1;
                                    })
                            )
                            .then(CommandManager.literal("power")
                                    .executes(context -> {
                                        ConfigManager.getConfig().setPowerEnabled(true);
                                        ConfigManager.saveConfig();
                                        context.getSource().sendFeedback(() -> ColoredTextFormatter.formatBooleanTextEnabledDisabled("Power enchantment on crossbows successfully", true), false);
                                        return 1;
                                    })
                            )
                            .then(CommandManager.literal("punch")
                                    .executes(context -> {
                                        ConfigManager.getConfig().setPunchEnabled(true);
                                        ConfigManager.saveConfig();
                                        context.getSource().sendFeedback(() -> ColoredTextFormatter.formatBooleanTextEnabledDisabled("Punch enchantment on crossbows successfully", true), false);
                                        return 1;
                                    })
                            )
                        )
                )
                .then(CommandManager.literal("features")
                        .then(CommandManager.literal("disable")
                            .then(CommandManager.literal("infinity-and-mending")
                                    .executes(context -> {
                                        ConfigManager.getConfig().setInfinityAndMendingEnabled(false);
                                        ConfigManager.saveConfig();
                                        context.getSource().sendFeedback(() -> ColoredTextFormatter.formatBooleanTextEnabledDisabled("Infinity And Mending on crossbows successfully", false), false);
                                        return 1;
                                    })
                            )
                            .then(CommandManager.literal("piercing-and-multishot")
                                    .executes(context -> {
                                        ConfigManager.getConfig().setPiercingAndMultishotEnabled(false);
                                        ConfigManager.saveConfig();
                                        context.getSource().sendFeedback(() -> ColoredTextFormatter.formatBooleanTextEnabledDisabled("Piercing And Multishot on crossbows successfully", false), false);
                                        return 1;
                                    })
                            )
                        )
                        .then(CommandManager.literal("enable")
                            .then(CommandManager.literal("infinity-and-mending")
                                    .executes(context -> {
                                        ConfigManager.getConfig().setInfinityAndMendingEnabled(true);
                                        ConfigManager.saveConfig();
                                        context.getSource().sendFeedback(() -> ColoredTextFormatter.formatBooleanTextEnabledDisabled("Infinity And Mending on crossbows successfully", true), false);
                                        return 1;
                                    })
                            )
                            .then(CommandManager.literal("piercing-and-multishot")
                                    .executes(context -> {
                                        ConfigManager.getConfig().setPiercingAndMultishotEnabled(true);
                                        ConfigManager.saveConfig();
                                        context.getSource().sendFeedback(() -> ColoredTextFormatter.formatBooleanTextEnabledDisabled("Piercing And Multishot on crossbows successfully", true), false);
                                        return 1;
                                    })
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
                            context.getSource().sendFeedback(() -> ColoredTextFormatter.formatTitleText("Crossbow Enchants settings were successfully reset to default."), false);
                            return 1;
                        })
                )
        ));
    }

}
