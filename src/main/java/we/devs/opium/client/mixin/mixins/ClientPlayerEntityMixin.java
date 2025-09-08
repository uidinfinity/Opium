package we.devs.opium.client.mixin.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import we.devs.opium.client.managers.impl.ModuleManager;
import we.devs.opium.client.systems.modules.impl.movement.Velocity;
import we.devs.opium.client.utils.player.RotationUtil;

import static we.devs.opium.client.OpiumClient.mc;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    @Unique
    private static Float[] angle;

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci){
        RotationUtil.tick();
        assert mc.player != null;
        angle = new Float[]{mc.player.getPitch(), mc.player.getYaw()};
//        Rotate.prevRotation = new float[]{mc.player.getPitch(), mc.player.getYaw()};
        if(!RotationUtil.shouldRotate()) return;
        setPitch(RotationUtil.keepPitch);
        setYaw(RotationUtil.keepYaw);
    }


    @Inject(method = "tick", at = @At("TAIL"))
    public void tickEnd(CallbackInfo ci){
        setPitch(angle[0]);
        setYaw(angle[1]);
        angle = null;
    }

    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    void pushFromBlocks(double x, double z, CallbackInfo ci) {
        if(ModuleManager.INSTANCE.getItemByClass(Velocity.class).isEnabled() && !Velocity.mode.is("Vulcan")) ci.cancel();
    }

//    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/packet/Packet;)V", ordinal = 2))
//    private void sendPacketFull(ClientPlayNetworkHandler instance, Packet packet) {
//        networkHandler.sendPacket(RotationUtil.onFull((PlayerMoveC2SPacket.Full) packet));
//    }
//
//    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/packet/Packet;)V", ordinal = 3))
//    private void sendPacketPosGround(ClientPlayNetworkHandler instance, Packet<?> packet) {
//        networkHandler.sendPacket(RotationUtil.onPosOnGround((PlayerMoveC2SPacket.PositionAndOnGround) packet));
//    }
//
//    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/packet/Packet;)V", ordinal = 4))
//    private void sendPacketLookGround(ClientPlayNetworkHandler instance, Packet<?> packet) {
//        PlayerMoveC2SPacket toSend = RotationUtil.onLookOnGround((PlayerMoveC2SPacket.LookAndOnGround) packet);
//        networkHandler.sendPacket(toSend);
//    }
//
//    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/packet/Packet;)V", ordinal = 5))
//    private void sendPacketGround(ClientPlayNetworkHandler instance, Packet<?> packet) {
//        networkHandler.sendPacket(RotationUtil.onOnGroundOnly((PlayerMoveC2SPacket.OnGroundOnly) packet));
//    }
}
