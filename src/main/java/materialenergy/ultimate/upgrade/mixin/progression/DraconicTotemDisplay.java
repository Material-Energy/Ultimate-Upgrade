package materialenergy.ultimate.upgrade.mixin.progression;

import materialenergy.ultimate.upgrade.registry.UUItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayNetworkHandler.class)
public class DraconicTotemDisplay {
    @Final
    @Shadow
    private MinecraftClient client;

    @Inject(
            method = "getActiveTotemOfUndying",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void modifyGetActive(PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
        for (Hand hand : Hand.values()) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (!itemStack.isOf(UUItems.DRACONIC_TOTEM)) continue;
            cir.setReturnValue(itemStack);
        }
    }

    @Redirect(
            method = "onEntityStatus",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleManager;addEmitter(Lnet/minecraft/entity/Entity;Lnet/minecraft/particle/ParticleEffect;I)V")
    )
    private void setTpParticle(ParticleManager instance, Entity entity, ParticleEffect parameters, int maxAge){
        PlayerEntity entity1 = (PlayerEntity) entity;
        ItemStack itemStack = ItemStack.EMPTY;
        for (Hand hand : Hand.values()) {
            itemStack = entity1.getStackInHand(hand);
            if (itemStack.isOf(UUItems.DRACONIC_TOTEM)) {
                break;
            }
            if (itemStack.isOf(Items.TOTEM_OF_UNDYING)) {
                break;
            }
        }
        if (itemStack.isOf(UUItems.DRACONIC_TOTEM)){
            this.client.particleManager.addEmitter(entity, ParticleTypes.REVERSE_PORTAL, 30);
        } else if (itemStack.isOf(Items.TOTEM_OF_UNDYING)){
            this.client.particleManager.addEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, 30);
        }

    }



}
