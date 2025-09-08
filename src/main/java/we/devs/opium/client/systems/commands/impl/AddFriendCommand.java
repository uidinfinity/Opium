package we.devs.opium.client.systems.commands.impl;

import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.systems.commands.ClientCommand;
import we.devs.opium.client.utils.player.ChatUtil;

public class AddFriendCommand extends ClientCommand {
    public AddFriendCommand() {
        super("addFriend", "add a player to your friends list");
    }

    @Override
    public void run(String[] args) {
        if(args.length != 2) {
            ChatUtil.err("Invalid arguments! Usage: addFriend <username>");
            return;
        }
        OpiumClient.friendSystem.addPlayer(args[1]);
        ChatUtil.sendLocalMsg("Added!");
    }
}
