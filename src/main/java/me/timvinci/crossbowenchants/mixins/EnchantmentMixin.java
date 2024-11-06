package me.timvinci.crossbowenchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.timvinci.crossbowenchants.CrossbowEnchants;
import me.timvinci.crossbowenchants.config.ConfigManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;

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
        RegistryEntry<Enchantment> enchantmentEntry = CrossbowEnchants.dynamicRegistryManager.get(RegistryKeys.ENCHANTMENT).getEntry(enchantment);

        // Taking a small precaution.
        if (enchantmentEntry == null) {
            return false;
        }

        if (stack.isOf(Items.BOW)) {
            return enchantmentEntry.matchesKey(Enchantments.LOOTING) && ConfigManager.getConfig().isLootingEnabled();
        }
        else if (enchantmentEntry.matchesKey(Enchantments.FLAME)) {
            return ConfigManager.getConfig().isFlameEnabled();
        }
        else if (enchantmentEntry.matchesKey(Enchantments.INFINITY)) {
            return ConfigManager.getConfig().isInfinityEnabled();
        }
        else if (enchantmentEntry.matchesKey(Enchantments.LOOTING)) {
            return ConfigManager.getConfig().isLootingEnabled();
        }
        else if (enchantmentEntry.matchesKey(Enchantments.POWER)) {
            return ConfigManager.getConfig().isPowerEnabled();
        }
        else if (enchantmentEntry.matchesKey(Enchantments.PUNCH)) {
            return ConfigManager.getConfig().isPunchEnabled();
        }
        else {
            return false;
        }
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
            return ConfigManager.getConfig().isPiercingAndMultishotEnabled();
        }

        return false;
    }

}

