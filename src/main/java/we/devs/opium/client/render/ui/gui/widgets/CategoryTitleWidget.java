package we.devs.opium.client.render.ui.gui.widgets;

import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.renderer.RenderContext;
import we.devs.opium.client.render.ui.gui.Widget;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.impl.setting.ClickGUI;
import we.devs.opium.client.utils.render.RenderUtil;

public class CategoryTitleWidget extends Widget {
    private final Category category;

    /**
     * Category element
     * @param x x coord
     * @param y y coord
     * @param w width, optimal value = 82
     * @param h height, optimal value = 17
     * @param category target category
     */
    public CategoryTitleWidget(float x, float y, float w, float h, Category category) {
        super(x, y, w, h);
        this.category = category;
    }

    float iconW = h - 4;
    float iconH = iconW;

    @Override
    public void render(RenderContext context) {
        Opium2D.Elements.container(context, x, y, w, h, false, false);
        if(ClickGUI.icons.isEnabled()) drawIcon(context);
        RenderUtil.textRenderer.drawString(context.matrixStack(), category.label, x + 2 + (ClickGUI.icons.isEnabled() ? iconW + 2 : 0), y + RenderUtil.fontOffsetY, context.colorScheme().TEXT().getRGB());
    }

    void drawIcon(RenderContext context) {
        switch (category) {
            case SETTING -> Opium2D.Icons.queue(() -> Opium2D.Icons.config(context, x + 2, y + 2, iconW, iconH));
            case HUD -> Opium2D.Icons.queue(() -> Opium2D.Icons.hud(context, x + 2, y + 2, iconW, iconH));
            case MISC -> Opium2D.Icons.queue(() -> Opium2D.Icons.misc(context, x + 2, y + 2, iconW, iconH));
            case WORLD -> Opium2D.Icons.queue(() -> Opium2D.Icons.world(context, x + 2, y + 2, iconW, iconH));
            case COMBAT -> Opium2D.Icons.queue(() -> Opium2D.Icons.combat(context, x + 2, y + 2, iconW, iconH));
            case RENDER -> Opium2D.Icons.queue(() -> Opium2D.Icons.render(context, x + 2, y + 2, iconW, iconH));
            case MOVEMENT -> Opium2D.Icons.queue(() -> Opium2D.Icons.move(context, x + 2, y + 2, iconW, iconH));
        }
    }
}
