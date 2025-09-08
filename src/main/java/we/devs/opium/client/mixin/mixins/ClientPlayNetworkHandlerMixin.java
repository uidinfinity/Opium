package we.devs.opium.client.mixin.mixins;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.managers.impl.ModuleManager;
import we.devs.opium.client.mixin.iinterface.IExplosionS2CPacket;
import we.devs.opium.client.systems.events.SendChatMessageEvent;
import we.devs.opium.client.systems.modules.impl.movement.Velocity;

import static we.devs.opium.client.systems.modules.impl.movement.Velocity.removeTimer;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {


    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void sendChatMessage(String content, CallbackInfo ci) {
        if(OpiumClient.Events.post(new SendChatMessageEvent(content)).isCancelled()) ci.cancel();
    }

    @Inject(method = "onExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetworkThreadUtils;forceMainThread(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;Lnet/minecraft/util/thread/ThreadExecutor;)V", shift = At.Shift.AFTER))
    void onExplosion(ExplosionS2CPacket packet, CallbackInfo ci) {
        if(ModuleManager.INSTANCE.getItemByClass(Velocity.class).isEnabled()) {
            if(Velocity.mode.is("Vulcan") && removeTimer.hasReached(1000)) {
                removeTimer.reset();
                return;
            }
            ((IExplosionS2CPacket) packet).pulse$setVelocityX(0);
            ((IExplosionS2CPacket) packet).pulse$setVelocityY(0);
            ((IExplosionS2CPacket) packet).pulse$setVelocityZ(0);
        }
    }
}
