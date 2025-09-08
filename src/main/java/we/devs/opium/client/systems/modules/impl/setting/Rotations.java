package we.devs.opium.client.systems.modules.impl.setting;

import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.systems.modules.settings.builders.ModeSettingBuilder;
import we.devs.opium.client.systems.modules.settings.impl.ModeSetting;
import we.devs.opium.client.systems.modules.settings.impl.NumberSetting;
import we.devs.opium.client.utils.Util;
import we.devs.opium.client.utils.annotations.ExcludeModule;
import we.devs.opium.client.utils.player.RotationUtil;

@ExcludeModule
public class Rotations extends ClientModule {

    public static ModeSetting MODE = new ModeSettingBuilder()
            .name("Mode")
            .description("leaked by 4asik with love <3")
            .defaultMode("Instant")
            .mode("Instant Lerp")
            .mode("Lerp")
            .mode("Instant")
            .build();

    public static NumberSetting LERP_STEP = numberSetting()
            .info("Lerp step", "leaked by 4asik with love <3")
            .range(0, 1)
            .defaultValue(0.05f)
            .setValueModifier(value -> (float) Util.round(value, 3))
            .build();

    public static NumberSetting ROTATION_DELAY = numberSetting()
            .info("Delay", "leaked by 4asik with love <3")
            .range(0, 200)
            .defaultValue(15)
            .stepFullNumbers()
            .build();

    public Rotations() {
        builder(this)
                .name("Rotations")
                .description("leaked by 4asik with love <3")
                .settings(MODE, LERP_STEP, ROTATION_DELAY)
                .category(Category.SETTING);

        MODE.addOnToggle(() -> {
            switch (MODE.getCurrent()) {
                case "Instant" -> RotationUtil.mode = RotationUtil.Mode.INSTANT;
                case "Lerp" -> RotationUtil.mode = RotationUtil.Mode.LERP;
                case "Instant Lerp" -> RotationUtil.mode = RotationUtil.Mode.INSTANT_LERP;
            }
        });

        LERP_STEP.addOnToggle(() -> RotationUtil.LERP_STEP = LERP_STEP.getValue());
    }

}
