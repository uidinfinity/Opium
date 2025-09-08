package we.devs.opium.client.render.world.blocks;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.renderer.Opium3D;
import we.devs.opium.client.render.world.OpiumBlock;

import java.awt.*;
import java.util.function.Supplier;

public class ScaledBlock extends OpiumBlock {
    private final Supplier<Double> scaleFunc;

    public ScaledBlock(BlockPos pos, Color fill, Supplier<Double> scaleFunc) {
        this(pos, fill, fill, scaleFunc);
    }

    public ScaledBlock(BlockPos pos, Color fill, Color outline, Supplier<Double> scaleFunc) {
        super(pos, fill, outline);
        this.scaleFunc = scaleFunc;
    }

    @Override
    public void render(MatrixStack matrices) {
        double size = scaleFunc.get();
        double invertedSize = Math.abs(1 - size);
        Vec3d pos = Vec3d.of(this.pos).add(new Vec3d(1, 1, 1).multiply(invertedSize));
        Vec3d dimensions = new Vec3d(1, 1, 1).multiply((0.5 - invertedSize) * 2);

        Color fill = Opium2D.injectAlpha(this.fill, (int) (this.fill.getAlpha() * scaleFunc.get()));
        Color outline = Opium2D.injectAlpha(this.outline, (int) (this.outline.getAlpha() * scaleFunc.get()));

        Opium3D.renderEdged(matrices, fill, outline, pos, dimensions);
    }
}
