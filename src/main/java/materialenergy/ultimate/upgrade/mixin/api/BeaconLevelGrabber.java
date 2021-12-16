package materialenergy.ultimate.upgrade.mixin.api;

import net.minecraft.block.entity.BeaconBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BeaconBlockEntity.class)
public interface BeaconLevelGrabber {
    @Accessor("level")
    int getLevel();
}
