package me.timvinci.crossbowenchants.mixins;

import me.timvinci.crossbowenchants.config.ConfigManager;
import me.timvinci.crossbowenchants.util.PersistentProjectileEntityHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// CrossbowItem mixin class.
@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {

    // Injecting into the createArrow method of the CrossbowItem class, provides us with the ability to modify the
    // projectile that's about to be shot out of the crossbow.
    // In our case, we use this method to apply the enchantment effects onto the projectile.
    @Inject(method = "createArrow", at = @At("TAIL"))
    private static void applyEnchantsToArrow(World world, LivingEntity shooter, ItemStack crossbow, ItemStack arrow, CallbackInfoReturnable<PersistentProjectileEntity> cir) {
        // As per usual, if the mod is disabled we don't intervene.
        if (!ConfigManager.getConfig().isEnabled())
            return;

        // If the PersistentProjectileEntity isn't some sort of arrow, meaning it's a firework,
        // We return, and by that refrain from applying enchantments to fireworks.
        PersistentProjectileEntity persistentProjectileEntity = cir.getReturnValue();
        if (!(persistentProjectileEntity instanceof ArrowEntity) && !(persistentProjectileEntity instanceof SpectralArrowEntity))
            return;

        // We can then apply the enchantment effects onto the projectile entity.
        // Note that the PersistentProjectileEntityHelper class will only apply the enchantment effect if the
        // corresponding setting is enabled, and if the crossbow actually has said enchantment.
        PersistentProjectileEntityHelper.applyFlameEnchant(persistentProjectileEntity, crossbow);
        PersistentProjectileEntityHelper.applyInfinityEnchant(persistentProjectileEntity, crossbow, arrow);
        PersistentProjectileEntityHelper.applyPowerEnchant(persistentProjectileEntity, crossbow);
        PersistentProjectileEntityHelper.applyPunchEnchant(persistentProjectileEntity, crossbow);
    }

    // SERVER-SIDE
    // The loadProjectile method is responsible for consuming the arrow when the crossbow is loaded.
    // By injecting into it, we can stop the consumption from happening in the first place.
    // In addition to that, since the client side will still display a fake consumption, even if it doesn't actually
    // happen, we send a slot update packet from this method.
    @Environment(EnvType.SERVER)
    @Inject(method = "loadProjectile", at = @At("HEAD"), cancellable = true)
    private static void retrieveArrowServerSide(LivingEntity shooter, ItemStack crossbow, ItemStack projectile, boolean simulated, boolean creative, CallbackInfoReturnable<Boolean> cir) {
        // If the player is in creative, this projectile is added by the Multishot enchantment, or the shooter isn't a player (pillager, skeleton..), we return.
        if (creative || simulated || !(shooter instanceof PlayerEntity))
            return;

        // If the mod is disabled, the infinity option is disabled, or the crossbow isn't enchanted with infinity,
        // we return.
        if (!ConfigManager.getConfig().isEnabled() || !ConfigManager.getConfig().isInfinityEnabled() || EnchantmentHelper.getLevel(Enchantments.INFINITY, crossbow) == 0)
            return;

        // If the projectile is an empty ItemStack, the crossbow's loading fails.
        // Otherwise, if the projectile is anything but an arrow, we return.
        if (projectile.isEmpty()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
        else if (!projectile.isOf(Items.ARROW))
            return;

        int arrowSlot = ((PlayerEntity)shooter).getInventory().getSlotWithStack(projectile);
        ItemStack itemStack = new ItemStack(Items.ARROW);

        // Send a slot update packet to stop the client from displaying fake consumption.
        ((ServerPlayerEntity)shooter).networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(-2, 0, arrowSlot, projectile));
        CrossbowItemAccessorMixin.putProjectile(crossbow, itemStack);
        cir.setReturnValue(true);
        cir.cancel();
    }

    // CLIENT-SIDE
    // This method is almost identical to the server side version, except for not sending any slot update packets.
    @Environment(EnvType.CLIENT)
    @Inject(method = "loadProjectile", at = @At("HEAD"), cancellable = true)
    private static void retrieveArrowClientSide(LivingEntity shooter, ItemStack crossbow, ItemStack projectile, boolean simulated, boolean creative, CallbackInfoReturnable<Boolean> cir) {
        // If the player is in creative, this projectile is added by the Multishot enchantment, or the shooter isn't a player (pillager, skeleton..), we return.
        if (creative || simulated || !(shooter instanceof PlayerEntity))
            return;

        // If the mod is disabled, the infinity option is disabled, or the crossbow isn't enchanted with infinity,
        // we return.
        if (!ConfigManager.getConfig().isEnabled() || !ConfigManager.getConfig().isInfinityEnabled() || EnchantmentHelper.getLevel(Enchantments.INFINITY, crossbow) == 0)
            return;

        // If the projectile is an empty ItemStack, the crossbow's loading fails.
        // Otherwise, if the projectile is anything but an arrow, we return.
        if (projectile.isEmpty()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
        else if (!projectile.isOf(Items.ARROW))
            return;

        ItemStack itemStack = new ItemStack(Items.ARROW);
        CrossbowItemAccessorMixin.putProjectile(crossbow, itemStack);
        cir.setReturnValue(true);
        cir.cancel();
    }

}





