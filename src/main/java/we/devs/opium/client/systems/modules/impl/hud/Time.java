package we.devs.opium.client.systems.modules.impl.hud;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.client.gui.DrawContext;
import we.devs.opium.client.managers.Managers;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.renderer.RenderContext;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.HudModule;
import we.devs.opium.client.systems.modules.settings.impl.ModeSetting;

public class Time extends HudModule {

    ModeSetting mode = modeSetting()
            .name("Mode")
            .description("leaked by 4asik with love <3")
            .defaultMode("min:hour")
            .mode("dd/mm/yyyy")
            .mode("m:h d. m")
            .mode("full")
            .mode("min:hour")
            .build();

    public Time() {
        hudBuilderOf(this)
                .pos(2, 2)
                .area(100, 10)
                .getBuilder()
                .name("Time")
                .description("leaked by 4asik with love <3")
                .category(Category.HUD)
                .settings("Settings", mode);
    }

    @Override
    public void render(DrawContext drawContext, float delta, RenderContext context) {
        String text = switch (mode.getCurrent()) {
            case "min:hour" -> Managers.VARIABLE.TIME$MINUTE_HOUR;
            case "dd/mm/yyyy" -> Managers.VARIABLE.TIME$DATE_MONTH_YEAR;
            case "m:h d. m" -> Managers.VARIABLE.TIME$MINUTE_HOUR_DATE_MONTH;
            case "full" -> Managers.VARIABLE.TIME$FULL;
            default -> throw new IllegalStateException("Unexpected value: " + mode.getCurrent());
        };

        AtomicDouble tw = new AtomicDouble(width);
        AtomicDouble th = new AtomicDouble(height);
        Opium2D.drawTextHudBase(context, (float) x, (float) y, tw, th, text);
        width = tw.get();
        height = th.get();
    }
}
