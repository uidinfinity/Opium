package we.devs.opium.client.systems.modules.impl.movement;

import meteordevelopment.orbit.EventHandler;
import org.lwjgl.glfw.GLFW;
import we.devs.opium.client.systems.events.KeyEvent;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.utils.InputUtil;
import we.devs.opium.client.utils.Util;

import static we.devs.opium.client.OpiumClient.mc;

public class AirJump extends ClientModule {
    public AirJump() {
        builder(this)
                .name("AirJump")
                .description("Jump on air")
                .bind(InputUtil.KEY_UNKNOWN)
                .category(Category.MOVEMENT);
    }

    @Override
    public void enable() {
        super.enable();
    }

    boolean jump = true;
    @EventHandler
    public void onKey(KeyEvent event) {
        if(Util.nullCheck(mc) || mc.currentScreen != null) return;
        if(event.getKey() == InputUtil.KEY_SPACE && jump) {
            mc.player.jump();
            jump = false;
        } else if(event.getAction() == GLFW.GLFW_RELEASE && event.getKey() == InputUtil.KEY_SPACE) jump = true;
    }
}
