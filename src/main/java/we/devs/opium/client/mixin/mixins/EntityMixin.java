package we.devs.opium.client.mixin.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.systems.events.PlayerMoveEvent;

import static we.devs.opium.client.OpiumClient.mc;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    void move(MovementType movementType, Vec3d movement, CallbackInfo ci) {
        Entity _this = ((Entity) (Object) this);

        if(!(_this == mc.player)) return;
        PlayerMoveEvent event = new PlayerMoveEvent(movementType, movement);
        OpiumClient.Events.post(event);
        if(event.isCancelled()) ci.cancel();
    }

    @Inject(method = "isSneaking", at = @At("HEAD"), cancellable = true)
    void s(CallbackInfoReturnable<Boolean> cir) {
//        if(Managers.MODULE.getItemByClass(Scaffold.class).isEnabled()) cir.setReturnValue(true);
    }

}
