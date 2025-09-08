package we.devs.opium.client.systems.modules.impl.hud;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.client.gui.DrawContext;
import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.renderer.RenderContext;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.HudModule;
import we.devs.opium.client.systems.modules.settings.impl.BooleanSetting;
import we.devs.opium.client.utils.render.font.FontRenderer;

public class Watermark extends HudModule {

    BooleanSetting showUsername = booleanSetting()
            .description("leaked by 4asik with love <3")
            .name("Show username")
            .build();

    BooleanSetting showAuthor = booleanSetting()
            .description("leaked by 4asik with love <3")
            .name("Show author")
            .build();

    public Watermark() {
        hudBuilderOf(this)
                .pos(2, 2)
                .area(100, 10)
                .getBuilder()
                .name("Watermark")
                .description("leaked by 4asik with love <3")
                .category(Category.HUD)
                .settings(showAuthor);
    }

    @Override
    public void render(DrawContext drawContext, float delta, RenderContext context) {
        FontRenderer.ColoredString text = FontRenderer.ColoredString.of(OpiumClient.NAME + " ", context.colorScheme().getLabelColor());
        text.add(OpiumClient.VERSION, context.colorScheme().TEXT());
        if(showAuthor.isEnabled()) {
            text.add(" by ", context.colorScheme().TEXT());
            text.add(OpiumClient.AUTHOR, context.colorScheme().getLabelColor());
        }

        AtomicDouble tw = new AtomicDouble(width);
        AtomicDouble th = new AtomicDouble(height);
        Opium2D.drawTextHudBase(context, (float) x, (float) y, tw, th, text);
        width = tw.get();
        height = th.get();
    }
}
