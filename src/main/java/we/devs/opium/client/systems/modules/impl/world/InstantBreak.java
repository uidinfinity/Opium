package we.devs.opium.client.systems.modules.impl.world;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import we.devs.opium.client.managers.impl.ModuleManager;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.renderer.Opium3D;
import we.devs.opium.client.render.ui.color.ThemeInfo;
import we.devs.opium.client.systems.events.BreakBlockEvent;
import we.devs.opium.client.systems.events.Render3DEvent;
import we.devs.opium.client.systems.events.WorldTickEvent;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.systems.modules.settings.impl.BooleanSetting;
import we.devs.opium.client.systems.modules.settings.impl.NumberSetting;
import we.devs.opium.client.utils.timer.TimerUtil;
import we.devs.opium.client.utils.world.BlockUtil;
import we.devs.opium.client.utils.world.PosUtil;

import static we.devs.opium.client.OpiumClient.mc;

public class InstantBreak extends ClientModule {

    public InstantBreak() {
        builder(this)
                .name("Instant break")
                .description("Instantly break blocks after mining once")
                .settings(range, delay)
                .category(Category.WORLD);
    }

    public BlockPos pos = null;
    NumberSetting range = numberSetting()
            .name("Range")
            .description("Range for blocks")
            .range(0, 4.5f)
            .defaultValue(4)
            .build();

    NumberSetting delay = numberSetting()
            .name("Delay")
            .description("Delay between break packets (ticks)")
            .range(0, 10f)
            .defaultValue(1)
            .stepFullNumbers()
            .build();

    BooleanSetting silent = new BooleanSetting("Silent", "mine silently", true, true);

    @EventHandler
    void bb(BreakBlockEvent e) {
        pos = e.getPos();
    }

    @EventHandler
    void render(Render3DEvent e) {
        if(pos != null) {
            Opium3D.renderThroughWalls();
            Opium3D.renderEdged(e.getMatrixStack(), Opium2D.injectAlpha(ThemeInfo.COLORSCHEME.PRIMARY(), 0), ThemeInfo.COLORSCHEME.ACCENT(), Vec3d.of(pos), new Vec3d(1, 1, 1));
        }
    }

    TimerUtil timer = new TimerUtil();
    @EventHandler
    void tick(WorldTickEvent.Pre e) {
        if(pos == null) return;
        if(PosUtil.distanceBetween(Vec3d.of(pos), mc.player.getPos()) <= range.getValue() && shouldMine()) {
            if(timer.hasReached(delay.getValue() * 50)) sendPacket();
            timer.reset();
        } else {
            pos = null;
        }
    }

    @Override
    public void enable() {
        super.enable();
        pos = null;
    }

    public void sendPacket() {
        mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, Direction.UP));
    }

    public boolean shouldMine() {
        return !mc.world.isOutOfHeightLimit(pos) && BlockUtil.isPosBreakable(pos);
    }

    public static boolean isBreaking(BlockPos pos) {
        InstantBreak module = (InstantBreak) ModuleManager.INSTANCE.getItemByClass(InstantBreak.class);
        return module.isEnabled() && pos == module.pos;
    }

    public static boolean isBreaking() {
        InstantBreak module = (InstantBreak) ModuleManager.INSTANCE.getItemByClass(InstantBreak.class);
        return module.isEnabled() && module.pos != null;
    }
}
