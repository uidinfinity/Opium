package we.devs.opium.client.render.ui.gui.widgets;

import org.lwjgl.glfw.GLFW;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.renderer.RenderContext;
import we.devs.opium.client.render.ui.gui.Widget;
import we.devs.opium.client.utils.render.AnimationUtil;
import we.devs.opium.client.utils.render.RenderUtil;

import java.awt.*;

public class ButtonWidget extends Widget {
    private final ButtonAction action;
    private final String text;

    public ButtonWidget(float wx, float wy, float ww, float wh, ButtonAction action, String text) {
        super(wx, wy, ww, wh);
        this.action = action;
        this.text = text;
    }

    AnimationUtil clickAnimation = new AnimationUtil(0, 300);
    @Override
    public void render(RenderContext context) {
        super.render(context);
        Color border = context.colorScheme().getBorderColor();
        Color inner = context.colorScheme().PRIMARY();
        Color click = context.colorScheme().SECONDARY();

        inner = clickAnimation.getColor(click, inner);

        Opium2D.drawRound(context.matrixStack(), x, y, w, h, Opium2D.cornerRad, border);
        Opium2D.drawRound(context.matrixStack(), x + Opium2D.borderWidth, y + Opium2D.borderWidth, w - Opium2D.borderWidth * 2, h - Opium2D.borderWidth * 2, Opium2D.cornerRad, inner);
        RenderUtil.textRenderer.drawString(context.matrixStack(), text, x + Opium2D.borderWidth + 2, y + Opium2D.borderWidth + 2 + RenderUtil.fontOffsetY, context.colorScheme().TEXT().getRGB());
    }

    @Override
    public void mouseInput(int key, int action, double mouseX, double mouseY) {
        super.mouseInput(key, action, mouseX, mouseY);
        if(hovered && action == GLFW.GLFW_PRESS && key == GLFW.GLFW_MOUSE_BUTTON_1) {
            this.action.run(this);
            clickAnimation.reset();
        }
    }

    @FunctionalInterface
    public interface ButtonAction {
        void run(ButtonWidget widget);
    }
}
