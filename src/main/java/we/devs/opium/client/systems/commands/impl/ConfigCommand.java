package we.devs.opium.client.systems.commands.impl;

import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.systems.commands.ClientCommand;
import we.devs.opium.client.utils.player.ChatUtil;

import java.nio.file.Path;

public class ConfigCommand extends ClientCommand {
    public ConfigCommand() {
        super("config", "save / load config (name for default config is \"config.nbt\"c ");
    }

    @Override
    public void run(String[] args) {
        if(args.length != 3) {
            ChatUtil.err("Usage: config <save / load> <name>");
            return;
        }

        String command = args[1];
        String file = args[2];
        Path path;
        try {
            path = OpiumClient.CONFIG.resolve(file);
        } catch (Exception e) {
            ChatUtil.err("Invalid name!");
            return;
        }
        switch (command.toLowerCase()) {
            case "load" -> {
                OpiumClient.INSTANCE.moduleConfigManager.quickLoad(path);
                ChatUtil.sendLocalMsg("Loaded!");
            }
            case "save" -> {
                OpiumClient.INSTANCE.moduleConfigManager.quickSave(path);
                ChatUtil.sendLocalMsg("Saved!");
            }
            default -> ChatUtil.err("Usage: config <save / load> <name>");
        }
    }
}
