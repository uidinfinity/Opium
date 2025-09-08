package we.devs.opium.client.systems.modules.impl.setting;

import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.systems.modules.settings.impl.TextSetting;

public class ChatCommands extends ClientModule {

    public static TextSetting PREFIX = new TextSetting("Prefix", "Prefix for all commands", "$", true);

    public ChatCommands() {
        super("Chat commands", "leaked by 4asik with love <3", -1, Category.SETTING);
        builder(this).settings(PREFIX);
    }
}
