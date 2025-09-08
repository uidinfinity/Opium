package we.devs.opium.client.render.ui.gui.widgets.settings;

import net.minecraft.util.math.Vec2f;
import org.lwjgl.glfw.GLFW;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.renderer.RenderContext;
import we.devs.opium.client.systems.modules.settings.impl.ModeSetting;
import we.devs.opium.client.utils.render.RenderUtil;
import we.devs.opium.client.utils.render.font.FontRenderer;

public class ModeSettingWidget extends SettingWidget{
    private final ModeSetting setting;

    public ModeSettingWidget(float wx, float wy, float ww, float wh, ModeSetting setting) {
        super(wx, wy, ww, wh);
        this.setting = setting;
    }

    @Override
    public void render(RenderContext context) {
//        Pulse2D.drawRound(context.matrixStack(), x + w - h - 4 - 3, y + 2, h - 4, h - 4, Pulse2D.cornerRad, context.colorScheme().ACCENT());
        Opium2D.Icons.renderIcon(Opium2D.Icons.ARROW_LEFT, context, x + w - h - 4 - 2, y, h, h, context.colorScheme().TEXT());

        FontRenderer.ColoredString string = FontRenderer.ColoredString.of(setting.getName() + ": ", context.colorScheme().TEXT());
        string.add(setting.getCurrent(), context.colorScheme().MUTED_TEXT());

        Vec2f textPos = RenderUtil.textRenderer.calcPosInBox(setting.getName(), x, y, w, h);

        RenderUtil.textRenderer.drawColoredString(context.matrixStack(), string, x + 3, textPos.y + RenderUtil.fontOffsetY);

        if(hovered) {
            RenderUtil.textRenderer.drawString(context.matrixStack(), setting.getDescription(), 2, context.screenHeight() - 2 - RenderUtil.textRenderer.getStringHeight(setting.getDescription(), false), context.colorScheme().TEXT().getRGB());
        }
    }

    @Override
    public void mouseInput(int key, int action, double mouseX, double mouseY) {
        if(key == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS && RenderUtil.isInside(mouseX, mouseY, x + w - h - 4 - 3, y + 2, x + w - h - 4 - 3 + h - 4, y + 2 + h - 4)) {
            setting.cycle();
        }
    }
}
