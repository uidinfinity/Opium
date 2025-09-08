package we.devs.opium.client.systems.modules.impl.world;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.AcknowledgeChunksC2SPacket;
import we.devs.opium.client.systems.events.SendPacketEvent;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.utils.annotations.ExcludeModule;

@ExcludeModule
public class NoChunkLoad extends ClientModule {
    public NoChunkLoad() {
        builder(this)
                .name("NoChunkLoad")
                .description("Prevents the client from acknowledging received chunks")
                .category(Category.WORLD);
    }

    @EventHandler
    void sendPacket(SendPacketEvent e) {
        if(e.getPacket() instanceof AcknowledgeChunksC2SPacket p) e.setCancelled(true);
    }
}
