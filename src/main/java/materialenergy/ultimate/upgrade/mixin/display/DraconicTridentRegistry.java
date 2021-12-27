package materialenergy.ultimate.upgrade.mixin.display;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelLoader.class)
public abstract class DraconicTridentRegistry {

    @Inject(
            method = "<init>",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V"
            )
    )
    public void init(ResourceManager resourceManager, BlockColors blockColors, Profiler profiler, int i, CallbackInfo ci){
        profiler.swap("special");
        this.addModel(new ModelIdentifier("ultimateupgrade:draconic_trident_in_hand#inventory"));
    }

    @Shadow
    protected abstract void addModel(ModelIdentifier modelIdentifier);
}
