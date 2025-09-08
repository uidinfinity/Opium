package we.devs.opium.client.systems.modules.impl.misc;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.systems.events.HandlePacketEvent;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.utils.annotations.ExcludeModule;

import static we.devs.opium.client.OpiumClient.mc;

// partially skidded from blackout
@ExcludeModule
public class AutoEZ extends ClientModule {
    public AutoEZ() {
        builder()
                .name("Auto EZ")
                .description("Automatically send a message in chat after pop / death")
                .category(Category.MISC);
    }

    String name = "??";
    @EventHandler
    private void onReceive(HandlePacketEvent event) {
        if (event.getPacket() instanceof EntityStatusS2CPacket packet) {
            // Pop
            if (packet.getStatus() == 35) {
                Entity entity = packet.getEntity(mc.world);
                if (mc.player != null && mc.world != null && entity instanceof PlayerEntity) {
                    if (entity != mc.player && !OpiumClient.friendSystem.isPlayerInSystem((PlayerEntity) entity) /*&&
                            mc.player.getPos().distanceTo(entity.getPos()) <= range.get()*/) {
//                        sendPopMessage(entity.getName().getString());
                    }
                }
            }
        }
    }

//    @SuppressWarnings("DataFlowIssue")
    private boolean anyDead(double range) {
        for (PlayerEntity pl : mc.world.getPlayers()) {
            if (pl != mc.player && OpiumClient.friendSystem.isPlayerInSystem(pl) && pl.getPos().distanceTo(mc.player.getPos()) <= range
                    && pl.getHealth() <= 0) {
                name = pl.getName().getString();
                return true;
            }
        }
        return false;
    }
}
