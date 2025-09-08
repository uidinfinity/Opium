package we.devs.opium.client.render.world.blocks;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.renderer.Opium3D;
import we.devs.opium.client.render.world.OpiumBlock;

import java.awt.*;

public class FillAnimationBlock extends OpiumBlock {
    public FillAnimationBlock(BlockPos pos, Color fill) {
        super(pos, fill);
    }

    public FillAnimationBlock(BlockPos pos, Color fill, Color outline) {
        super(pos, fill, outline);
    }

    public FillAnimationBlock(BlockPos pos, Color fill, Color outline, double fadeTime) {
        super(pos, fill, outline, fadeTime);
    }

    @Override
    public void render(MatrixStack matrices) {
        Opium3D.renderThroughWalls();
        if(!animation.hasEnded()) {
            double size = animation.getDouble(0.5, 1);
            double invertedSize = Math.abs(1 - size);
            Vec3d pos = Vec3d.of(this.pos).add(new Vec3d(1, 1, 1).multiply(invertedSize));
            Vec3d dimensions = new Vec3d(1, 1, 1).multiply((0.5 - invertedSize) * 2);

            Color fill = Opium2D.injectAlpha(this.fill, animation.getInt(40, this.fill.getAlpha()));
            Color outline = Opium2D.injectAlpha(this.outline, animation.getInt(40, this.outline.getAlpha()));

            Opium3D.renderEdged(matrices, fill, outline, pos, dimensions);
        } else {
            Opium3D.renderEdged(matrices, fill, outline, Vec3d.of(pos), new Vec3d(1, 1, 1));
        }
        Opium3D.stopRenderThroughWalls();
    }
}
