package we.devs.opium.client.systems.modules.impl.world;

import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.utils.annotations.ExcludeModule;

@ExcludeModule
public class Suffix extends ClientModule {
    public Suffix() {
        super("Chat", "Adds a suffix to all your messages", -1, Category.WORLD);
    }
}
