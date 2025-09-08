package we.devs.opium.client.mixin.mixins;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.systems.events.BreakBlockEvent;
import we.devs.opium.client.systems.events.StartBreakingBlockEvent;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {

    @Shadow protected abstract void syncSelectedSlot();

    @Inject(method = "breakBlock", at = @At("HEAD"), cancellable = true)
    void blockBreak(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if(OpiumClient.Events.post(new BreakBlockEvent(pos)).isCancelled()) cir.cancel();
        syncSelectedSlot();
    }

    @Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
    private void onAttackBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (OpiumClient.Events.post(new StartBreakingBlockEvent(pos, direction)).isCancelled()) cir.cancel();
    }

}
