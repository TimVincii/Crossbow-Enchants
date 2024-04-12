package me.timvinci.crossbowenchants.mixins;

import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

// Accessor class for the CrossbowItemMixin class.
@Mixin(CrossbowItem.class)
public interface CrossbowItemAccessorMixin {

    @Invoker("putProjectile")
    static void putProjectile(ItemStack crossbow, ItemStack projectile) {
        throw new AssertionError();
    }
}

