package we.devs.opium.client.systems.modules.impl.misc;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import we.devs.opium.client.systems.events.HandlePacketEvent;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.utils.player.ChatUtil;

public class TPDetector extends ClientModule {
    public TPDetector() {
        builder()
                .name("TP Detect")
                .description("Notifies you whenever a teleport occurs")
                .category(Category.MISC);
    }

    @EventHandler
    void p2c(HandlePacketEvent e) {
        if(e.getPacket() instanceof PlayerListS2CPacket p) {
            int i = 0;
            for (PlayerListS2CPacket.Action action : p.getActions()) {
                if(action == PlayerListS2CPacket.Action.ADD_PLAYER) {
                    PlayerListS2CPacket.Entry entry = p.getEntries().get(i);
                    ChatUtil.info("Possible teleport detected: %s (gamemode: %s, listed: %s)".formatted(entry.profile().getName(), entry.gameMode(), entry.listed()));
                }
                i++;
            }
        }
    }
}
