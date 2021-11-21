package materialenergy.ultimate.upgrade.entities;


import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

public class MoltenProjRenderer<M extends MoltenProj> extends ProjectileEntityRenderer<MoltenProj> {

    public static final Identifier TEXTURE = new Identifier("ultimateupgrade:textures/entity/projectiles/molten_arrow.png");

    public static final Identifier TEXTURE_ = new Identifier("ultimateupgrade:textures/entity/projectiles/soul_arrow.png");

    public MoltenProjRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    public void render(M MoltenProj, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i){
        matrixStack.push();
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(g, MoltenProj.prevYaw, MoltenProj.getYaw()) - 90.0F));
        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(g, MoltenProj.prevPitch, MoltenProj.getPitch())));
        boolean j = false;
        float h = 0.0F;
        float k = 0.5F;
        float l = 0.0F;
        float m = 0.15625F;
        float n = 0.0F;
        float o = 0.15625F;
        float p = 0.15625F;
        float q = 0.3125F;
        float r = 0.05625F;
        float s = (float)MoltenProj.shake - g;
        if (s > 0.0F) {
            float t = -MathHelper.sin(s * 3.0F) * s;
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(t));
        }

        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(45.0F));
        matrixStack.scale(0.05625F, 0.05625F, 0.05625F);
        matrixStack.translate(-4.0D, 0.0D, 0.0D);
        VertexConsumer t = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutout(this.getTexture(MoltenProj)));
        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f matrix4f = entry.getModel();
        Matrix3f matrix3f = entry.getNormal();
        this.vertex(matrix4f, matrix3f, t, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, i);
        this.vertex(matrix4f, matrix3f, t, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, i);
        this.vertex(matrix4f, matrix3f, t, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, i);
        this.vertex(matrix4f, matrix3f, t, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, i);
        this.vertex(matrix4f, matrix3f, t, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, i);
        this.vertex(matrix4f, matrix3f, t, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, i);
        this.vertex(matrix4f, matrix3f, t, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, i);
        this.vertex(matrix4f, matrix3f, t, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, i);

        for(int u = 0; u < 4; ++u) {
            matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));
            this.vertex(matrix4f, matrix3f, t, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, i);
            this.vertex(matrix4f, matrix3f, t, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, i);
            this.vertex(matrix4f, matrix3f, t, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, i);
            this.vertex(matrix4f, matrix3f, t, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, i);
        }

        matrixStack.pop();
        super.render(MoltenProj, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(MoltenProj entity) {
        return entity.getSupercharged() ? TEXTURE_ : TEXTURE;
    }
}