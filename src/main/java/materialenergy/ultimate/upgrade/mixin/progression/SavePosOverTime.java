package materialenergy.ultimate.upgrade.mixin.progression;

import materialenergy.ultimate.upgrade.api.GetPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.List;

@Mixin(LivingEntity.class)
public abstract class SavePosOverTime extends Entity implements GetPos {

    public List<Vec3d> posList = Collections.emptyList();

    public SavePosOverTime(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    public void savePos(CallbackInfo ci){
        posList.add(this.getPos());
        if (posList.size() > 60 * 20){
            posList.remove(0);
        }
    }

    @Override
    public Vec3d getPosAt(int secondsBefore) {
        if (secondsBefore * 20 <= posList.size() && secondsBefore > 0) return posList.get(secondsBefore * 20);
        return null;
    }

    @Override
    public int getTotalSeconds() {
        return posList.size() / 20;
    }
}
