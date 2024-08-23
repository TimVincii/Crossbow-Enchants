package me.timvinci.crossbowenchants.mixins;

import me.timvinci.crossbowenchants.config.ConfigManager;
import me.timvinci.crossbowenchants.util.EnchantmentApplier;
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

    // Applying the enchants to the arrow via injecting the createArrow method.
    @Inject(method = "createArrow", at = @At("TAIL"))
    private static void applyEnchantsToArrow(World world, LivingEntity shooter, ItemStack crossbow, ItemStack arrow, CallbackInfoReturnable<PersistentProjectileEntity> cir) {
        // Returning if Crossbow Enchants is disabled.
        if (!ConfigManager.getConfig().isEnabled())
            return;

        // Returning if the projectile isn't an arrow.
        PersistentProjectileEntity persistentProjectileEntity = cir.getReturnValue();
        if (!(persistentProjectileEntity instanceof ArrowEntity) && !(persistentProjectileEntity instanceof SpectralArrowEntity))
            return;

        // Applying the enchantments onto the arrow.
        EnchantmentApplier.applyFlame(persistentProjectileEntity, crossbow);
        EnchantmentApplier.applyInfinity(persistentProjectileEntity, crossbow, arrow);
        EnchantmentApplier.applyPower(persistentProjectileEntity, crossbow);
        EnchantmentApplier.applyPunch(persistentProjectileEntity, crossbow);
    }

    // SERVER-SIDE
    // Stopping the consumption of the arrow if the crossbow is enchanted with infinity, via injecting the
    // loadProjectile method.
    @Environment(EnvType.SERVER)
    @Inject(method = "loadProjectile", at = @At("HEAD"), cancellable = true)
    private static void stopArrowConsumption(LivingEntity shooter, ItemStack crossbow, ItemStack projectile, boolean simulated, boolean creative, CallbackInfoReturnable<Boolean> cir) {
        // Returning if the player is in creative, the projectile is added by the multishot enchantment, or the shooter
        // isn't a player (pillager, skeleton..).
        if (creative || simulated || !(shooter instanceof PlayerEntity))
            return;

        // Returning if Crossbow Enchants is disabled, the infinity enchantment is disabled, or the crossbow isn't
        // enchanted with infinity.
        if (!ConfigManager.getConfig().isEnabled() || !ConfigManager.getConfig().isInfinityEnabled() || EnchantmentHelper.getLevel(Enchantments.INFINITY, crossbow) == 0)
            return;

        // Returning false if the projectile is an empty stack.
        if (projectile.isEmpty()) {
            cir.setReturnValue(false);
            cir.cancel();
        } // Returning if the projectile isn't a regular arrow.
        else if (!projectile.isOf(Items.ARROW))
            return;

        // Getting the player's inventory slot in which the projectile is in.
        int arrowSlot = ((PlayerEntity)shooter).getInventory().getSlotWithStack(projectile);
        // Creating a dummy arrow stack.
        ItemStack itemStack = new ItemStack(Items.ARROW);

        // Send a slot update packet to stop the client from displaying fake consumption.
        ((ServerPlayerEntity)shooter).networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(-2, 0, arrowSlot, projectile));
        // Passing the dummy arrow to the put projectile method.
        CrossbowItemAccessorMixin.putProjectile(crossbow, itemStack);
        cir.setReturnValue(true);
        cir.cancel();
    }

    // CLIENT-SIDE
    // This method is identical to the server side version, except for not sending any slot update packets.
    @Environment(EnvType.CLIENT)
    @Inject(method = "loadProjectile", at = @At("HEAD"), cancellable = true)
    private static void stopArrowConsumptionClient(LivingEntity shooter, ItemStack crossbow, ItemStack projectile, boolean simulated, boolean creative, CallbackInfoReturnable<Boolean> cir) {
        // Returning if the player is in creative, the projectile is added by the multishot enchantment, or the shooter
        // isn't a player (pillager, skeleton..).
        if (creative || simulated || !(shooter instanceof PlayerEntity))
            return;

        // Returning if Crossbow Enchants is disabled, the infinity enchantment is disabled, or the crossbow isn't
        // enchanted with infinity.
        if (!ConfigManager.getConfig().isEnabled() || !ConfigManager.getConfig().isInfinityEnabled() || EnchantmentHelper.getLevel(Enchantments.INFINITY, crossbow) == 0)
            return;

        // Returning false if the projectile is an empty stack.
        if (projectile.isEmpty()) {
            cir.setReturnValue(false);
            cir.cancel();
        } // Returning if the projectile isn't a regular arrow.
        else if (!projectile.isOf(Items.ARROW))
            return;

        // Creating a dummy arrow stack.
        ItemStack itemStack = new ItemStack(Items.ARROW);
        // Passing the dummy arrow to the put projectile method.
        CrossbowItemAccessorMixin.putProjectile(crossbow, itemStack);
        cir.setReturnValue(true);
        cir.cancel();
    }
}





