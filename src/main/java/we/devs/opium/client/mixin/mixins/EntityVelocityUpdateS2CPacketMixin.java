package we.devs.opium.client.mixin.mixins;

import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import we.devs.opium.client.mixin.iinterface.IEntityVelocityUpdateS2CPacket;

@Mixin(EntityVelocityUpdateS2CPacket.class)
public class EntityVelocityUpdateS2CPacketMixin implements IEntityVelocityUpdateS2CPacket {


    @Mutable
    @Shadow @Final private int velocityX;


    @Mutable
    @Shadow @Final private int velocityY;

    @Mutable
    @Shadow @Final private int velocityZ;

    @Override
    public void pulse$setX(int x) {
        this.velocityX = x;
    }

    @Override
    public void pulse$setY(int y) {
        this.velocityY = y;
    }

    @Override
    public void pulse$setZ(int z) {
        this.velocityZ = z;
    }
}
