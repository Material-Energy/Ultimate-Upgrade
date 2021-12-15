package materialenergy.ultimate.upgrade.commands;


import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.literal;

public class InstantHeal {

    public static void initialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> dispatcher.register(literal("heal").executes(context -> {
            ServerPlayerEntity player = context.getSource().getPlayer();
            player.heal(player.getMaxHealth() - player.getHealth());
            return 1;
        })));
    }
}
