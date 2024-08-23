package me.timvinci.crossbowenchants.util;

import me.timvinci.crossbowenchants.config.ConfigManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class EnchantmentApplier {
    public static void applyFlame(PersistentProjectileEntity entity, ItemStack crossbow) {
        if (ConfigManager.getConfig().isFlameEnabled() && EnchantmentHelper.getLevel(Enchantments.FLAME, crossbow) > 0)
            entity.setOnFireFor(100);
    }

    public static void applyInfinity(PersistentProjectileEntity entity, ItemStack crossbow, ItemStack projectile) {
        if (ConfigManager.getConfig().isInfinityEnabled() && EnchantmentHelper.getLevel(Enchantments.INFINITY, crossbow) > 0 && projectile.isOf(Items.ARROW))
            entity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
    }

    public static void applyPower(PersistentProjectileEntity entity, ItemStack crossbow) {
        if (ConfigManager.getConfig().isPowerEnabled()) {
            int powerLevel = EnchantmentHelper.getLevel(Enchantments.POWER, crossbow);
            entity.setDamage(entity.getDamage() + (double)powerLevel * 0.5 + 0.5);
        }
    }

    public static void applyPunch(PersistentProjectileEntity entity, ItemStack crossbow) {
        if (ConfigManager.getConfig().isPunchEnabled()) {
            int punchLevel = EnchantmentHelper.getLevel(Enchantments.PUNCH, crossbow);
            if (punchLevel > 0) entity.setPunch(punchLevel);
        }
    }
}
