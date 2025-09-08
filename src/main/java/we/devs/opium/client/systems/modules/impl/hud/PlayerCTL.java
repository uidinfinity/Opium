package we.devs.opium.client.systems.modules.impl.hud;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.client.gui.DrawContext;
import we.devs.opium.client.managers.Managers;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.renderer.RenderContext;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.HudModule;
import we.devs.opium.client.utils.render.font.FontRenderer;

public class PlayerCTL extends HudModule {

    boolean scroll = true;
    public PlayerCTL() {
        hudBuilderOf(this)
                .area(100, 20)
                .getBuilder()
                .name("Playerctl")
                .description("leaked by 4asik with love <3")
                .category(Category.HUD);
    }

    @Override
    public void render(DrawContext drawContext, float delta, RenderContext context) {
        AtomicDouble w = new AtomicDouble(width);
        AtomicDouble h = new AtomicDouble(height);
        FontRenderer.ColoredString text = FontRenderer.ColoredString.of("Playing: ", context.colorScheme().TEXT());
        text.add(Managers.VARIABLE.SONGDATA$SONG, context.colorScheme().getLabelColor());
        text.add(" by ", context.colorScheme().TEXT());
        text.add(Managers.VARIABLE.SONGDATA$ARTIST, context.colorScheme().getLabelColor());
        Opium2D.drawTextHudBase(context, (float) x, (float) y, w, h, text);
        this.width = w.get();
        this.height = h.get();
    }
}
