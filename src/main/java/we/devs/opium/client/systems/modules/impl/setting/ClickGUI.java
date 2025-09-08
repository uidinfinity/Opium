package we.devs.opium.client.systems.modules.impl.setting;

import org.lwjgl.glfw.GLFW;
import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.ui.color.Colors;
import we.devs.opium.client.render.ui.gui.PulseScreen;
import we.devs.opium.client.render.ui.gui.screens.MainScreen;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.systems.modules.settings.builders.BooleanSettingBuilder;
import we.devs.opium.client.systems.modules.settings.builders.ModeSettingBuilder;
import we.devs.opium.client.systems.modules.settings.builders.NumberSettingBuilder;
import we.devs.opium.client.systems.modules.settings.impl.BooleanSetting;
import we.devs.opium.client.systems.modules.settings.impl.ModeSetting;
import we.devs.opium.client.systems.modules.settings.impl.NumberSetting;
import we.devs.opium.client.render.ui.color.ColorScheme;
import we.devs.opium.client.utils.Util;
import we.devs.opium.client.utils.render.RenderUtil;
import we.devs.opium.client.utils.thread.ThreadManager;

import static we.devs.opium.client.OpiumClient.mc;

public class ClickGUI extends ClientModule {

    public ModeSetting theme = new ModeSettingBuilder()
            .name("Theme")
            .description("leaked by 4asik with love <3")
            .shouldShow(true)
            .defaultMode(Colors.DEFAULT.NAME())
            .build();

    public static BooleanSetting blur = new BooleanSettingBuilder()
            .name("Blur")
            .description("leaked by 4asik with love <3")
            .defaultValue(true)
            .build();

    public static NumberSetting blurStrength = new NumberSettingBuilder()
            .name("Blur strength")
            .description("leaked by 4asik with love <3")
            .min(0f)
            .max(10f)
            .defaultValue(5f)
            .stepFullNumbers()
            .shouldShow(true)
            .build();

    public static NumberSetting blurDirection = new NumberSettingBuilder()
            .name("Blur direction")
            .description("leaked by 4asik with love <3")
            .min(0f)
            .max(1f)
            .defaultValue(0.5f)
            .shouldShow(true)
            .build();

    public static BooleanSetting roundCorners = new BooleanSettingBuilder()
            .name("Round corners")
            .description("leaked by 4asik with love <3")
            .defaultValue(true)
            .build();

    public static NumberSetting cornerRadius = new NumberSettingBuilder()
            .name("Corner radius")
            .description("leaked by 4asik with love <3")
            .range(0, 10)
            .defaultValue(2)
            .setValueModifier(value -> (float) Util.round(value, 2))
            .build();

    public static NumberSetting borderWidth = new NumberSettingBuilder()
            .name("Border width")
            .description("leaked by 4asik with love <3")
            .range(0, 5)
            .defaultValue(Opium2D.borderWidth)
            .setValueModifier(value -> (float) Util.round(value, 2))
            .build();

    public static ModeSetting font = new ModeSettingBuilder()
            .name("Font")
            .description("leaked by 4asik with love <3")
            .defaultMode("Notosans-light")
            .mode("Noto-regular")
            .mode("Flux-bold")
            .mode("Flux-extralight")
            .mode("Flux-medium")
            .mode("Flux-thin")
            .mode("Flux-light")
            .mode("Verdana")
            .mode("Comforta-bold")
            .mode("Comforta-light")
            .mode("Comforta-medium")
            .mode("Comforta-regular")
            .mode("Comforta-semibold")
            .mode("Notosans-light")
            .build();

    public static ModeSetting MSAASamples = new ModeSettingBuilder()
            .name("MSAA samples")
            .description("leaked by 4asik with love <3")
            .defaultMode("32")
            .mode("Disabled")
            .mode("2")
            .mode("4")
            .mode("8")
            .mode("16")
            .mode("32")
            .build();

    public static BooleanSetting ICON_MSAA = new BooleanSetting("Smooth icons", "leaked by 4asik with love <3", true, true);

    public static ModeSetting borderMode = new ModeSettingBuilder()
            .name("Border color")
            .description("leaked by 4asik with love <3")
            .defaultMode("Accent")
            .mode("Secondary")
            .mode("None")
            .mode("Accent")
            .build();

