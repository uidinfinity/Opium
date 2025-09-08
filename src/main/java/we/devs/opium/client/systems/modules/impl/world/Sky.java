package we.devs.opium.client.systems.modules.impl.world;

import we.devs.opium.client.managers.Managers;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.systems.modules.settings.builders.ColorSettingBuilder;
import we.devs.opium.client.systems.modules.settings.impl.ColorSetting;
import we.devs.opium.client.systems.modules.settings.impl.ModeSetting;

import java.awt.*;

public class Sky extends ClientModule {

    ModeSetting color = modeSetting()
            .name("Color mode")
            .description("Color mode")
            .defaultMode("Custom")
            .mode("Rainbow")
            .mode("Custom")
            .build();

    ColorSetting colorSetting = new ColorSettingBuilder()
            .setName("Color")
            .setDescription("Custom color")
            .build();

    public Sky() {
        builder()
                .name("Sky")
                .description("Better sky")
                .settings("Color", color, colorSetting)
                .category(Category.WORLD);
    }

    public static Color getSkyColor(Color previous) {
        if(Managers.MODULE.getItemByClass(Sky.class).isEnabled()) {
            Sky sky = (Sky)Managers.MODULE.getItemByClass(Sky.class);
            if(sky.color.is("Custom")) return sky.colorSetting.getJavaColor();
            else if(sky.color.is("Rainbow")) return Opium2D.skyRainbow(25, 1);
        }

        return previous;
    }

}
