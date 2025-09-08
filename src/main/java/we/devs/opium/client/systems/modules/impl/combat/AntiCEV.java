package we.devs.opium.client.systems.modules.impl.combat;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import we.devs.opium.client.systems.events.WorldTickEvent;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.systems.modules.settings.impl.NumberSetting;
import we.devs.opium.client.utils.Util;
import we.devs.opium.client.utils.entity.EntityFinder;
import we.devs.opium.client.utils.player.PlayerUtil;

import static we.devs.opium.client.OpiumClient.mc;

public class AntiCEV extends ClientModule {

    NumberSetting range = numberSetting()
            .name("Range")
            .description("Range")
            .range(0, 7)
            .defaultValue(5f)
            .setValueModifier(value -> (float) Util.round(value, 2))
            .build();

    public AntiCEV() {
        builder()
                .name("Anti CEV")
                .description("Automatically attack crystals near you")
                .settings(range)
                .category(Category.COMBAT);
    }

    @EventHandler
    void tick(WorldTickEvent.Post e) {
        EntityFinder.EntityList targets = EntityFinder.findEntitiesInRange(range.getValue(), mc.player.getPos());
        for (Entity entity : targets.get()) {
            if(!entity.isAlive()) continue;
            if(entity instanceof EndCrystalEntity ec) {
                PlayerUtil.attackEntity(ec);
            }
        }
    }

}
