package me.timvinci.crossbowenchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.timvinci.crossbowenchants.config.ConfigManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Enchantment Mixin class.
@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @ModifyReturnValue(method = "isAcceptableItem", at = @At("RETURN"))
    public boolean modifyIsAcceptableItemReturnValue(boolean original, ItemStack stack) {
        // Returning the original value if:
        // The original was already set to true.
        // The stack isn't a crossbow.
        // Crossbow Enchants is disabled.
        if (original || !(stack.isOf(Items.CROSSBOW) || stack.isOf(Items.BOW)) || !ConfigManager.getConfig().isEnabled()) {
            return original;
        }

        Enchantment enchantment = (Enchantment) (Object) this;
        if (stack.isOf(Items.BOW)) {
            return enchantment == Enchantments.LOOTING && ConfigManager.getConfig().isLootingEnabled();
        }
        else if (enchantment == Enchantments.FLAME) {
            return ConfigManager.getConfig().isFlameEnabled();
        }
        else if (enchantment == Enchantments.INFINITY) {
            return ConfigManager.getConfig().isInfinityEnabled();
        }
        else if (enchantment == Enchantments.LOOTING) {
            return ConfigManager.getConfig().isLootingEnabled();
        }
        else if (enchantment == Enchantments.POWER) {
            return ConfigManager.getConfig().isPowerEnabled();
        }
        else if (enchantment == Enchantments.PUNCH) {
            return ConfigManager.getConfig().isPunchEnabled();
        }
        else {
            return false;
        }
    }

    /**
     * Makes Infinity and Mending as well as Piercing and Multishot compatible with one another.
     */
    @ModifyReturnValue(method = "canCombine", at = @At("RETURN"))
    private boolean modifyCanCombineReturnValue(boolean original, Enchantment other) {
        // Returning the original value if:
        // The original was already set to true.
        // Crossbow Enchants is disabled.
        if (original || !ConfigManager.getConfig().isEnabled()) {
            return original;
        }

        Enchantment enchantment = (Enchantment) (Object) this;
        // Checking if the first enchantment is infinity, and the second enchantment is mending, and the opposite order.
        // Then returning the value based on the state of the feature.
        if (enchantment == Enchantments.INFINITY && other == Enchantments.MENDING ||
                enchantment == Enchantments.MENDING && other == Enchantments.INFINITY) {
            return ConfigManager.getConfig().isInfinityAndMendingEnabled();
        }

        // Checking if the first enchantment is piercing, and the second enchantment is multishot, and the
        // opposite order. Then returning the value based on the state of the feature.
        if (enchantment == Enchantments.PIERCING && other == Enchantments.MULTISHOT ||
                enchantment == Enchantments.MULTISHOT && other == Enchantments.PIERCING) {
            return ConfigManager.getConfig().isPiercingAndMultishotEnabled();
        }

        return false;
    }
}

