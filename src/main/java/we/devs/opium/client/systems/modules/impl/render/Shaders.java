package we.devs.opium.client.systems.modules.impl.render;

import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.systems.modules.settings.impl.BooleanSetting;
import we.devs.opium.client.utils.annotations.ExcludeModule;
import we.devs.opium.client.utils.annotations.Status;

@ExcludeModule
@Status.Fixme
// outline shader seems to be broken or im using it incorrectly
public class Shaders extends ClientModule {

    public BooleanSetting itemShaders = booleanSetting()
            .name("Item shaders")
            .description("Item shaders")
            .defaultValue(true)
            .build();

    public Shaders() {
        builder(this)
                .name("Shaders")
                .description("Adds shaders")
                .settings(itemShaders)
                .category(Category.RENDER);
    }

}
