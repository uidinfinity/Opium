package we.devs.opium.client.mixin.mixins;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.managers.impl.ModuleManager;
import we.devs.opium.client.mixin.iinterface.IClientConnection;
import we.devs.opium.client.systems.events.HandlePacketEvent;
import we.devs.opium.client.systems.events.SendPacketEvent;
import we.devs.opium.client.systems.modules.impl.movement.LiveOverflow;

import static we.devs.opium.client.OpiumClient.Events;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin implements IClientConnection {


    @Shadow protected abstract void sendImmediately(Packet<?> packet, @Nullable PacketCallbacks callbacks, boolean flush);

    @Override
    public void pulse$sendImmediately(Packet<?> packet, boolean flush) {
        sendImmediately(packet, null,  true);
    }

    @Override
    public void pulse$sendImmediately(Packet<?> packet) {
        pulse$sendImmediately(packet, true);
    }

    @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true)
    private static void handlePacket(Packet<?> packet, PacketListener listener, CallbackInfo ci) {
        if(OpiumClient.Events.post(new HandlePacketEvent(packet)).isCancelled()) ci.cancel();

        if(ModuleManager.INSTANCE.getItemByClass(LiveOverflow.class).isEnabled() && LiveOverflow.noWorldBorder.isEnabled() &&
                (packet instanceof WorldBorderCenterChangedS2CPacket ||
                packet instanceof WorldBorderInitializeS2CPacket ||
                packet instanceof WorldBorderSizeChangedS2CPacket ||
                packet instanceof WorldBorderInterpolateSizeS2CPacket ||
                packet instanceof WorldBorderWarningBlocksChangedS2CPacket ||
                packet instanceof WorldBorderWarningTimeChangedS2CPacket)
        ) ci.cancel();
    }

    @Inject(method = "sendImmediately", at = @At("HEAD"), cancellable = true)
    void sp(Packet<?> packet, @Nullable PacketCallbacks callbacks, boolean flush, CallbackInfo ci) {
        if(Events.post(new SendPacketEvent(packet)).isCancelled()) ci.cancel();
    }
}
