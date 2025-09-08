package we.devs.opium.client.systems.commands.impl;

import we.devs.opium.client.systems.commands.ClientCommand;
import we.devs.opium.client.utils.player.ChatUtil;
import we.devs.opium.client.utils.world.PacketUtil;

import static we.devs.opium.client.OpiumClient.mc;

public class VclipCommand extends ClientCommand {
    public VclipCommand() {
        super("vclip", "vertical clip (no paperclip support yet)");
    }

    @Override
    public void run(String[] args) {
        if(args.length != 2) {
            ChatUtil.err("Usage: vclip <n>");
            return;
        }

        try {
            double y = Double.parseDouble(args[1]);
            PacketUtil.sendMove(mc.player.getPos().add(0, y, 0));
            mc.player.setPosition(mc.player.getPos().add(0, y, 0));
            ChatUtil.sendLocalMsg("Vclipped " + y + " blocks");
        } catch (Exception e) {
            ChatUtil.err("Usage: vclip <n>");
            e.printStackTrace();
        }
    }
}
