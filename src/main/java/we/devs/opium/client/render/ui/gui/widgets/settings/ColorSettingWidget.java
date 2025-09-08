package we.devs.opium.client.render.ui.gui.widgets.settings;

import org.lwjgl.glfw.GLFW;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.renderer.RenderContext;
import we.devs.opium.client.render.ui.gui.screens.ColorScreen;
import we.devs.opium.client.systems.modules.settings.impl.ColorSetting;
import we.devs.opium.client.utils.InputUtil;
import we.devs.opium.client.utils.render.RenderUtil;

import static we.devs.opium.client.OpiumClient.mc;

public class ColorSettingWidget extends SettingWidget {

    ColorSetting setting;

    public ColorSettingWidget(float wx, float wy, float ww, float wh, ColorSetting setting) {
        super(wx, wy, ww, wh);
        this.setting = setting;
    }

    RenderContext prev = null;
    @Override
    public void render(RenderContext context) {
        RenderUtil.textRenderer.drawString(
                context.matrixStack(), setting.getName(),
                this.x + 3, this.y + 2 + RenderUtil.fontOffsetY,
                context.colorScheme().TEXT().getRGB());

        Opium2D.drawRound(
                context.matrixStack(),
                this.x + this.w - RenderUtil.textRenderer.getStringWidth("Edit", false) - 6 - Opium2D.borderWidth * 3 ,
                y + 2,
                this.w - (this.w - RenderUtil.textRenderer.getStringWidth("Edit", false) - 4 - Opium2D.borderWidth * 3),
                this.h - 4,
                Opium2D.cornerRad,
                setting.getJavaColor()
        );
        prev = context;
    }

    @Override
    public void mouseInput(int key, int action, double mouseX, double mouseY) {
        if(prev == null) return;
        if(key == InputUtil.MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS
                && RenderUtil.isInside(mouseX, mouseY,
                    x,
                y,
                x + w,
                y + h
                )
        ) {
            mc.setScreen(new ColorScreen(prev.parent(), setting));
        }
    }
}
