package me.timvinci.crossbowenchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.timvinci.crossbowenchants.CrossbowEnchants;
import me.timvinci.crossbowenchants.config.ConfigManager;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * A mixin of the Enchantment class, used to make the exclusive bow enchantments applicable to the Crossbow, and to
 * make Infinity and Mending as well as Piercing and Multishot compatible with one another.
 */
@Mixin(Enchantment.class)
public class EnchantmentMixin {

    /**
     * Makes the bow exclusive enchantments applicable to the Crossbow.
     */
    @ModifyReturnValue(method = "canEnchant", at = @At("RETURN"))
    public boolean canEnchant(boolean original, ItemStack stack) {
        // Returning the original value if:
        // The original was already set to true.
        // The stack isn't a crossbow.
        // Crossbow Enchants is disabled.
        if (original || !(stack.is(Items.CROSSBOW) || stack.is(Items.BOW)) || !ConfigManager.getConfig().isEnabled()) {
            return original;
        }

        Enchantment enchantment = (Enchantment) (Object) this;
        Holder<Enchantment> enchantmentEntry = CrossbowEnchants.dynamicRegistryManager.lookupOrThrow(Registries.ENCHANTMENT).wrapAsHolder(enchantment);

        // Taking a small precaution.
        if (enchantmentEntry == null) {
            return false;
        }

        if (stack.is(Items.BOW)) {
            return enchantmentEntry.is(Enchantments.LOOTING) && ConfigManager.getConfig().isLootingEnabled();
        }
        else if (enchantmentEntry.is(Enchantments.FLAME)) {
            return ConfigManager.getConfig().isFlameEnabled();
        }
        else if (enchantmentEntry.is(Enchantments.INFINITY)) {
            return ConfigManager.getConfig().isInfinityEnabled();
        }
        else if (enchantmentEntry.is(Enchantments.LOOTING)) {
            return ConfigManager.getConfig().isLootingEnabled();
        }
        else if (enchantmentEntry.is(Enchantments.POWER)) {
            return ConfigManager.getConfig().isPowerEnabled();
        }
        else if (enchantmentEntry.is(Enchantments.PUNCH)) {
            return ConfigManager.getConfig().isPunchEnabled();
        }
        else {
            return false;
        }
    }

    /**
     * Makes Infinity and Mending as well as Piercing and Multishot compatible with one another.
     */
    @ModifyReturnValue(method = "areCompatible", at = @At("RETURN"))
    private static boolean areCompatible(boolean original, Holder<Enchantment> first, Holder<Enchantment> second) {
        // Returning the original value if:
        // The original was already set to true.
        // Crossbow Enchants is disabled.
        if (original || !ConfigManager.getConfig().isEnabled()) {
            return original;
        }

        // Checking if the first enchantment is infinity, and the second enchantment is mending, and the opposite order.
        // Then returning the value based on the state of the feature.
        if (first.is(Enchantments.INFINITY) && second.is(Enchantments.MENDING) ||
                first.is(Enchantments.MENDING) && second.is(Enchantments.INFINITY)) {
            return ConfigManager.getConfig().isInfinityAndMendingEnabled();
        }

        // Checking if the first enchantment is piercing, and the second enchantment is multishot, and the
        // opposite order. Then returning the value based on the state of the feature.
        if (first.is(Enchantments.PIERCING) && second.is(Enchantments.MULTISHOT) ||
                first.is(Enchantments.MULTISHOT) && second.is(Enchantments.PIERCING)) {
            return ConfigManager.getConfig().isPiercingAndMultishotEnabled();
        }

        return false;
    }
}
