package we.devs.opium.client.render.renderer;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import we.devs.opium.client.render.ui.color.ColorScheme;
import we.devs.opium.client.render.ui.gui.PulseScreen;

public record RenderContext(MatrixStack matrixStack, DrawContext context, int mouseX, int mouseY, int screenWidth, int screenHeight, float delta, ColorScheme colorScheme, PulseScreen parent) {
    public MatrixStack getMatrices() {
        return matrixStack;
    }
}
