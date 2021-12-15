package materialenergy.ultimate.upgrade.registry;

import materialenergy.ultimate.upgrade.effects.FireWeakness;
import materialenergy.ultimate.upgrade.effects.VoidLeech;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.registry.Registry;

public class UUEffects {
    public static final StatusEffect FIRE_WEAKNESS = register("incendiary",new FireWeakness());
    public static final StatusEffect VOID_LEECH = register("void_leech",new VoidLeech());

    private static StatusEffect register( String id, StatusEffect entry) {
        return Registry.register(Registry.STATUS_EFFECT,Registries.id(id),entry);
    }

    public static void init(){

    }
}
