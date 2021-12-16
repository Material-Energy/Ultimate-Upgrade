package materialenergy.ultimate.upgrade.api;

import net.minecraft.util.math.Vec3d;

public interface GetPos {
    Vec3d getPosAt(int secondsBefore);
    int getTotalSeconds();
}
