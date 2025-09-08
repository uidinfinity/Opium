package we.devs.opium.client.systems.modules.impl.misc;

import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.systems.modules.settings.builders.BooleanSettingBuilder;
import we.devs.opium.client.systems.modules.settings.impl.BooleanSetting;
import we.devs.opium.client.systems.modules.settings.impl.TextSetting;

public class Chat extends ClientModule {

    public static TextSetting suffix = new TextSetting("Suffix text", "Will be appended to all chat", " ⎥ ｅｕｐｈｏｒｉｘ", true);
    public static BooleanSetting fancyChat = new BooleanSettingBuilder()
            .name("Fancy chat")
            .description("Replaces your chars with ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴩqʀꜱᴛᴜᴠᴡxyᴢ")
            .defaultValue(false)
            .shouldShow(true)
            .build();
    public static BooleanSetting onlySuffix = new BooleanSettingBuilder()
            .name("Only suffix")
            .description("Only make the suffix 'fancy'")
            .defaultValue(false)
            .build();

    public static BooleanSetting timestamps = new BooleanSettingBuilder()
            .name("Timestamps")
            .description("Adds timestamps to chat")
            .build();

    public static BooleanSetting noBG = new BooleanSettingBuilder()
            .name("No background")
            .description("Remove chat background")
            .build();

    public static BooleanSetting font = new BooleanSettingBuilder()
            .name("Font")
            .description("Use custom font")
            .build();

    public static BooleanSetting slideIn = new BooleanSettingBuilder()
            .name("Slide in")
            .description("All new messages have an animation")
            .build();

    public Chat() {
        super("Chat", "Chat options", -1, Category.MISC);
        builder(this).settings(suffix, fancyChat, onlySuffix, timestamps, font, slideIn, noBG);

        fancyChat.addOnToggle(() -> {
            onlySuffix.setShouldShow(fancyChat.isEnabled());
        });
    }


}
