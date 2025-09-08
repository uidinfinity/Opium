package we.devs.opium.client.systems.modules.impl.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.renderer.RenderContext;
import we.devs.opium.client.render.ui.color.ThemeInfo;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.HudModule;
import we.devs.opium.client.utils.render.RenderUtil;

import java.util.ArrayList;
import java.util.List;

import static we.devs.opium.client.OpiumClient.mc;

public class BetterHotbar extends HudModule {

    static float size = 16;

    public BetterHotbar() {
        hudBuilderOf(this)
                .area(2 + (size + 2) * 9, 3 + size)
                .getBuilder()
                .name("Hotbar")
                .description("leaked by 4asik with love <3")
                .category(Category.HUD);
    }

    @Override
    public void render(DrawContext drawContext, float delta, RenderContext context) {
        List<ItemStack> hotBar = new ArrayList<>();
        for (int i = 0; i <= 8; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            hotBar.add(stack);
        }
        ItemStack offhand = mc.player.getOffHandStack();

        Opium2D.drawHudBase(context.getMatrices(), (float) x, (float) y, (float) width, (float) height, Opium2D.cornerRad, 0.85f);
        Opium2D.drawHudBase(context.getMatrices(), (float) (x - size - 4 - 1), (float) y, size + 2, (float) height, Opium2D.cornerRad, 0.85f);
        int i = 0;
        for (ItemStack stack : hotBar) {
            float nX = (float) (x + (size + 2) * i + 1);
            if(i != mc.player.getInventory().selectedSlot) RenderSystem.setShaderColor(0.35f, 0.35f, 0.35f, 1f);
            RenderUtil.drawItem(drawContext, stack, (int) nX, (int) y + 1, 1f, true);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            i++;
        }

        if(!offhand.isEmpty())
            RenderUtil.drawItem(drawContext, offhand, (int) (x - size - 4), (int) y + 1, 1f, true);
    }

    public static void renderHealth(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking) {
        Opium2D.drawRound(context.getMatrices(), x - 1, y - 10, maxHealth * 2 + 2, 16, Math.max(1.5f, Opium2D.cornerRad),
                ThemeInfo.COLORSCHEME.getBorderColor()
        );

        Opium2D.drawRound(context.getMatrices(), x, y - 9, maxHealth * 2, 14, Math.max(1.5f, Opium2D.cornerRad),
                ThemeInfo.COLORSCHEME.SECONDARY()
        );
        Opium2D.drawRound(context.getMatrices(), x, y - 9, health * 2, 14, Math.max(1.5f, Opium2D.cornerRad),
                ThemeInfo.COLORSCHEME.ACCENT()
        );

        String s = "" + health;

        if(absorption > 0) s += " + " + absorption;

        RenderUtil.textRenderer.drawString(context.getMatrices(),
                s, x + (maxHealth - RenderUtil.textRenderer.getWidth(s)) / 2,
                (double) (y + (8 - RenderUtil.textRenderer.getHeight(s)) / 2) + RenderUtil.fontOffsetY, ThemeInfo.COLORSCHEME.TEXT().getRGB());
    }
}
