package we.devs.opium.client.systems.modules.impl.render;

import me.x150.renderer.render.Renderer3d;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3d;
import org.joml.Vector3f;
import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.ui.color.Colors;
import we.devs.opium.client.systems.events.Render3DEvent;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.utils.render.RenderUtil;
import we.devs.opium.client.utils.world.PosUtil;

import java.awt.*;

import static java.lang.Math.abs;
import static we.devs.opium.client.OpiumClient.mc;

public class Tracers extends ClientModule {
    public Tracers() {
        builder(this)
                .name("Tracers")
                .description("Draws lines to nearby players")
                .category(Category.RENDER);
    }

    @EventHandler
    private void render3D(Render3DEvent e) {
        Renderer3d.renderThroughWalls();
        float delta = e.getTickCounter().getTickDelta(true);
        for (Entity entity : mc.world.getEntities()) {
            if(!(entity instanceof PlayerEntity player)) continue;
            if(entity == mc.player) continue;

            Vector3f pos = new Vector3f(0, 0, 1);
            Vec3d center = new Vec3d(pos.x, -pos.y, pos.z)
                    .rotateX(-(float) Math.toRadians(mc.gameRenderer.getCamera().getPitch()))
                    .rotateY(-(float) Math.toRadians(mc.gameRenderer.getCamera().getYaw()))
                    .add(mc.gameRenderer.getCamera().getPos());

            Vector3d vec = new Vector3d();
            RenderUtil.set(vec, entity, delta);
            Vec3d lineEnd = new Vec3d(vec.x, vec.y, vec.z).add(0, entity.getHeight() / 2, 0);

            double distance = PosUtil.distanceBetween(mc.player.getPos(), lineEnd);
            Color color = getColor(distance);

            if(OpiumClient.friendSystem.isPlayerInSystem(player)) color = Colors.FRIEND;
            else if(OpiumClient.rageSystem.isPlayerInSystem(player)) color = Colors.RAGE;

            Renderer3d.renderLine(e.getMatrixStack(), Opium2D.injectAlpha(color, 150),
                    center, lineEnd);
        }
    }

    Color getColor(double distance) {
        if(distance > 50) return new Color(197, 194, 194);
        else if(distance > 25) return new Color(70, 204, 70);
        else return new Color(154, 33, 33);
    }
}
