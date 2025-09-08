package we.devs.opium.client.systems.modules.impl.render;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.WorldChunk;
import we.devs.opium.client.render.renderer.Opium2D;
import we.devs.opium.client.render.renderer.Opium3D;
import we.devs.opium.client.systems.events.Render3DEvent;
import we.devs.opium.client.systems.events.WorldTickEvent;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.systems.modules.settings.impl.BooleanSetting;
import we.devs.opium.client.systems.modules.settings.impl.NumberSetting;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static we.devs.opium.client.OpiumClient.mc;

public class StorageESP extends ClientModule {

    BooleanSetting chests = booleanSetting()
            .name("Chests")
            .description("Show chests")
            .build();

    BooleanSetting enderChests = booleanSetting()
            .name("Ender chests")
            .description("Show ender chests")
            .build();

    BooleanSetting shulkers = booleanSetting()
            .name("Shulkers")
            .description("Show shulker boxes")
            .build();

    NumberSetting range = numberSetting()
            .name("Range")
            .description("How far")
            .defaultValue(128)
            .range(0, 1024)
            .stepFullNumbers()
            .build();

    public StorageESP() {
        builder()
                .name("StorageESP")
                .description("Show containers trough walls")
                .settings(chests, enderChests, shulkers, range)
                .category(Category.RENDER);
    }

    List<BlockEntity> entities = new CopyOnWriteArrayList<>();
    @EventHandler
    void tick(WorldTickEvent.Post e) {
        ClientWorld world = mc.world;

        // Get the player's current chunk position
        ChunkPos playerChunkPos = new ChunkPos(mc.player.getBlockPos());

        // Define the render distance in chunks
        int renderDistance = mc.options.getClampedViewDistance();

        // Loop through all chunks within the render distance
        entities.clear();
        for (int x = -renderDistance; x <= renderDistance; x++) {
            for (int z = -renderDistance; z <= renderDistance; z++) {
                WorldChunk chunk = world.getChunk(playerChunkPos.x + x, playerChunkPos.z + z);

                for (BlockEntity blockEntity : chunk.getBlockEntities().values()) {
                    if((blockEntity.getType() == BlockEntityType.CHEST || blockEntity.getType() == BlockEntityType.BARREL) && !chests.isEnabled()) continue;
                    if(blockEntity.getType() == BlockEntityType.ENDER_CHEST && !enderChests.isEnabled()) continue;
                    if(blockEntity.getType() == BlockEntityType.SHULKER_BOX && !shulkers.isEnabled()) continue;
                    entities.add(blockEntity);
                }
            }
        }
    }

    @EventHandler
    void render(Render3DEvent event) {
        Opium3D.renderThroughWalls();
        for (BlockEntity entity : entities) {
            Color color;
            BlockEntityType<?> type = entity.getType();
            if(type == BlockEntityType.CHEST || type == BlockEntityType.BARREL) color = new Color(209, 115, 61);
            else if(type == BlockEntityType.ENDER_CHEST) color = new Color(159, 26, 189);
            else if(type == BlockEntityType.SHULKER_BOX) color = new Color(26, 189, 151);
            else color = Color.GRAY;

            Opium3D.renderEdged(event.getMatrixStack(), Opium2D.injectAlpha(color, 100), Opium2D.injectAlpha(color.darker(), 200),
                    Vec3d.of(entity.getPos()), new Vec3d(1, 1, 1));
        }
        Opium3D.stopRenderThroughWalls();
    }

}
