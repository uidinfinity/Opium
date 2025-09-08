package we.devs.opium.client.systems.modules.impl.misc;

import net.minecraft.item.Items;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.systems.modules.settings.impl.NumberSetting;
import we.devs.opium.client.utils.Util;
import we.devs.opium.client.utils.player.ChatUtil;
import we.devs.opium.client.utils.player.InventoryUtils;
import we.devs.opium.client.utils.player.SlotUtil;
import we.devs.opium.client.utils.thread.ThreadManager;

import static we.devs.opium.client.OpiumClient.mc;

public class TotemDupe extends ClientModule {

    private final NumberSetting dupeItemCount = new NumberSetting("Dupe item count", "How many times should dupe", 1f, 64f, 16f, true);
    private final NumberSetting delay = new NumberSetting("Delay", "time in ms between actions", 0f, 1000f, 50f, true);

    // todo
//    BooleanSetting auto = booleanSetting()
//            .name("Auto")
//            .description("Automatically dupe once totem count is below the set number")
//            .build();
//
//    private final NumberSetting autoC = new NumberSetting("Trigger Count",
//            "if auto is enabled, totems will be automatically duped if the total count is smaller or equal to this number",
//            1f, 36f, 10f, true);


    public TotemDupe() {
        builder()
                .name("Totem Dupe")
                .description("On play.dupeanarchy.com, bypass for quickly duping totems")
                .category(Category.MISC)
                .settings(dupeItemCount, delay);
        dupeItemCount.setValueModifier((value -> (int) value));
        delay.setValueModifier((value -> (int) value));
    }

    @Override
    public void enable() {
        super.enable();
        toggle();
        ThreadManager.cachedPool.submit(() -> {
            if(Util.nullCheck()) return;
            int c = dupeItemCount.getValueInt();
            int slot = InventoryUtils.getItemSlotAll(Items.TOTEM_OF_UNDYING);
            if(slot == -1) return;
            int to = mc.player.getInventory().selectedSlot;
            SlotUtil.swapInv(slot, to);
            Util.sleep(delay.getValueLong());
            ChatUtil.sendServerMsg("/dupe " + c);
            Util.sleep(delay.getValueLong());
            SlotUtil.swapBack();
        });
    }
}
