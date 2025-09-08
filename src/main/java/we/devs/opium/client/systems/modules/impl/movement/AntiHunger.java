package we.devs.opium.client.systems.modules.impl.movement;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import we.devs.opium.client.mixin.iinterface.IPlayerMoveC2SPacket;
import we.devs.opium.client.systems.events.SendPacketEvent;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.systems.modules.settings.impl.BooleanSetting;
import we.devs.opium.client.utils.InputUtil;
import we.devs.opium.client.utils.Util;

import static we.devs.opium.client.OpiumClient.mc;

public class AntiHunger extends ClientModule {

    BooleanSetting onGround = booleanSetting()
            .name("Ground spoof")
            .description("Spoof ground")
            .defaultValue(false)
            .build();

    BooleanSetting walk = booleanSetting()
            .name("Walk spoof")
            .description("Spoof walking when sprinting")
            .defaultValue(true)
            .build();

    public AntiHunger() {
        super("Anti hunger", "Reduces hunger", InputUtil.KEY_UNKNOWN, Category.MOVEMENT);
        builder()
                .settings(walk, onGround);
    }

    @EventHandler
    void p2S(SendPacketEvent event) {
        if(Util.nullCheck()) return;
        Packet<?> p = event.getPacket();

        if (mc.player.hasVehicle() || mc.player.isTouchingWater() || mc.player.isSubmergedInWater()) return;

        if (p instanceof ClientCommandC2SPacket packet && walk.isEnabled()) {
            if (packet.getMode() == ClientCommandC2SPacket.Mode.START_SPRINTING) event.cancel();
        }

        if (event.getPacket() instanceof PlayerMoveC2SPacket packet && onGround.isEnabled() && mc.player.isOnGround() && mc.player.fallDistance <= 0.0 && !mc.interactionManager.isBreakingBlock()) {
            ((IPlayerMoveC2SPacket) packet).pulse$setOnGround(false);
        }
    }

}
