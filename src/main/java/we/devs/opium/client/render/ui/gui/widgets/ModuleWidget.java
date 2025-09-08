package we.devs.opium.client.render.ui.gui.widgets;

import org.lwjgl.glfw.GLFW;
import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.renderer.RenderContext;
import we.devs.opium.client.render.ui.gui.Widget;
import we.devs.opium.client.render.ui.gui.screens.ModuleScreen;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.systems.modules.impl.setting.ClickGUI;
import we.devs.opium.client.utils.InputUtil;
import we.devs.opium.client.utils.annotations.Status;
import we.devs.opium.client.utils.render.AnimationUtil;
import we.devs.opium.client.utils.render.RenderUtil;
import we.devs.opium.client.utils.render.font.FontRenderer;

import java.awt.*;

import static we.devs.opium.client.OpiumClient.mc;

public class ModuleWidget extends Widget {
    public ClientModule module;
    public boolean expanded = false;

    public ModuleWidget(float x, float y, float w, float h, ClientModule module) {
        super(x, y, w, h);
        this.module = module;
    }

    Opium2D.GradientRect btnColor = Opium2D.GradientRect.of(Color.WHITE);

    AnimationUtil hoverAnim = new AnimationUtil(0, 450, true);
    boolean animateHover = false;
    boolean prevHover = false;
    boolean prevEnabled = false;

    boolean hoverInit = false;

    @Override
    @Status.MarkedForCleanup
    public void render(RenderContext context) {
        if(prevListening) prevListening = false;
        double mouseX = context.mouseX();
        double mouseY = context.mouseY();

        if(!hoverInit) {
            hoverInit = hoverAnim.hasEnded();
        }

        hovered = RenderUtil.isInside(mouseX, mouseY, x, y, x + w, y + h);
        btnColor = getFill(context);
        Opium2D.drawRound(context.matrixStack(), x + Opium2D.borderWidth + 1, y + Opium2D.borderWidth, w - Opium2D.borderWidth*2 - 2,
                h - Opium2D.borderWidth*2, Opium2D.cornerRad, btnColor);
//        if(hovered && ClickGUI.hoverMode.is("hollow")) Pulse2D.drawRound(context.matrixStack(), x + Pulse2D.borderWidth + 1 + 1, y + Pulse2D.borderWidth + 1, w - Pulse2D.borderWidth*2 - 2 - 2,
//                h - Pulse2D.borderWidth*2 - 2, Pulse2D.cornerRad, getFill(context));

        if(module.isEnabled() || prevEnabled) {
            Opium2D.drawRound(context.matrixStack(), x + Opium2D.borderWidth + 1 + 1, y + Opium2D.borderWidth + 1, w - Opium2D.borderWidth*2 - 2 - 2,
                    h - Opium2D.borderWidth*2 - 2, Opium2D.cornerRad, getFill(context));
        } else if(hovered && hoverInit) {
            Opium2D.GradientRect rect = new Opium2D.GradientRect(btnColor.c1(), btnColor.c2(), context.colorScheme().PRIMARY(), Opium2D.darker(hoverAnim.getColor(context.colorScheme().PRIMARY(), btnColor.c4()), 0.85f));
            Opium2D.drawRound(context.matrixStack(), x + Opium2D.borderWidth + 1, y + Opium2D.borderWidth, w - Opium2D.borderWidth*2 - 2,
                    h - Opium2D.borderWidth*2, Opium2D.cornerRad, rect);
        } else if(animateHover && hoverInit) {
            Opium2D.GradientRect rect =
                    new Opium2D.GradientRect(hoverAnim.getColor(btnColor.c1(),
                            context.colorScheme().PRIMARY()), hoverAnim.getColor(btnColor.c2(),
                            context.colorScheme().PRIMARY()), context.colorScheme().PRIMARY(),
                            hoverAnim.getColor(Opium2D.darker(hoverAnim.getColor(context.colorScheme().PRIMARY(),
                                    btnColor.c1()), 0.85f), context.colorScheme().PRIMARY()));

            Opium2D.drawRound(context.matrixStack(), x + Opium2D.borderWidth + 1, y + Opium2D.borderWidth, w - Opium2D.borderWidth*2 - 2,
                    h - Opium2D.borderWidth*2, Opium2D.cornerRad, rect);
        }

        FontRenderer.ColoredString string;
        if(listening) {
            string = FontRenderer.ColoredString.of("Listening...", context.colorScheme().TEXT());
        } else {
            string = FontRenderer.ColoredString.of(module.getName(), context.colorScheme().TEXT());
            if(module.getBind() != InputUtil.KEY_UNKNOWN) string.add(" [" + InputUtil.getKey(module.getBind()) + "]", context.colorScheme().MUTED_TEXT());
        }

        RenderUtil.textRenderer.drawColoredString(context.matrixStack(), string, x + 2 + 2, y + RenderUtil.fontOffsetY);
        if(hovered) {
            RenderUtil.textRenderer.drawString(context.matrixStack(), module.getDescription(), 2, context.screenHeight() - 2 - RenderUtil.textRenderer.getStringHeight(module.getDescription(), false), context.colorScheme().TEXT().getRGB());
        }

        if(((!prevHover && hovered) || (prevHover && !hovered)) && hoverInit) {
            hoverAnim.reset();
        }

        animateHover = hovered || !hoverAnim.hasEnded();
        prevEnabled = module.isEnabled() || !animation.hasEnded();
        prevHover = hovered;
    }

