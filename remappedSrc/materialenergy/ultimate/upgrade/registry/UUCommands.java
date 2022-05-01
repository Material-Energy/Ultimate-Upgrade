package materialenergy.ultimate.upgrade.common.registry;

import materialenergy.ultimate.upgrade.common.commands.ChargeItem;
import materialenergy.ultimate.upgrade.common.commands.InstantHeal;

public class UUCommands {
    public static void init(){
        InstantHeal.initialize();
        ChargeItem.initialize();
    }
}
