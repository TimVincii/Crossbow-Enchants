package me.timvinci.crossbowenchants.mixins;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Enchantment.class)
public interface EnchantmentAccessor {

    // Creating an accessor for the 'description' property of the Enchantment class.
    @Accessor("description")
    Text description();

}