    public static BooleanSetting icons = new BooleanSettingBuilder()
            .name("Icons")
            .description("leaked by 4asik with love <3")
            .defaultValue(true)
            .build();

    public static ModeSetting hoverMode = new ModeSettingBuilder()
            .name("Hover mode")
            .description("leaked by 4asik with love <3")
            .defaultMode("Fill")
            .mode("Hollow")
            .mode("Fill")
            .build();

    public static ModeSetting iconColor = new ModeSettingBuilder()
            .name("Icon color")
            .description("leaked by 4asik with love <3")
            .defaultMode("Accent")
            .mode("Secondary")
            .mode("Accent")
            .build();

    public static ModeSetting enabledColor = new ModeSettingBuilder()
            .name("Active color")
            .description("leaked by 4asik with love <3")
            .defaultMode("Accent")
            .mode("Secondary")
            .mode("Accent")
            .build();

    public static ModeSetting fontMode = new ModeSettingBuilder()
            .name("Font mode")
            .description("leaked by 4asik with love <3")
            .defaultMode("Regular")
            .mode("All lowercase")
            .mode("Regular")
            .build();

    public static NumberSetting backgroundOpacity = new NumberSettingBuilder()
            .name("Background opacity")
            .description("leaked by 4asik with love <3")
            .range(0, 255)
            .defaultValue(190)
            .stepFullNumbers()
            .build();

    public static BooleanSetting colRandomizer = new BooleanSettingBuilder()
            .name("Color noise")
            .description("leaked by 4asik with love <3")
            .defaultValue(true)
            .build();

    public static BooleanSetting gradient = new BooleanSettingBuilder()
            .name("Gradients")
            .description("leaked by 4asik with love <3")
            .defaultValue(true)
            .build();

    public static BooleanSetting customFontOffset = new BooleanSetting("Use font offset", "leaked by 4asik with love <3", false, true);

    public static NumberSetting offset = new NumberSettingBuilder()
            .name("Font offset")
            .description("leaked by 4asik with love <3")
            .range(-25, 25)
            .defaultValue(RenderUtil.fontOffsetY)
            .stepFullNumbers()
            .build();


    public ClickGUI() {
        super("ClickGUI", "leaked by 4asik with love <3", GLFW.GLFW_KEY_RIGHT_CONTROL, Category.SETTING);

        builder(this)
                .settings(theme, enabledColor)
                .settings("Blur", blur, blurStrength, blurDirection)
                .settings("Corners", roundCorners, cornerRadius)
                .settings("Font", font, fontMode, customFontOffset, offset)
                .settings("MSAA", MSAASamples, ICON_MSAA)
                .settings("Border", borderMode, borderWidth)
                .settings("Icons", icons, iconColor)
                .settings("Background", backgroundOpacity, colRandomizer, gradient);

        ThreadManager.fixedPool.submit(() -> {
            while (OpiumClient.INSTANCE == null);
            for (ColorScheme scheme : OpiumClient.INSTANCE.themeManager.getItemList()) {
                theme.addMode(scheme.NAME());
            }
        });

        theme.addOnToggle(() -> {
            OpiumClient.INSTANCE.themeManager.setTheme(theme.getCurrent());
        });

        roundCorners.addOnToggle(() -> {
            Opium2D.cornerRad = roundCorners.isEnabled() ? cornerRadius.getValue() : 0;
        });

        cornerRadius.addOnToggle(() -> {
            Opium2D.cornerRad = roundCorners.isEnabled() ? cornerRadius.getValue() : 0;
        });

        font.addOnToggle(() -> {
            RenderUtil.curFontName = font.getCurrent();
            RenderUtil.updateFont();
        });

        borderWidth.addOnToggle(() -> {
            Opium2D.borderWidth = borderWidth.getValue();
        });

        offset.addOnToggle(() -> {
            if(customFontOffset.isEnabled()) RenderUtil.fontOffsetY = offset.getValue();
        });

        customFontOffset.addOnToggle(() -> {
            if(customFontOffset.isEnabled()) RenderUtil.fontOffsetY = offset.getValue();
            else RenderUtil.updateFont();
        });
    }

    @Override
    public void enable() {
        super.enable();
        if (mc.currentScreen instanceof PulseScreen) {
            mc.setScreen(null);
        } else {
            mc.setScreen(OpiumClient.INSTANCE.windowManager.getItemByClass(MainScreen.class));
        }
        this.toggle();
    }
}
