package materialenergy.ultimate.upgrade.common.registry;

import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.StatType;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class UUStats {
    public static final Identifier INTERACT_WITH_MOLTEN_ANVIL = register("interact_with_molten_anvil", StatFormatter.DEFAULT);


    private static Identifier register(String id, StatFormatter formatter) {
        Identifier identifier = new Identifier(id);
        Registry.register(Registry.CUSTOM_STAT, id, identifier);
        Stats.CUSTOM.getOrCreateStat(identifier, formatter);
        return identifier;
    }

    private static <T> StatType<T> registerType(String id, Registry<T> registry) {
        return Registry.register(Registry.STAT_TYPE, id, new StatType<>(registry));
    }
}
