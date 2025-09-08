package we.devs.opium.client.systems.config;

import net.minecraft.nbt.*;
import we.devs.opium.client.OpiumClient;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

public class ConfigLoader {

    protected Path filepath;

    public ConfigLoader(Path filepath) {
        this.filepath = filepath;
    }

    /**
     * NBTList setting order:
     * 0 - module name
     * 1 - module description
     * 2 - category
     * 3 - bind (implement!)
     * 4+ - other settings (format: settingname::valuetype::value) (implement!)
     */

    public void load() {
        OpiumClient.LOGGER.info("Using config from {}", filepath.toString());
        if(!filepath.toFile().exists()) {
            OpiumClient.LOGGER.error("Config doesn't exist!");
            save();
            return;
        }
        try {
            NbtCompound config = NbtIo.read(filepath);
            try {
                parseSaveData(config);
            } catch (Exception e) {
                OpiumClient.LOGGER.error("Invalid NBT data! {}: {}", e.getLocalizedMessage(), Arrays.toString(e.getStackTrace()));
            }
        } catch (IOException e) {
            OpiumClient.LOGGER.error("Cannot read file {}! (corrupted?)", filepath.toString());
            OpiumClient.LOGGER.error(e.getMessage());
        }
    }

    public void save() {
        String pref = getPrefix();
        if(!filepath.getParent().toFile().exists()) {
            OpiumClient.INSTANCE.firstRun();
            OpiumClient.LOGGER.info("Creating config folder");
            filepath.getParent().toFile().mkdir();
        }
        if(!filepath.toFile().exists()) {
            try {
                OpiumClient.LOGGER.info("Creating config file");
                filepath.toFile().createNewFile();
            } catch (IOException e) {
                OpiumClient.LOGGER.error("Cannot create config file: {}", e.getMessage());
                return;
            }
        }

        NbtCompound config = new NbtCompound();
        NbtList data = getTargetData();
        config.put("data", data);
        try {
            NbtIo.write(config, filepath);
        } catch (IOException e) {
            OpiumClient.LOGGER.error("Cannot save config: {}", e.getMessage());
        }
        OpiumClient.LOGGER.info("[{}] saved config!", pref);
    }


    public NbtList getTargetData() {
        return new NbtList();
    }

    public void parseSaveData(NbtCompound data) {}

    public String getPrefix() { return ""; }

}
