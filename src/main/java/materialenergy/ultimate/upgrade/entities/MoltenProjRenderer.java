package materialenergy.ultimate.upgrade.entities;


import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.Identifier;

public class MoltenProjRenderer<M extends ArrowEntity> extends EntityRenderer<MoltenProj> {
    public static final Identifier TEXTURE = new Identifier("ultimateupgrade:textures/entity/projectiles/molten_arrow.png");

    public MoltenProjRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(MoltenProj entity) {
        return TEXTURE;
    }
}