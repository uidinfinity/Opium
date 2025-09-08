package we.devs.opium.client.render.ui.gui.screens;

import we.devs.opium.client.render.ui.gui.PulseScreen;
import we.devs.opium.client.render.ui.gui.widgets.color.ColorWidget;
import we.devs.opium.client.systems.modules.settings.impl.ColorSetting;

import java.awt.*;

import static we.devs.opium.client.OpiumClient.mc;

// todo
public class BetterColorScreen extends PulseScreen {
    private final PulseScreen prev;
    private final ColorSetting setting;

    public BetterColorScreen(PulseScreen prev, ColorSetting setting) {
        super("color");
        this.prev = prev;
        this.setting = setting;
    }

    @Override
    protected void init() {
        shouldClose = false;
        super.init();
        addWidget(new ColorWidget(10, 10, 50, 16, Color.BLACK, Color.RED));
        addWidget(new ColorWidget(10, 30, 50, 16, Color.BLACK, Color.GREEN));
        addWidget(new ColorWidget(10, 40, 50, 16, Color.BLACK, Color.BLUE));
    }

    @Override
    public void close() {
        super.close();
        mc.setScreen(prev);
    }
}
