package me.timvinci.crossbowenchants.commands;

import com.mojang.brigadier.context.CommandContext;
import me.timvinci.crossbowenchants.config.ConfigManager;
import me.timvinci.crossbowenchants.config.CrossbowEnchantsConfig;
import me.timvinci.crossbowenchants.util.TextStyler;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// A class responsible for registering the commands, not much else to say about it.
public class CrossbowEnchantsCommands {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, environment) -> dispatcher.register(CommandManager.literal("crossbowenchants")
            .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("disable")
                        .executes(context -> modifyProperty(context, "setEnabled", "Crossbow Enchants successfully", false))
                )
                .then(CommandManager.literal("enable")
                        .executes(context -> modifyProperty(context, "setEnabled", "Crossbow Enchants successfully", true))
                )
                .then(CommandManager.literal("enchantments")
                        .then(CommandManager.literal("disable")
                            .then(CommandManager.literal("flame")
                                    .executes(context -> modifyProperty(context, "setFlameEnabled", "Flame enchantment on crossbows successfully", false))
                            )
                            .then(CommandManager.literal("infinity")
                                    .executes(context -> modifyProperty(context, "setInfinityEnabled", "Infinity enchantment on crossbows successfully", false))
                            )
                            .then(CommandManager.literal("power")
                                    .executes(context -> modifyProperty(context, "setPowerEnabled", "Power enchantment on crossbows successfully", false))
                            )
                            .then(CommandManager.literal("punch")
                                    .executes(context -> modifyProperty(context, "setPunchEnabled", "Punch enchantment on crossbows successfully", false))
                            )
                        )
                        .then(CommandManager.literal("enable")
                            .then(CommandManager.literal("flame")
                                    .executes(context -> modifyProperty(context, "setFlameEnabled", "Flame enchantment on crossbows successfully", true))
                            )
                            .then(CommandManager.literal("infinity")
                                    .executes(context -> modifyProperty(context, "setInfinityEnabled", "Infinity enchantment on crossbows successfully", true))
                            )
                            .then(CommandManager.literal("power")
                                    .executes(context -> modifyProperty(context, "setPowerEnabled", "Power enchantment on crossbows successfully", true))
                            )
                            .then(CommandManager.literal("punch")
                                    .executes(context -> modifyProperty(context, "setPunchEnabled", "Punch enchantment on crossbows successfully", true))
                            )
                        )
                )
                .then(CommandManager.literal("features")
                        .then(CommandManager.literal("disable")
                            .then(CommandManager.literal("infinity-and-mending")
                                    .executes(context -> modifyProperty(context, "setInfinityAndMendingEnabled", "Infinity And Mending on crossbows and bows successfully", false))
                            )
                            .then(CommandManager.literal("piercing-and-multishot")
                                    .executes(context -> modifyProperty(context, "setPiercingAndMultishotEnabled", "Piercing And Multishot on crossbows successfully", false))
                            )
                        )
                        .then(CommandManager.literal("enable")
                                .then(CommandManager.literal("infinity-and-mending")
                                        .executes(context -> modifyProperty(context, "setInfinityAndMendingEnabled", "Infinity And Mending on crossbows and bows successfully", true))
                                )
                                .then(CommandManager.literal("piercing-and-multishot")
                                        .executes(context -> modifyProperty(context, "setPiercingAndMultishotEnabled", "Piercing And Multishot on crossbows successfully", true))
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

    private static int modifyProperty(CommandContext<ServerCommandSource> context, String methodName, String feedbackMessage, boolean newValue) {
        try {
            Method propertySetter = CrossbowEnchantsConfig.class.getDeclaredMethod(methodName, boolean.class);
            propertySetter.invoke(ConfigManager.getConfig(), newValue);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ConfigManager.saveConfig();
        context.getSource().sendFeedback(() -> TextStyler.styleEnabledDisabledText(feedbackMessage, newValue), true);

        return 1;
    }
}
