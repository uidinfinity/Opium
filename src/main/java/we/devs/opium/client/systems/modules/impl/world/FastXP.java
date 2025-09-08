package we.devs.opium.client.systems.modules.impl.world;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import we.devs.opium.client.systems.events.WorldTickEvent;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.systems.modules.settings.impl.NumberSetting;
import we.devs.opium.client.utils.Util;
import we.devs.opium.client.utils.timer.TimerUtil;

import static we.devs.opium.client.OpiumClient.mc;

public class FastXP extends ClientModule {

    public FastXP() {
        builder(this)
                .name("FastXP")
                .description("Throw xp bottles more quickly")
                .settings(delay)
                .category(Category.WORLD);
    }

    NumberSetting delay = numberSetting()
            .name("Delay")
            .description("Throw delay")
            .defaultValue(25)
            .range(0, 500)
            .stepFullNumbers()
            .build();

    TimerUtil timer = new TimerUtil();
    @EventHandler
    void preWorldTick(WorldTickEvent.Pre e) {
        if(Util.nullCheck()) return;
        if(mc.options.useKey.isPressed() &&
                 mc.player.getInventory().getStack(mc.player.getInventory().selectedSlot).getItem().equals(Items.EXPERIENCE_BOTTLE)){
            if(timer.hasReached(delay.getValue())) {
                mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
                timer.reset();
            }
        }
    }

}
