package we.devs.opium.client.managers;

import we.devs.opium.client.managers.impl.CommandManager;
import we.devs.opium.client.managers.impl.ModuleManager;
import we.devs.opium.client.utils.BlockManager;
import we.devs.opium.client.utils.VarManger;
import we.devs.opium.client.utils.player.SlotManager;
import we.devs.opium.client.utils.render.shaders.ShaderManager;

public class Managers {
    public static CommandManager COMMAND = CommandManager.INSTANCE;
    public static ModuleManager MODULE = ModuleManager.INSTANCE;
    public static ShaderManager SHADER = new ShaderManager();
    public static VarManger VARIABLE = new VarManger();
    public static SlotManager SLOT = new SlotManager();
    public static BlockManager BLOCK = BlockManager.INSTANCE;
}
