package we.devs.opium.client.managers.impl;

import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.managers.Manager;
import we.devs.opium.client.render.ui.color.ColorScheme;
import we.devs.opium.client.render.ui.color.Colors;
import we.devs.opium.client.render.ui.color.ThemeInfo;
import we.devs.opium.client.utils.thread.ThreadManager;

public class ThemeManager extends Manager<ColorScheme> {

    ColorScheme activeTheme = Colors.DEFAULT;

    public ThemeManager() {
        super("ThemeManager");

        addItem(Colors.GRUVBOX_DARK_ORANGE);
        addItem(Colors.GRUVBOX_DARK_BLUE);
        addItem(Colors.GRUVBOX_DARK_GREEN);
        addItem(Colors.DARK_GREEN);
        addItem(Colors.CATPPUCCIN_LATTE_BLUE);
        addItem(Colors.PEACH_GREY_BROWN);
        addItem(Colors.PURPLE_YELLOW);
        addItem(Colors.YELLOW_PEACH);
        addItem(Colors.DARKER_MONO);
        addItem(Colors.DARKER_BLUE);
        addItem(Colors.DARKER_PINK);
        addItem(Colors.DARKER_RED);
    }

    public void setTheme(String name) {
        for(ColorScheme scheme : itemList) {
            if(scheme.NAME().equalsIgnoreCase(name)) {
                activeTheme = scheme;
                ThreadManager.fixedPool.submit(() -> {
                    while (OpiumClient.INSTANCE == null);
                    OpiumClient.INSTANCE.windowManager.applyTheme(activeTheme);
                    ThemeInfo.COLORSCHEME = activeTheme;
                });
                return;
            }
        }
    }

    public ColorScheme getActiveTheme() {
        return activeTheme;
    }
}
