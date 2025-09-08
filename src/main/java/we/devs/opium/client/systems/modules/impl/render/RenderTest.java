package we.devs.opium.client.systems.modules.impl.render;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.DrawContext;
import we.devs.opium.client.render.renderer.RenderContext;
import we.devs.opium.client.systems.events.Render3DEvent;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.HudModule;

//import static xyz.qweru.pulse.client.utils.render.ShaderUtil.TEST_SHADER;

public class RenderTest extends HudModule {
    public RenderTest() {
        super(null, null, -1, null, 0, 0, 0, 0);
        builder(this)
                .name("Render Test")
                .description("shader / render test module")
                .category(Category.RENDER);
    }

    @Override
    public void render(DrawContext drawContext, float delta, RenderContext context) {

    }

    @EventHandler
    void render3d(Render3DEvent e)  {

    }

}
