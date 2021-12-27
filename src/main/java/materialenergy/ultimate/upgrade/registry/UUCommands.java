package materialenergy.ultimate.upgrade.registry;

import materialenergy.ultimate.upgrade.commands.ChargeItem;
import materialenergy.ultimate.upgrade.commands.InstantHeal;

public class UUCommands {
    public static void init(){
        InstantHeal.initialize();
        ChargeItem.initialize();
    }
}
