package we.devs.opium.client.systems.modules.impl.misc;

import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.systems.modules.settings.impl.TextSetting;
import we.devs.opium.client.utils.Util;
import we.devs.opium.client.utils.player.ChatUtil;

public class Macro extends ClientModule {

    public static TextSetting suffix = new TextSetting("Text", "What command / message to send", "/dupe 64", true);

    public Macro() {
        super("Macro", "send message / command with a keybind", -1, Category.MISC);
        builder(this).settings(suffix);
    }

    @Override
    public void enable() {
        super.enable();

        if(Util.nullCheck()) {
            toggle();
            return;
        }
        ChatUtil.sendServerMsg(suffix.getValue());

        this.toggle();
    }
}
