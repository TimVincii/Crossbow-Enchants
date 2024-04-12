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


    // Injecting into the isAcceptableItem method of the Enchantment class, provides us with the ability to allow or
    // deny certain enchantments on certain items.
    // In our case, this method will control whether Flame, Infinity, Power, and Punch, are allowed on a crossbow.
    @Inject(method="isAcceptableItem", at=@At("HEAD"), cancellable=true)
    private void allowEnchantsOnCrossbow(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        // If the mod is disabled or the candidate for enchanting isn't a crossbow, we return and by that make the class
        // proceed with the original method's execution.
        if (!ConfigManager.getConfig().isEnabled() || !stack.isOf(Items.CROSSBOW)) {
            return;
        }

        boolean result = false;

        // Check each enchantment conditionally and update the result accordingly.
        if (ConfigManager.getConfig().isFlameEnabled() && (Object)this == Enchantments.FLAME) {
            result = true;
        } else if (ConfigManager.getConfig().isInfinityEnabled() && (Object)this == Enchantments.INFINITY) {
            result = true;
        } else if (ConfigManager.getConfig().isPowerEnabled() && (Object)this == Enchantments.POWER) {
            result = true;
        } else if (ConfigManager.getConfig().isPunchEnabled() && (Object)this == Enchantments.PUNCH) {
            result = true;
        }

        // Set the return value and cancel if at least one of the conditions above was met.
        if (result) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }


    // Injecting into the canCombine method of the enchantment class, provides us with the ability to modify the
    // compatibility of certain enchantments with other enchantments.
    // In our case, this method will control whether Infinity and Mending, and Piercing and Multishot, are compatible
    // with each other.
    @Inject(method = "canCombine", at = @At("HEAD"), cancellable = true)
    private void allowFeatures(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
        // Once again, if the mod is disabled, we return and by that make the class proceed with the original method's
        // execution.
        if (!ConfigManager.getConfig().isEnabled())
            return;

        // Check for both cases of the Infinity and Mending combination.
        // Then, set the return value to whether or not the Infinity And Mending feature is enabled.
        if ((Object)this == Enchantments.INFINITY && other == Enchantments.MENDING ||
            (Object)this == Enchantments.MENDING && other == Enchantments.INFINITY) {
            cir.setReturnValue(ConfigManager.getConfig().isInfinityAndMendingEnabled());
            cir.cancel(); // Explicitly stop further processing.
            return; // Exit the method to prevent further checks.
        }

        // Check for both cases of the Piercing and Multishot combination.
        // Then, set the return value to whether or not the Piercing and Multishot feature is enabled.
        if ((Object)this == Enchantments.PIERCING && other == Enchantments.MULTISHOT ||
            (Object)this == Enchantments.MULTISHOT && other == Enchantments.PIERCING) {
            cir.setReturnValue(ConfigManager.getConfig().isPiercingAndMultishotEnabled());
            cir.cancel(); // Again, stop further processing.
            // No need for a return statement here since it's the end of the method.
        }
    }

}

