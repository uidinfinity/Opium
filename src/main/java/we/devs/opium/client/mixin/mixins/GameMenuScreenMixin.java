package we.devs.opium.client.mixin.mixins;

import net.minecraft.client.gui.screen.GameMenuScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import we.devs.opium.client.OpiumClient;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin {
    @Inject(method = "disconnect", at = @At("HEAD"), cancellable = true)
    private void onDisconnect(CallbackInfo ci) {
        OpiumClient.INSTANCE.moduleConfigManager.save();
    }
}
