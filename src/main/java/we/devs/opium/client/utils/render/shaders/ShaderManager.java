package we.devs.opium.client.utils.render.shaders;

import me.x150.renderer.render.OutlineFramebuffer;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.ui.color.ThemeInfo;

public class ShaderManager {
    public void renderShader(Runnable task, Shader shader) {
        switch (shader) {
            case OUTLINE -> {
                OutlineFramebuffer.use(task);
                OutlineFramebuffer.draw(4, ThemeInfo.COLORSCHEME.getBorderColor(), Opium2D.injectAlpha(ThemeInfo.COLORSCHEME.PRIMARY(), 50));
            }
        }
    }

}
