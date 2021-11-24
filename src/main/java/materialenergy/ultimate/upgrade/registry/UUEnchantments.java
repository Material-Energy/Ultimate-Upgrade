package materialenergy.ultimate.upgrade.registry;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class UUEnchantments {



    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registry.ENCHANTMENT, new Identifier("ultimateupgrade",name), enchantment);
    }

    public static void init(){

    }
}
