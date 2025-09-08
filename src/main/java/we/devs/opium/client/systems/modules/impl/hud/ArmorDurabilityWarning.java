package we.devs.opium.client.systems.modules.impl.hud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import we.devs.opium.client.render.renderer.RenderContext;
import we.devs.opium.client.render.ui.color.ThemeInfo;
import we.devs.opium.client.render.ui.gui.screens.HudConfigScreen;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.HudModule;
import we.devs.opium.client.utils.render.RenderUtil;

import static we.devs.opium.client.OpiumClient.mc;

// todo settings (durability threshold, text), im too lazy to do this rn
public class ArmorDurabilityWarning extends HudModule {
    public ArmorDurabilityWarning() {
        hudBuilderOf(this)
                .area(100, 14)
                .pos(2, 2)
                .getBuilder()
                .name("Armor warning")
                .description("leaked by 4asik with love <3")
                .category(Category.HUD);
    }

    @Override
    public void render(DrawContext drawContext, float delta, RenderContext context) {
        if(mc.currentScreen instanceof HudConfigScreen) {
            drawContext.drawBorder((int) x, (int) y, (int) width, (int) height, ThemeInfo.COLORSCHEME.getBorderColor().getRGB());
        }

        float lowestDura = 1f;
        for (ItemStack itemStack : mc.player.getInventory().armor) {
            if(!itemStack.isEmpty() && itemStack.getMaxDamage() != 0) {
                float dura = 1 - (float) itemStack.getDamage() / itemStack.getMaxDamage();
                if(lowestDura > dura) lowestDura = dura;
            }
        }

        if(lowestDura <= 0.5f) {
            RenderUtil.textRenderer.drawString(context.getMatrices(), "Repair your armor!!", x, y, ThemeInfo.COLORSCHEME.TEXT().getRGB());
        }
    }
}
