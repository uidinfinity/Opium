package we.devs.opium.client.mixin.mixins;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import we.devs.opium.client.mixin.iinterface.IModelPart;

import java.util.Map;

@Mixin(ModelPart.class)
public abstract class ModelPartMixin implements IModelPart {

    @Shadow public abstract void forEachCuboid(MatrixStack matrices, ModelPart.CuboidConsumer consumer);

    @Shadow @Final private Map<String, ModelPart> children;

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V", at = @At("HEAD"), cancellable = true)
    void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color, CallbackInfo ci) {
//        Pulse3D.renderThroughWalls();
//        this.forEachCuboid(matrices, (matrix, path, index, cuboid) -> {
//            ModelRenderer.renderCuboid(cuboid, matrices);
//        });
//        Pulse3D.stopRenderThroughWalls();
    }
}
