package me.timvinci.crossbowenchants.mixins;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

// Enchantment Mixin Accessor class.
@Mixin(Enchantment.class)
public interface EnchantmentAccessor {

    @Accessor("description")
    Text description();

}
