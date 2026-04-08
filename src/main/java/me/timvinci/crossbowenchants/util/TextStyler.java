package me.timvinci.crossbowenchants.util;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

/**
 * A compact class used for styling text.
 */
public class TextStyler {
    // Setting the colors to use.
    private static final ChatFormatting TITLE_COLOR = ChatFormatting.GOLD;
    private static final ChatFormatting TEXT_COLOR = ChatFormatting.YELLOW;
    private static final ChatFormatting POSITIVE_COLOR = ChatFormatting.GREEN;
    private static final ChatFormatting NEGATIVE_COLOR = ChatFormatting.RED;

    private static MutableComponent colorize(String text, ChatFormatting color) {
        return Component.literal(text).withStyle(color);
    }

    private static MutableComponent colorizeBoolean(Boolean bool) {
        return colorize(String.valueOf(bool), bool ? POSITIVE_COLOR : NEGATIVE_COLOR);
    }

    public static MutableComponent styleTitleText(String text) {
        return colorize(text, TITLE_COLOR);
    }

    public static Component styleBooleanText(String text, Boolean bool) {
        return Component.literal(text + ' ')
                .withStyle(TEXT_COLOR)
                .append(colorizeBoolean(bool));
    }

    public static Component styleEnabledDisabledText(String text, Boolean bool) {
        return Component.literal(text + ' ')
                .withStyle(TEXT_COLOR)
                .append(colorize(bool ? "enabled" : "disabled", bool ? POSITIVE_COLOR : NEGATIVE_COLOR))
                .append(colorize(".", TEXT_COLOR));
    }

    public static MutableComponent styleError(String errorMessage) {
        return Component.literal(errorMessage).withStyle(NEGATIVE_COLOR);
    }
}
