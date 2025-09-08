package we.devs.opium.client.systems.commands.impl;

import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.systems.commands.ClientCommand;
import we.devs.opium.client.utils.player.ChatUtil;

public class RemoveFriendCommand extends ClientCommand {
    public RemoveFriendCommand() {
        super("removeFriend", "remove a player from your friends list");
    }

    @Override
    public void run(String[] args) {
        if(args.length != 2) {
            ChatUtil.err("Invalid arguments! Usage: removeFriend <username>");
            return;
        }
        if(OpiumClient.friendSystem.removePlayer(args[1])) ChatUtil.warn("Could not find " + args[1] + " in your friends list.");
        else ChatUtil.sendLocalMsg("Removed!");
    }
}
