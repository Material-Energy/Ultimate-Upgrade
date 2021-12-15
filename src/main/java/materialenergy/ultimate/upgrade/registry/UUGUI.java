package materialenergy.ultimate.upgrade.registry;

import materialenergy.ultimate.upgrade.gui.MoltenAnvilScreen;
import materialenergy.ultimate.upgrade.gui.MoltenAnvilScreenHandler;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class UUGUI{

    public static ScreenHandlerType<MoltenAnvilScreenHandler> MOLTEN_ANVIL = register("molten_anvil", MoltenAnvilScreenHandler::new, MoltenAnvilScreen::new);

    public static<T extends ScreenHandler,H extends Screen & ScreenHandlerProvider<T>> ScreenHandlerType<T> register(String id, ScreenHandlerRegistry.SimpleClientHandlerFactory<ScreenHandler> factory, ScreenRegistry.Factory<T,H> screenFactory){
        ScreenHandlerType<T> screenHandlerType = (ScreenHandlerType<T>) ScreenHandlerRegistry.registerSimple(Registries.id(id),factory);
        ScreenRegistry.register(screenHandlerType, screenFactory);
        return screenHandlerType;
    }

    public static void init(){

    }

}
