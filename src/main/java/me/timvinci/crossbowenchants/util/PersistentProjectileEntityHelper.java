package me.timvinci.crossbowenchants.util;

import me.timvinci.crossbowenchants.config.ConfigManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;

// This small class separates the enchanting logic from the CrossbowItemMixin class.
// All enchantment logic seen here is copied from the BowItem class.
public class PersistentProjectileEntityHelper {
    public static void applyFlameEnchant(PersistentProjectileEntity entity, ItemStack crossbow) {
        if (ConfigManager.getConfig().isFlameEnabled() && EnchantmentHelper.getLevel(Enchantments.FLAME, crossbow) > 0)
            entity.setOnFireFor(100);
    }

    // This method only sets the arrows pick-ability to creative if the crossbow has infinity, the rest of the logic
    // behind infinity functioning on crossbows is applied in CrossbowItemMixin.retrieveArrowServerSide / retrieveArrowClientSide.
    public static void applyInfinityEnchant(PersistentProjectileEntity entity, ItemStack crossbow) {
        if (ConfigManager.getConfig().isInfinityEnabled() && EnchantmentHelper.getLevel(Enchantments.INFINITY, crossbow) > 0)
            entity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
    }

    public static void applyPowerEnchant(PersistentProjectileEntity entity, ItemStack crossbow) {
        if (ConfigManager.getConfig().isPowerEnabled()) {
            int powerLevel = EnchantmentHelper.getLevel(Enchantments.POWER, crossbow);
            entity.setDamage(entity.getDamage() + (double)powerLevel * 0.5 + 0.5);
        }
    }

    public static void applyPunchEnchant(PersistentProjectileEntity entity, ItemStack crossbow) {
        if (ConfigManager.getConfig().isPunchEnabled()) {
            int punchLevel = EnchantmentHelper.getLevel(Enchantments.PUNCH, crossbow);
            if (punchLevel > 0) entity.setPunch(punchLevel);
        }
    }
}
