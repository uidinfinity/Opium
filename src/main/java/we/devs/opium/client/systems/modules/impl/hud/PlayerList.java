package we.devs.opium.client.systems.modules.impl.hud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import we.devs.opium.client.render.renderer.RenderContext;
import we.devs.opium.client.render.ui.color.ThemeInfo;
import we.devs.opium.client.render.ui.gui.screens.HudConfigScreen;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.HudModule;
import we.devs.opium.client.systems.modules.settings.impl.BooleanSetting;
import we.devs.opium.client.systems.modules.settings.impl.ModeSetting;
import we.devs.opium.client.systems.modules.settings.impl.NumberSetting;
import we.devs.opium.client.utils.Util;
import we.devs.opium.client.utils.player.FakePlayer;
import we.devs.opium.client.utils.render.RenderUtil;
import we.devs.opium.client.utils.render.font.FontRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static we.devs.opium.client.OpiumClient.mc;

public class PlayerList extends HudModule {

    BooleanSetting hp = booleanSetting()
            .name("Show health")
            .description("leaked by 4asik with love <3")
            .build();

    BooleanSetting distance = booleanSetting()
            .name("Show distance")
            .description("leaked by 4asik with love <3")
            .build();

    ModeSetting sortBy = modeSetting()
            .name("Sort by")
            .description("leaked by 4asik with love <3")
            .defaultMode("Distance")
            .mode("Health")
            .mode("Name length")
            .mode("None")
            .mode("Distance")
            .build();

    NumberSetting maxSize = numberSetting()
            .name("Max size")
            .description("leaked by 4asik with love <3")
            .range(0, 50)
            .defaultValue(10)
            .stepFullNumbers()
            .build();

    public PlayerList() {
        hudBuilderOf(this)
                .pos(2, 2)
                .area(75, 75)
                .getBuilder()
                .name("PlayerList")
                .description("leaked by 4asik with love <3")
                .settings(hp, distance, sortBy, maxSize)
                .category(Category.HUD);
    }

    @Override
    public void render(DrawContext drawContext, float delta, RenderContext context) {
        if(mc.currentScreen instanceof HudConfigScreen) {
            drawContext.drawBorder((int) x, (int) y, (int) width, (int) height, ThemeInfo.COLORSCHEME.getBorderColor().getRGB());
        }

        FontRenderer text = RenderUtil.textRenderer;

        List<PlayerData> players = new ArrayList<>();
        for (AbstractClientPlayerEntity player : mc.world.getPlayers()) {
            if(player == mc.player) continue;
            players.add(new PlayerData(player.getGameProfile().getName(), player.getHealth(), mc.player.getPos().distanceTo(player.getPos()), player));
        }

        if(players.size() > maxSize.getValueInt()) {
            players.subList(0, maxSize.getValueInt());
        }

        if(sortBy.is("Distance")) players.sort(Comparator.comparingDouble((PlayerData::distance)));
        else if(sortBy.is("Health")) players.sort(Comparator.comparingDouble((PlayerData::health)));
        else if(sortBy.is("Name length")) players.sort(Comparator.comparingInt(value -> -value.name.length()));

        int i = 0;
        for (PlayerData player : players) {
            FontRenderer.ColoredString string = FontRenderer.ColoredString.of((player.entity instanceof FakePlayer ? "[FAKE] " : "") +player.name, context.colorScheme().TEXT());
            if(hp.isEnabled()) string.add(" %sHP ".formatted(Util.round(player.health, 1)), player.health > 10 ? Color.GREEN : (player.health > 5 ? Color.ORANGE : Color.RED));
            if(distance.isEnabled()) string.add(" %sM ".formatted(Util.round(player.distance, 1)), player.distance > 100 ? context.colorScheme().MUTED_TEXT() : (player.distance > 50 ? Color.ORANGE : Color.RED));

            text.drawColoredString(context.getMatrices(), string, (float) x + 1, (float) (y + (text.getHeight("AA") + 2) * i) + 1 + RenderUtil.fontOffsetY);
            i++;
        }

        width = i * (text.getHeight("AA") + 2);
    }

    record PlayerData(String name, double health, double distance, PlayerEntity entity) {}

}
