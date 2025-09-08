package we.devs.opium.client.render.ui.gui.widgets;

import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.renderer.RenderContext;
import we.devs.opium.client.render.ui.gui.Widget;

import java.awt.*;

import static we.devs.opium.client.render.renderer.Opium2D.borderWidth;
import static we.devs.opium.client.render.renderer.Opium2D.cornerRad;

public class CategoryBackgroundWidget extends Widget {
    public CategoryBackgroundWidget(float wx, float wy, float ww, float wh) {
        super(wx, wy, ww, wh);
    }

    public void resizeTo(float ny) {
        this.h = ny - y;
    }
    public boolean altColor = false;
    public Color color = new Color(0, 0, 0);

    @Override
    public void render(RenderContext context) {
        Color bColor = context.colorScheme().getBorderColor();

        Opium2D.drawRound(context.matrixStack(), x, y, w, h, cornerRad, bColor);
        Opium2D.drawRound(context.matrixStack(), x + borderWidth, y + borderWidth,
                w - borderWidth * 2, h - borderWidth  * 2, cornerRad, altColor ? color : context.colorScheme().PRIMARY());

    }

    public void setH(float h) {
        this.h = h;
    }
}
