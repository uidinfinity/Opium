package we.devs.opium.client.systems.commands.impl;

import net.minecraft.util.math.Vec3d;
import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.systems.commands.ClientCommand;
import we.devs.opium.client.systems.modules.impl.world.TravelSettings;
import we.devs.opium.client.utils.player.ChatUtil;
import we.devs.opium.client.utils.player.MovementUtil;
import we.devs.opium.client.utils.world.PacketUtil;
import we.devs.opium.client.utils.Util;

import static we.devs.opium.client.OpiumClient.mc;

public class TravelCommand extends ClientCommand {
    public TravelCommand() {
        super("travel", "Automatically travel to given coordinates (PAPER ONLY). Usage: travel <x> <z>");
    }

    @Override
    public void run(String[] args) {
        StopMoveCommand.terminate = false;
        if(args.length < 3) {
            ChatUtil.err("Usage: travel <x> <z>");
            return;
        }

        Vec3d pos;

        try {
            pos = new Vec3d(Float.parseFloat(args[1]), TravelSettings.travelY.getValue(), Float.parseFloat(args[2]));
        } catch (Exception ignore) {
            ChatUtil.err("Usage: travel <x> <z>");
            return;
        }


        ChatUtil.sendLocalMsg("Traveling to: %s %s".formatted(args[1], args[2]));

        for(int i = 1; i <= 3; i++) {
            if(mc.player.getPos().y >= TravelSettings.travelY.getValue()) break;
            MovementUtil.buildVClip(mc.player.getPos());
            PacketUtil.sendMove(mc.player.getPos().add(0, 99, 0));
            OpiumClient.LOGGER.debug("Built vclip, tpd 99 blocks up");
            try {
                wait(200);
                OpiumClient.LOGGER.info("slept");
            } catch (Exception ignored) {};
        }

        Vec3d finalPos = pos;
        Util.delay(() -> {
            if(TravelSettings.shouldRepeatTP.isEnabled()) {
                new Thread(() -> {
                    Vec3d nextPos = MovementUtil.moveTowards( // to prevent out-of-sync tps
                            mc.player.getPos(),
                            finalPos
                    );
                    while(!StopMoveCommand.terminate) {
                        try {

                            if(TravelSettings.doubleTP.isEnabled()) PacketUtil.sendMove(nextPos);
                            Thread.sleep(80);
                            nextPos = MovementUtil.moveTowards(
                                    nextPos,
                                    finalPos
                            );

                        } catch (Exception e) {
                            ChatUtil.err("Usage: travel <x> <z>");
                            OpiumClient.LOGGER.info("[travel] Exception while traveling: {}", e.getMessage());
                            break;
                        }
                    }
                    if(!Util.nullCheck(mc)) mc.player.setPos(finalPos.x, finalPos.y, finalPos.z);
                }).start();
            }

            else MovementUtil.moveTo(mc.player.getPos(), finalPos, false);

        }, 500);
    }
}