    public boolean listening = false;
    boolean prevListening = false;
    @Override
    public void mouseInput(int key, int action, double mouseX, double mouseY) {
        if(action == GLFW.GLFW_PRESS && RenderUtil.isInside(mouseX, mouseY, x, y, x + w, y + h)) {
            if(key == GLFW.GLFW_MOUSE_BUTTON_1) {
                module.toggle();
                animation.reset();
            }
            else if(key == GLFW.GLFW_MOUSE_BUTTON_2) {
                mc.setScreen((
                        (ModuleScreen) OpiumClient.INSTANCE.windowManager.getItemByClass(ModuleScreen.class)).initModule(module));
//                PulseClient.LOGGER.info("RClick");
//                expanded =! expanded;
            } else if(key == GLFW.GLFW_MOUSE_BUTTON_3) {
                listening = true;
            }
         }
    }

    AnimationUtil animation = new AnimationUtil(0, 450);
    Opium2D.GradientRect getFill(RenderContext context) {
        Color color = ClickGUI.enabledColor.is("Secondary") ? context.colorScheme().SECONDARY() : context.colorScheme().ACCENT();
        if(!hoverInit && !module.isEnabled()) return Opium2D.GradientRect.of(context.colorScheme().PRIMARY());
        else if(animateHover) {
            Opium2D.GradientRect prevRect = new Opium2D.GradientRect(color, color, context.colorScheme().PRIMARY(), Opium2D.darker(hoverAnim.getColor(context.colorScheme().PRIMARY(), color), 0.85f));

            if(!module.isEnabled()) {
                return new Opium2D.GradientRect(
                        animation.getColor(color, prevRect.c1()),
                        animation.getColor(color, prevRect.c2()),
                        animation.getColor(color, prevRect.c3()),
                        animation.getColor(color, prevRect.c4())
                );
            }

            return new Opium2D.GradientRect(
                    animation.getColor(prevRect.c1(), color),
                    animation.getColor(prevRect.c2(), color),
                    animation.getColor(prevRect.c3(), color),
                    animation.getColor(prevRect.c4(), color)
            );
        } else {
            if(module.isEnabled()) return Opium2D.GradientRect.of(animation.getColor(context.colorScheme().PRIMARY(), color));
            else return Opium2D.GradientRect.of(animation.getColor(color, context.colorScheme().PRIMARY()));
        }
    }

    @Override
    public void input(int keycode, int scancode, int action) {
        if(listening) {
            if(keycode == InputUtil.KEY_ESCAPE) module.setBind(-1);
            else module.setBind(keycode);
            listening = false;
            prevListening = true;
        }
        super.input(keycode, scancode, action);
    }

    @Override
    public boolean close() {
        return !prevListening;
    }
}
