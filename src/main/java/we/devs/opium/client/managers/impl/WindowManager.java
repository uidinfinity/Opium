package we.devs.opium.client.managers.impl;

import org.jetbrains.annotations.Nullable;
import we.devs.opium.client.managers.Manager;
import we.devs.opium.client.render.ui.color.ColorScheme;
import we.devs.opium.client.render.ui.gui.screens.ColorScreen;
import we.devs.opium.client.render.ui.gui.screens.HudConfigScreen;
import we.devs.opium.client.render.ui.gui.PulseScreen;
import we.devs.opium.client.render.ui.gui.screens.MainScreen;
import we.devs.opium.client.render.ui.gui.screens.ModuleScreen;

public class WindowManager extends Manager<PulseScreen> {

    public WindowManager() {
        super("WindowManager");

        addItem(new PulseScreen("a"));
        addItem(new MainScreen());
        addItem(new ModuleScreen());
        addItem(new HudConfigScreen());
        addItem(new ColorScreen(null, null));
    }

    public void applyTheme(ColorScheme theme) {
        for (PulseScreen pulseWindow : itemList) {
            pulseWindow.setColorScheme(theme);
        }
    }

    public void apply(WindowAction action) {
        for (PulseScreen pulseWindow : itemList) {
            action.run(pulseWindow);
        }
    }

    public interface WindowAction {
        void run(PulseScreen window);
    }

    @Override
    public @Nullable PulseScreen getItemByClass(Class<? extends PulseScreen> clazz) {
        @Nullable PulseScreen a = super.getItemByClass(clazz);
        if(a != null) a.reset();
        return a;
    }
}
