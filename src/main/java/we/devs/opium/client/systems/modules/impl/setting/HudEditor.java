package we.devs.opium.client.systems.modules.impl.setting;

import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.render.ui.gui.screens.HudConfigScreen;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.systems.modules.settings.builders.ModeSettingBuilder;
import we.devs.opium.client.systems.modules.settings.builders.NumberSettingBuilder;
import we.devs.opium.client.systems.modules.settings.impl.BooleanSetting;
import we.devs.opium.client.systems.modules.settings.impl.ModeSetting;
import we.devs.opium.client.systems.modules.settings.impl.NumberSetting;
import we.devs.opium.client.utils.Util;

import static we.devs.opium.client.OpiumClient.mc;

public class HudEditor extends ClientModule {
    public HudEditor() {
        builder(this)
                .name("Hud")
                .description("leaked by 4asik with love <3")
                .settings(glow, outline, color, snap, hudMode, textColor)
                .category(Category.SETTING);
    }

    public static NumberSetting glow = new NumberSettingBuilder()
            .name("Glow")
            .description("leaked by 4asik with love <3")
            .range(0, 1)
            .defaultValue(0.7f)
            .setValueModifier(value -> (float) Util.round(value, 2))
            .build();

    public static NumberSetting outline = new NumberSettingBuilder()
            .name("Outline")
            .description("leaked by 4asik with love <3")
            .range(0, 1)
            .defaultValue(0.7f)
            .setValueModifier(value -> (float) Util.round(value, 2))
            .build();

    public static ModeSetting color = new ModeSettingBuilder()
            .name("Color")
            .description("leaked by 4asik with love <3")
            .defaultMode("Primary")
            .mode("Accent")
            .mode("Rainbow")
            .mode("None")
            .mode("Primary")
            .build();

    public static ModeSetting textColor = new ModeSettingBuilder()
            .name("Label color")
            .description("leaked by 4asik with love <3")
            .defaultMode("Text")
            .mode("Accent")
            .mode("Secondary")
            .mode("Rainbow")
            .mode("Text")
            .build();

    public static ModeSetting hudMode = new ModeSettingBuilder()
            .name("Mode")
            .description("leaked by 4asik with love <3")
            .defaultMode("Normal")
            .mode("Minimal")
            .mode("None")
            .mode("Normal")
            .build();


    public static BooleanSetting snap = new BooleanSetting("Snap", "leaked by 4asik with love <3", true, true);

    @Override
    public void enable() {
        mc.setScreen(OpiumClient.INSTANCE.windowManager.getItemByClass(HudConfigScreen.class));
        this.toggle();
    }
}
