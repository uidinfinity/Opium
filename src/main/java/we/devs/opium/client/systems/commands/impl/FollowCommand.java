package we.devs.opium.client.systems.commands.impl;

import we.devs.opium.client.systems.commands.ClientCommand;
import we.devs.opium.client.utils.player.ChatUtil;

public class FollowCommand extends ClientCommand {
    public FollowCommand() {
        super("follow", "Follow specified player, run again to stop. (Usage: follow <username>)");
    }

    @Override
    public void run(String[] args) {
        // todo
        ChatUtil.sendLocalMsg("Remind me to actually implement this");
    }
}
