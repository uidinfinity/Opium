package we.devs.opium.client.systems.modules.impl.hud;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.client.gui.DrawContext;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.renderer.RenderContext;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.HudModule;
import we.devs.opium.client.systems.modules.settings.impl.ModeSetting;
import we.devs.opium.client.utils.render.font.FontRenderer;

import static we.devs.opium.client.OpiumClient.mc;

public class Username extends HudModule {

    ModeSetting setting = modeSetting()
            .name("Extra")
            .description("Adds extra text")
            .defaultMode("None")
            .mode("Logged in as")
            .mode("on top!")
            .mode("None")
            .build();

    public Username() {
        hudBuilderOf(this)
                .pos(2, 2)
                .area(100, 10)
                .getBuilder()
                .name("Username")
                .description("leaked by 4asik with love <3")
                .settings(setting)
                .category(Category.HUD);
    }

    @Override
    public void render(DrawContext drawContext, float delta, RenderContext context) {
        FontRenderer.ColoredString text;
        if(setting.is("Logged in as")) {
            text = FontRenderer.ColoredString.of("Logged in as ", context.colorScheme().TEXT());
            text.add(mc.getSession().getUsername(), context.colorScheme().getLabelColor());
        } else {
            text = FontRenderer.ColoredString.of(mc.getSession().getUsername(), context.colorScheme().getLabelColor());
        }

        if(setting.is("on top!")) text.add(" on top!", context.colorScheme().TEXT());

        AtomicDouble tw = new AtomicDouble(width);
        AtomicDouble th = new AtomicDouble(height);
        Opium2D.drawTextHudBase(context, (float) x, (float) y, tw, th, text);
        width = tw.get();
        height = th.get();
    }
}
