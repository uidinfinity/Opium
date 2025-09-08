package we.devs.opium.client.render.world;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import we.devs.opium.client.render.renderer.Opium3D;
import we.devs.opium.client.utils.render.AnimationUtil;

import java.awt.*;

public class OpiumBlock {

    protected final BlockPos pos;
    protected final Color fill;
    protected final Color outline;
    protected final double fadeTime;
    protected final AnimationUtil animation;

    public OpiumBlock(BlockPos pos, Color fill) {
        this(pos, fill, fill.darker());
    }

    public OpiumBlock(BlockPos pos, Color fill, Color outline) {
        this(pos, fill, outline, 200);
    }

    public OpiumBlock(BlockPos pos, Color fill, Color outline, double fadeTime) {
        this.pos = pos;
        this.fill = fill;
        this.outline = outline;
        this.fadeTime = fadeTime;
        this.animation = new AnimationUtil(0, (long) fadeTime);
    }

    public void render(MatrixStack matrices) {
        Opium3D.renderThroughWalls();
        Opium3D.renderEdged(matrices, fill, outline, Vec3d.of(pos), new Vec3d(1, 1, 1));
    }
}
