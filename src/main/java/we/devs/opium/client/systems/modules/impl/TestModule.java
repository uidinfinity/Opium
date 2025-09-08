package we.devs.opium.client.systems.modules.impl;

import meteordevelopment.orbit.EventHandler;
import we.devs.opium.client.systems.events.Render3DEvent;
import we.devs.opium.client.systems.events.WorldTickEvent;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.systems.modules.settings.impl.BooleanSetting;
import we.devs.opium.client.systems.modules.settings.impl.ModeSetting;
import we.devs.opium.client.systems.modules.settings.impl.NumberSetting;
import we.devs.opium.client.systems.modules.settings.impl.TextSetting;

public class TestModule extends ClientModule {
    public TestModule() {
        super("TestModule", "A test module", -1, Category.SETTING);
        addSettings(new BooleanSetting("TestBool", "real setting (not fake)", false, true));
        addSettings(new ModeSetting("real", "test mode setting", true, "hepl", "hepl 2", "hepl"));
        addSettings(new TextSetting("Test text", "test teeeexxt", "hepl", true));
        addSettings(new NumberSetting("realll", "real numbers", 0f, 10f, 2f, true));
    }

    @Override
    public void disable() {
        super.disable();
    }

    @EventHandler
    public void render(Render3DEvent event) {
//        if(nextPath == null) return;
//        NodeRenderer.drawPath(nextPath, event.getMatrixStack());
    }

    @EventHandler
    public void t(WorldTickEvent.Post ignored) {
//        ThreadManager.cachedPool.submit(this::getPath);
    }

}
