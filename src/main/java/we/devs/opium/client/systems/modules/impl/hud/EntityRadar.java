package we.devs.opium.client.systems.modules.impl.hud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.renderer.RenderContext;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.HudModule;
import we.devs.opium.client.systems.modules.settings.impl.BooleanSetting;
import we.devs.opium.client.systems.modules.settings.impl.NumberSetting;
import we.devs.opium.client.utils.entity.EntityFinder;

import java.awt.*;

import static we.devs.opium.client.OpiumClient.mc;

public class EntityRadar extends HudModule {
    BooleanSetting players = booleanSetting()
            .name("Show players")
            .description("leaked by 4asik with love <3")
            .build();

    BooleanSetting mobs = booleanSetting()
            .name("Show hostile")
            .description("leaked by 4asik with love <3")
            .build();

    BooleanSetting passive = booleanSetting()
            .name("Show passive")
            .description("leaked by 4asik with love <3")
            .build();

    BooleanSetting misc = booleanSetting()
            .name("Show misc")
            .description("leaked by 4asik with love <3")
            .build();

    NumberSetting range = numberSetting()
            .range(0, 30)
            .name("Range")
            .description("leaked by 4asik with love <3")
            .defaultValue(15)
            .build();

    public EntityRadar() {
        hudBuilderOf(this)
                .pos(2, 2)
                .area(50, 50)
                .getBuilder()
                .name("EntityRadar")
                .description("leaked by 4asik with love <3")
                .settings("Targets", players, mobs)
                .settings("Range", range)
//                .settings(players, entities, livingEntities)
                .category(Category.HUD);
    }

    @Override
    public void render(DrawContext drawContext, float delta, RenderContext context) {
        double centerX = this.x + this.width / 2;
        double centerY = this.y + this.height / 2;
        Opium2D.drawHudBase(context.getMatrices(), (float) x, (float) y, (float) width, (float) height, 20, 0.85f);
        Opium2D.drawRound(context.getMatrices(), (int) (centerX-1), (int) (centerY-1), 3, 3, Opium2D.cornerRad, Color.MAGENTA.darker());
        EntityFinder.EntityList playerList = EntityFinder.findEntitiesIn2DRange(range.getValue(), mc.player.getPos());
        int i = 0;
        Vec3d playerPos = mc.player.getPos();
        for (Entity entity : playerList.get()) {
            double distance = playerPos.distanceTo(new Vec3d(entity.getX(), playerPos.getY(), entity.getZ()));
            double angle = Math.atan2(entity.getZ() - mc.player.getZ(), entity.getX() - mc.player.getX()) - Math.toRadians(mc.player.getYaw()) - Math.PI;
            float x2 = (float) (Math.cos(angle) * (distance / range.getValue()) * ((float) this.width / 2));
            float y2 = (float) (Math.sin(angle) * (distance / range.getValue()) * ((float) this.width / 2));
            int drawX = (int) (centerX + x2);
            int drawY = (int) (centerY + y2);

            Color color = Color.GRAY;

            if(entity == mc.player) continue;
            else if(entity instanceof PlayerEntity){
                if(!players.isEnabled()) continue;
                color = Color.CYAN;
            }
            else if(entity instanceof PassiveEntity) {
                if(!passive.isEnabled()) continue;
                color = Color.GREEN;
            }
            else if(entity instanceof MobEntity) {
                if(!mobs.isEnabled()) continue;
                color = Color.RED;
            } else if(!misc.isEnabled()) continue;


            Opium2D.drawRound(context.getMatrices(), drawX-1, drawY - 1, 3, 3, Opium2D.cornerRad, color);
            i++;
        }
    }
}
