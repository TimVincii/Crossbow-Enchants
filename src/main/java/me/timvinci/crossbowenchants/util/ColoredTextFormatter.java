package me.timvinci.crossbowenchants.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

// A simple class that helps format color on text.
public class ColoredTextFormatter {

    private static final Formatting defaultStringTitleFormattingColor = Formatting.GOLD;
    private static final Formatting defaultStringTextFormattingColor = Formatting.YELLOW;
    private static final Formatting defaultBooleanTrueFormattingColor = Formatting.GREEN;
    private static final Formatting defaultBooleanFalseFormattingColor = Formatting.RED;
    public static MutableText formatTitleText(String text) {
        return Text.literal(text).formatted(defaultStringTitleFormattingColor);
    }
    public static MutableText formatText(String text) {
        return Text.literal(text).formatted(defaultStringTextFormattingColor);
    }
    public static Text formatBooleanText(String text, Boolean bool) {
        return Text.literal(text + ' ')
                .formatted(defaultStringTextFormattingColor)
                .append(Text.literal(String.valueOf(bool))
                        .formatted(bool ? defaultBooleanTrueFormattingColor : defaultBooleanFalseFormattingColor))
                .append(Text.literal(".")
                        .formatted(defaultStringTextFormattingColor));
    }
    public static Text formatBooleanTextEnabledDisabled(String text, Boolean bool) {
        return Text.literal(text + ' ')
                .formatted(defaultStringTextFormattingColor)
                .append(Text.literal(bool ? "enabled" : "disabled")
                        .formatted(bool ? defaultBooleanTrueFormattingColor : defaultBooleanFalseFormattingColor))
                .append(Text.literal(".")
                        .formatted(defaultStringTextFormattingColor));
    }
}
