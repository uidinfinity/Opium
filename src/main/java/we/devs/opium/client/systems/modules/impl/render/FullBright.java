package we.devs.opium.client.systems.modules.impl.render;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import we.devs.opium.client.systems.events.WorldTickEvent;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.utils.Util;

import static we.devs.opium.client.OpiumClient.mc;

public class FullBright extends ClientModule {
    public FullBright() {
        builder(this)
                .name("FullBright")
                .description("No more darkness")
                .category(Category.RENDER);
    }

    @EventHandler
    private void tickEvent(WorldTickEvent.Post e) {
        mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 999, 1, false, false, false));
    }

    @Override
    public void disable() {
        if(Util.nullCheck(mc)) return;
        mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
    }
}
