package me.timvinci.crossbowenchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.timvinci.crossbowenchants.config.ConfigManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @ModifyReturnValue(method = "isAcceptableItem", at = @At("RETURN"))
    public boolean modifyIsAcceptableItemReturnValue(boolean original, ItemStack stack) {
        // Returning the original value if:
        // The original was already set to true.
        // The stack isn't a crossbow.
        // Crossbow Enchants is disabled.
        if (original || !stack.isOf(Items.CROSSBOW) || !ConfigManager.getConfig().isEnabled()) {
            return original;
        }

        //TODO - this isn't a very robust way to check which enchantment this Mixin is applied on..
        String enchantmentName = ((EnchantmentAccessor)this).description().getString();
        // Checking the name of the enchantment and returning based on it.
        return switch (enchantmentName) {
            case "Flame" -> ConfigManager.getConfig().isFlameEnabled();
            case "Infinity" -> ConfigManager.getConfig().isInfinityEnabled();
            case "Power" -> ConfigManager.getConfig().isPowerEnabled();
            case "Punch" -> ConfigManager.getConfig().isPunchEnabled();
            default -> false;
        };
    }

    @ModifyReturnValue(method = "canBeCombined", at = @At("RETURN"))
    private static boolean modifyCanBeCombinedReturnValue(boolean original, RegistryEntry<Enchantment> first, RegistryEntry<Enchantment> second) {
        // Returning the original value if:
        // The original was already set to true.
        // Crossbow Enchants is disabled.
        if (original || !ConfigManager.getConfig().isEnabled()) {
            return original;
        }

        // Checking if the first enchantment is infinity, and the second enchantment is mending, and the opposite order.
        // Then returning the value based on the state of the feature.
        if (first.matchesKey(Enchantments.INFINITY) && second.matchesKey(Enchantments.MENDING) ||
                first.matchesKey(Enchantments.MENDING) && second.matchesKey(Enchantments.INFINITY)) {
            return ConfigManager.getConfig().isInfinityAndMendingEnabled();
        }

        // Checking if the first enchantment is piercing, and the second enchantment is multishot, and the
        // opposite order. Then returning the value based on the state of the feature.
        if (first.matchesKey(Enchantments.PIERCING) && second.matchesKey(Enchantments.MULTISHOT) ||
                first.matchesKey(Enchantments.MULTISHOT) && second.matchesKey(Enchantments.PIERCING)) {
            return ConfigManager.getConfig().isEnabled();
        }

        return false;
    }

}

