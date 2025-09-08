package we.devs.opium.client.mixin.mixins;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.SkinTextures;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import we.devs.opium.client.managers.impl.ModuleManager;
import we.devs.opium.client.systems.modules.impl.misc.CoolCape;

import static we.devs.opium.client.OpiumClient.mc;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin {

    @Unique
    private static @NotNull SkinTextures getModifiedSkinTexture(SkinTextures original) {
        return new SkinTextures(original.texture(), original.textureUrl(), CoolCape.getCapeTextures() == null ? original.capeTexture() : CoolCape.getCapeTextures(), original.elytraTexture(), original.model(), original.secure());
    }

    @Inject(method = "getSkinTextures", at = @At("RETURN"), cancellable = true)
    private void modifyCapeTexture(CallbackInfoReturnable<SkinTextures> cir) {
        if (this.equals(mc.player)) {
            SkinTextures original = cir.getReturnValue();
            if (original == null) {
                cir.setReturnValue(cir.getReturnValue());
                return;
            }

            if (ModuleManager.INSTANCE.getItemByClass(CoolCape.class).isEnabled()) {
                SkinTextures modified = getModifiedSkinTexture(original);
                cir.setReturnValue(modified);
            }
        }
    }
}
