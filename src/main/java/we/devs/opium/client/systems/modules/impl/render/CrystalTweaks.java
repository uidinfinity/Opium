package we.devs.opium.client.systems.modules.impl.render;

import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.systems.modules.settings.impl.NumberSetting;
import we.devs.opium.client.utils.InputUtil;

public class CrystalTweaks extends ClientModule {

    public CrystalTweaks() {
        super("Crystal Tweaks", "Change crystal rendering", InputUtil.KEY_UNKNOWN, Category.RENDER);
        builder(this).settings(scale, rotateSpeed);
    }

    public NumberSetting scale = numberSetting()
            .name("Scale")
            .description("Crystal scale multiplier")
            .range(0, 5)
            .defaultValue(1)
            .build();

    public NumberSetting rotateSpeed = numberSetting()
            .name("Rotate speed")
            .description("Rotate speed multiplier")
            .range(0, 5)
            .defaultValue(1)
            .build();
}
