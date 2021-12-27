package materialenergy.ultimate.upgrade.commands;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import materialenergy.ultimate.upgrade.item.draconic.DraconicBaseItem;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import org.lwjgl.system.CallbackI;

public class ChargeItem {

    public static void initialize(){
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> dispatcher.register(literal("charge").then(argument("amount", IntegerArgumentType.integer()).executes(context -> {
            ServerPlayerEntity player = context.getSource().getPlayer();
            int amount = IntegerArgumentType.getInteger(context, "amount");
            ItemStack stack = player.getMainHandStack();
            if (stack.getItem() instanceof DraconicBaseItem) {
                if (amount >= 0 && amount <= ((DraconicBaseItem)stack.getItem()).getTotalEnergy()){
                    DraconicBaseItem.writeEnderEnergy(stack, (short) amount);
                    context.getSource().sendFeedback(new TranslatableText("commands.charge.successful", stack.getName()), true);
                    return 1;
                } else {
                    context.getSource().sendFeedback(new TranslatableText("commands.charge.fail.out_of_bounds"), true);
                    return 0;
                }
            } else {
                context.getSource().sendFeedback(new TranslatableText("commands.charge.fail.unchargeable"), true);
                return 0;
            }
        }))));
    }
}
