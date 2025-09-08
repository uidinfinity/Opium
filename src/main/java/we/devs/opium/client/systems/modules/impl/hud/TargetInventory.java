package we.devs.opium.client.systems.modules.impl.hud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.renderer.RenderContext;
import we.devs.opium.client.render.ui.color.ThemeInfo;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.HudModule;
import we.devs.opium.client.systems.modules.settings.impl.BooleanSetting;
import we.devs.opium.client.utils.annotations.ExcludeModule;
import we.devs.opium.client.utils.entity.EntityFinder;
import we.devs.opium.client.utils.render.RenderUtil;

import static we.devs.opium.client.OpiumClient.mc;

@ExcludeModule
public class TargetInventory extends HudModule {

    static float size = 16;

    BooleanSetting ignoreFriends = booleanSetting()
            .name("Ignore friends")
            .description("Ignore friends")
            .build();

    public TargetInventory() {
        hudBuilderOf(this)
                .area(2 + (size + 1) * 9, 2 + (size + 1) * 3)
                .getBuilder()
                .name("Target Inv")
                .description("leaked by 4asik with love <3")
                .settings(ignoreFriends)
                .category(Category.HUD);
    }

    float scale = 1f;
    @Override
    public void render(DrawContext drawContext, float delta, RenderContext context) {
        float spacing = 1;

        PlayerInventory inventory = null;
        String name = "??";
        EntityFinder.EntityList list = EntityFinder.findEntitiesInRange(128, mc.player.getPos());

        double distance = Double.MAX_VALUE;
        for (Entity entity : list.get()) {
            if(!(entity instanceof PlayerEntity)) continue;
            if(entity == mc.player || OpiumClient.friendSystem.isPlayerInSystem((PlayerEntity) entity)) continue;
            double eD = entity.getPos().distanceTo(mc.player.getPos());

            if(eD < distance) {
                distance = eD;
                inventory = ((PlayerEntity) entity).getInventory();
                name = ((PlayerEntity) entity).getGameProfile().getName();
            }
        }

        if(inventory == null) return;

        RenderUtil.textRenderer.drawString(context.getMatrices(), name + "'s inventory:", x + 0.2F, y - 1 - RenderUtil.textRenderer.getHeight(name + "'s inventory:"), ThemeInfo.COLORSCHEME.TEXT().getRGB());
        Opium2D.drawHudBase(context.getMatrices(), (float) x, (float) y, (float) width, (float) height, Opium2D.cornerRad, 0.85f);
        int slot = 9;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                float x1 = (float) (x + j * size * scale + j * spacing * scale + 1);
                float y1 = (float) (y + i * size * scale + i * spacing * scale + 1);
                ItemStack stack = inventory.getStack(slot);
                RenderUtil.drawItem(drawContext, stack, (int) x1, (int) y1, scale, false);
                slot++;
            }
        }
    }
}
