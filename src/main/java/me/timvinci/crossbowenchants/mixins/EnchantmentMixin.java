package me.timvinci.crossbowenchants.mixins;

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

    // Allowing bow enchantments on the crossbow via injecting the isAcceptableItem method.
    @Inject(method="isAcceptableItem", at=@At("TAIL"))
    private boolean allowEnchantsOnCrossbow(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        // Returning if the enchantment is already acceptable on this item, Crossbow Enchants is disabled, or the item
        // isn't a crossbow.
        if (cir.getReturnValue() || !ConfigManager.getConfig().isEnabled() || !stack.isOf(Items.CROSSBOW)) {
            return cir.getReturnValue();
        }

        // Checking the enchantment this mixin is applied on, and returning whether that enchantment is enabled.
        if ((Object)this == Enchantments.FLAME) {
            return ConfigManager.getConfig().isFlameEnabled();
        }
        else if ((Object) this == Enchantments.INFINITY) {
            return ConfigManager.getConfig().isInfinityEnabled();
        }
        else if ((Object) this == Enchantments.POWER) {
            return ConfigManager.getConfig().isPowerEnabled();
        }
        else if ((Object)this == Enchantments.PUNCH) {
            return ConfigManager.getConfig().isPunchEnabled();
        }

        return false;
    }

    // Making infinity and mending as well as piercing and multishot compatible with each other via injecting the
    // canCombine method.
    @Inject(method = "canCombine", at = @At("TAIL"))
    private boolean allowFeatures(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
        // Returning if the enchantments are already compatible with each other, or if Crossbow Enchants is disabled.
        if (cir.getReturnValue() || !ConfigManager.getConfig().isEnabled()) {
            return cir.getReturnValue();
        }

        // Checking if the enchantment this mixin is applied on and the other enchantment are a pair of infinity and
        // mending, and then returning whether their feature is enabled.
        if ((Object)this == Enchantments.INFINITY && other == Enchantments.MENDING ||
            (Object)this == Enchantments.MENDING && other == Enchantments.INFINITY) {
            return ConfigManager.getConfig().isInfinityAndMendingEnabled();
        }

        // Checking if the enchantment this mixin is applied on and the other enchantment are a pair of piercing and
        // multishot, and then returning whether their feature is enabled.
        if ((Object)this == Enchantments.PIERCING && other == Enchantments.MULTISHOT ||
            (Object)this == Enchantments.MULTISHOT && other == Enchantments.PIERCING) {
            return ConfigManager.getConfig().isPiercingAndMultishotEnabled();
        }

        return false;
    }

}

