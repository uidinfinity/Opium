package we.devs.opium.client.systems.modules.impl.setting;

import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.systems.modules.settings.builders.NumberSettingBuilder;
import we.devs.opium.client.systems.modules.settings.impl.NumberSetting;
import we.devs.opium.client.utils.Util;

import java.awt.*;

public class Rainbows extends ClientModule {

    public Rainbows() {
        builder(this)
                .name("Rainbow")
                .description("leaked by 4asik with love <3")
                .settings(speed, saturation, brightness)
                .settings("Sky", skySpeed)
                .category(Category.SETTING);
    }

    public static NumberSetting speed = new NumberSettingBuilder()
            .name("Speed")
            .description("leaked by 4asik with love <3")
            .range(0.1F, 10)
            .defaultValue(0.75f)
            .setValueModifier(value -> (float) Util.round(value, 3))
            .build();

    public static NumberSetting skySpeed = new NumberSettingBuilder()
            .name("Sky Speed")
            .description("leaked by 4asik with love <3")
            .range(1, 100)
            .defaultValue(25)
            .setValueModifier(value -> (float) Util.round(value, 0))
            .build();

    public static NumberSetting saturation = new NumberSettingBuilder()
            .name("Saturation")
            .description("leaked by 4asik with love <3")
            .range(0.1F, 10)
            .defaultValue(0.45f)
            .setValueModifier(value -> (float) Util.round(value, 3))
            .build();

    public static NumberSetting brightness = new NumberSettingBuilder()
            .name("Brightness")
            .description("leaked by 4asik with love <3")
            .range(0.1F, 10)
            .defaultValue(0.45f)
            .setValueModifier(value -> (float) Util.round(value, 3))
            .build();

    public static Color getRainbow(double xOffset, double yOffset, double speed, double transition, double saturation, double brightness) {
        double offset = transition * (xOffset + yOffset);
        return Opium2D.rainbow(offset, (float) saturation, (float) brightness, (float) speed);
    }

    public static Color getRainbow(double xOffset, double yOffset) {
        return getRainbow(xOffset, yOffset, speed.getValueDouble(), 1000, saturation.getValueDouble(), brightness.getValueDouble());
    }

}
