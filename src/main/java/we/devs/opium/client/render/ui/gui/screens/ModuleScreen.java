package we.devs.opium.client.render.ui.gui.screens;

import net.minecraft.client.gui.DrawContext;
import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.render.ui.gui.PulseScreen;
import we.devs.opium.client.render.ui.gui.widgets.CategoryBackgroundWidget;
import we.devs.opium.client.render.ui.gui.widgets.SeperatorWidget;
import we.devs.opium.client.render.ui.gui.widgets.TextWidget;
import we.devs.opium.client.render.ui.gui.widgets.settings.*;
import we.devs.opium.client.systems.modules.settings.impl.*;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.systems.modules.settings.Setting;

import static we.devs.opium.client.OpiumClient.mc;

public class ModuleScreen extends PulseScreen {
    ClientModule curModule = new ClientModule();
    boolean closed = false;

    public ModuleScreen() {
        super("pulse-settings");
    }

    @Override
    protected void init() {
        super.init();
        if(closed || curModule == null) return;
        float width = 150;
        float height = 0;
        float spacing = 3.5f;
        float sH = 14;

        OpiumClient.LOGGER.debug("ModuleScreen > module: {} ({})", curModule.getName(), curModule.getClass().getSimpleName());

        height += sH;
        for (Setting s : curModule.getSettings()) {
            if(s instanceof SeperatorSetting) height += 10;
            else height += sH;
        }
        height += spacing;

        float x = Math.max(((float) mc.getWindow().getScaledWidth() - width) / 2, 0);
        float y = Math.max((mc.getWindow().getScaledHeight() - height) / 2, 0);
        CategoryBackgroundWidget cbw = new CategoryBackgroundWidget(x, y, width, height);
        widgetList.add(cbw);
        widgetList.add(new TextWidget(curModule.getName(), x, y, width, sH));
        for (Setting setting : curModule.getSettings()) {
            y += sH;
            if(setting instanceof BooleanSetting bs) {
                widgetList.add(new BooleanSettingWidget(x, y, width, sH, bs));
            } else if(setting instanceof NumberSetting ns) {
                widgetList.add(new NumberSettingWidget(x, y, width, sH, ns));
            } else if(setting instanceof ModeSetting ms) {
                widgetList.add(new ModeSettingWidget(x, y, width, sH, ms));
            } else if(setting instanceof TextSetting ts) {
                widgetList.add(new TextSettingWidget(x, y, width, sH, ts));
            } else if(setting instanceof ColorSetting cs) {
                widgetList.add(new ColorSettingWidget(x, y, width, sH, cs));
            } else if(setting instanceof SeperatorSetting ss) {
                widgetList.add(new SeperatorWidget(x, y, width, 10, ss));
                y -= (sH - 10);
            }
        }
    }

    public ModuleScreen initModule(ClientModule module) {
        this.curModule = module;
        closed = false;
        widgetList.clear();
        init();
        return this;
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public void close() {
        closed = true;
        this.client.setScreen(OpiumClient.INSTANCE.windowManager.getItemByClass(MainScreen.class));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
//        render.render(context, delta);
        super.render(context, mouseX, mouseY, delta);
    }

    public static class DynamicFloat {
        float v;
        public DynamicFloat(float value) {
            v = value;
        }

        public float getV() {
            return v;
        }

        public void setV(float v) {
            this.v = v;
        }
    }
}
