package we.devs.opium.client.render.world.blocks;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.renderer.Opium3D;
import we.devs.opium.client.render.world.OpiumBlock;

import java.awt.*;

public class FadeInBlock extends OpiumBlock {
    public FadeInBlock(BlockPos pos, Color fill) {
        super(pos, fill);
    }

    public FadeInBlock(BlockPos pos, Color fill, Color outline) {
        super(pos, fill, outline);
    }

    public FadeInBlock(BlockPos pos, Color fill, Color outline, double fadeTime) {
        super(pos, fill, outline, fadeTime);
    }

    boolean fadeInFinished = false;
    @Override
    public void render(MatrixStack matrices) {
        Opium3D.renderThroughWalls();
        if(!animation.hasEnded()) {
            Color fill = Opium2D.injectAlpha(this.fill, animation.getInt(10, this.fill.getAlpha()));
            Opium3D.renderEdged(matrices, fill, outline, Vec3d.of(pos), new Vec3d(1, 1, 1));
        }
        Opium3D.stopRenderThroughWalls();
    }
}
