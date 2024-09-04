package me.timvinci.crossbowenchants.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class TextStyler {
    // Setting the colors to use.
    private static final Formatting TITLE_COLOR = Formatting.GOLD;
    private static final Formatting TEXT_COLOR = Formatting.YELLOW;
    private static final Formatting POSITIVE_COLOR = Formatting.GREEN;
    private static final Formatting NEGATIVE_COLOR = Formatting.RED;

    private static MutableText colorize(String text, Formatting color) {
        return Text.literal(text).formatted(color);
    }

    private static MutableText colorizeBoolean(Boolean bool) {
        return colorize(String.valueOf(bool), bool ? POSITIVE_COLOR : NEGATIVE_COLOR);
    }

    public static MutableText styleTitleText(String text) {
        return colorize(text, TITLE_COLOR);
    }

    public static Text styleBooleanText(String text, Boolean bool) {
        return Text.literal(text + ' ')
                .formatted(TEXT_COLOR)
                .append(colorizeBoolean(bool))
                .append(colorize(".", TEXT_COLOR));
    }

    public static Text styleEnabledDisabledText(String text, Boolean bool) {
        return Text.literal(text + ' ')
                .formatted(TEXT_COLOR)
                .append(colorize(bool ? "enabled" : "disabled", bool ? POSITIVE_COLOR : NEGATIVE_COLOR))
                .append(colorize(".", TEXT_COLOR));
    }

    public static MutableText styleError(String errorMessage) {
        return Text.literal(errorMessage).formatted(NEGATIVE_COLOR);
    }
}
