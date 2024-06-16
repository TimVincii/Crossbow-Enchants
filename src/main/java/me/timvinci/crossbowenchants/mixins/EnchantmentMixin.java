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

// Enchantment Mixin class.
@Mixin(Enchantment.class)
public class EnchantmentMixin {


    // The isAcceptableItem method of the Enchantment class is used to check which enchantments are allowed on certain items.
    // By modifying its return value, it is possible to directly allow bow exclusive enchantments on the Crossbow Item.
    @ModifyReturnValue(method = "isAcceptableItem", at = @At("RETURN"))
    public boolean modifyIsAcceptableItemReturnValue(boolean original, ItemStack stack) {
        if (original || !stack.isOf(Items.CROSSBOW) || !ConfigManager.getConfig().isEnabled()) {
            return original;
        }

        //TODO - this isn't very robust way to check which enchantment this Mixin is applied on..
        String enchantmentName = ((EnchantmentAccessor)this).description().getString();
        return switch (enchantmentName) {
            case "Flame" -> ConfigManager.getConfig().isFlameEnabled();
            case "Infinity" -> ConfigManager.getConfig().isInfinityEnabled();
            case "Power" -> ConfigManager.getConfig().isPowerEnabled();
            case "Punch" -> ConfigManager.getConfig().isPunchEnabled();
            default -> false;
        };

    }

    // The canBeCombined method of the Enchantment class is used to check which enchantments are compatible with one another.
    // By modifying its return value, it is possible to directly allow infinity and mending, as well as piercing and multishot, to coexist.
    @ModifyReturnValue(method = "canBeCombined", at = @At("RETURN"))
    private static boolean modifyCanBeCombinedReturnValue(boolean original, RegistryEntry<Enchantment> first, RegistryEntry<Enchantment> second) {
        if (original || !ConfigManager.getConfig().isEnabled()) {
            return original;
        }

        if (first.matchesKey(Enchantments.INFINITY) && second.matchesKey(Enchantments.MENDING) ||
                first.matchesKey(Enchantments.MENDING) && second.matchesKey(Enchantments.INFINITY)) {
            return ConfigManager.getConfig().isInfinityAndMendingEnabled();
        }

        if (first.matchesKey(Enchantments.PIERCING) && second.matchesKey(Enchantments.MULTISHOT) ||
                first.matchesKey(Enchantments.MULTISHOT) && second.matchesKey(Enchantments.PIERCING)) {
            return ConfigManager.getConfig().isEnabled();
        }

        return false;
    }

}

