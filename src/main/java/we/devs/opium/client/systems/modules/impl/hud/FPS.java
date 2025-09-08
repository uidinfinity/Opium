package we.devs.opium.client.systems.modules.impl.hud;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.client.gui.DrawContext;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.renderer.RenderContext;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.HudModule;
import we.devs.opium.client.utils.render.font.FontRenderer;

import static we.devs.opium.client.OpiumClient.mc;

public class FPS extends HudModule {

    public FPS() {
        hudBuilderOf(this)
                .pos(2, 2)
                .area(30, 13)
                .getBuilder() // ((((((((((((
                .name("Fps")
                .description("leaked by 4asik with love <3")
                .category(Category.HUD);
    }

    @Override
    public void render(DrawContext drawContext, float delta, RenderContext context) {
        int fps = mc.getCurrentFps();
        AtomicDouble nW = new AtomicDouble(width);
        AtomicDouble nH = new AtomicDouble(height);

        FontRenderer.ColoredString string = FontRenderer.ColoredString.of("FPS: ", context.colorScheme().getLabelColor());
        string.add(fps + "", context.colorScheme().MUTED_TEXT());
        Opium2D.drawTextHudBase(context, (float) x, (float) y, nW, nH, string);

        width = nW.get();
        height = nH.get();
    }
}
