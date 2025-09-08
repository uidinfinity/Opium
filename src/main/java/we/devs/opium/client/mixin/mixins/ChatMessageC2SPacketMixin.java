package we.devs.opium.client.mixin.mixins;

import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import we.devs.opium.client.mixin.iinterface.IChatMessageC2SPacket;

@Mixin(ChatMessageC2SPacket.class)
public class ChatMessageC2SPacketMixin implements IChatMessageC2SPacket {

    @Mutable
    @Shadow @Final private String chatMessage;

    @Override
    public void pulse$setMessage(String message) {
        this.chatMessage = message;
    }

}
