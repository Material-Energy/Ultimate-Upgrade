package materialenergy.ultimate.upgrade.entities;

import materialenergy.ultimate.upgrade.registry.Registries;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class DraconicShardRenderer<M extends Entity> extends ProjectileEntityRenderer<DraconicShard> {
    public ItemStack OBI = new ItemStack(Items.OBSIDIAN);
    public ItemStack CRY_OBI = new ItemStack(Items.CRYING_OBSIDIAN);
    public static final Identifier TEXTURE = Registries.id("textures/entity/projectiles/draconic_shard.png");

    public DraconicShardRenderer(EntityRendererFactory.Context context) {
        super(context);
    }



    @Override
    public Identifier getTexture(DraconicShard entity) {
        return TEXTURE;
    }
}
