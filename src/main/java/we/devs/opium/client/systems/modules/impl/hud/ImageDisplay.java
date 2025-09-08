package we.devs.opium.client.systems.modules.impl.hud;

import me.x150.renderer.render.Renderer2d;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.render.renderer.RenderContext;
import we.devs.opium.client.systems.modules.Category;
import we.devs.opium.client.systems.modules.HudModule;
import we.devs.opium.client.systems.modules.settings.impl.ModeSetting;
import we.devs.opium.client.systems.modules.settings.impl.NumberSetting;
import we.devs.opium.client.systems.modules.settings.impl.TextSetting;
import we.devs.opium.client.utils.render.TextureUtil;
import we.devs.opium.client.utils.timer.TimerUtil;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageDisplay extends HudModule {

    TimerUtil timer = new TimerUtil();

    TextSetting path = textSetting()
            .name("Path")
            .description("leaked by 4asik with love <3")
            .defaultValue("D:\\images.png")
            .build();
    ModeSetting mode = modeSetting()
            .name("Mode")
            .description("leaked by 4asik with love <3")
            .defaultMode("png/jpg/jpeg")
            .mode("gif")
            .mode("png/jpg/jpeg")
            .build();
    NumberSetting delayMS = numberSetting()
            .name("GIF delay")
            .description("leaked by 4asik with love <3")
            .range(0, 5000)
            .defaultValue(200)
            .build();

    public ImageDisplay() {
        hudBuilderOf(this)
                .pos(2, 2)
                .area(50, 50)
                .getBuilder()
                .name("Image Display")
                .description("leaked by 4asik with love <3")
                .category(Category.HUD)
                .settings("File", path)
                .settings("Settings", mode, delayMS);
        timer.reset();

        path.addOnToggle(() -> {
            gifIndex = 0;
            BufferedImage img;
            try
            {
                img = ImageIO.read(new File(path.getValue()));
            }
            catch (IOException e)
            {
                OpiumClient.throwException(e);
                return;
            }
            TextureUtil.registerBufferedImageTexture(Identifier.of("pulse", "memory/imgdisplay.png"), img);
        });
    }

    int gifIndex = 0;
    @Override
    public void render(DrawContext drawContext, float delta, RenderContext context) {
        if(path.getValue() == "") return;
        switch (mode.getCurrent()) {
            case "gif" -> drawGifFrames(new File(path.getValue()), drawContext);
            case "png/jpg/jpeg" -> Renderer2d.renderTexture(context.getMatrices(), Identifier.of("pulse", "memory/imgdisplay.png"), this.x, this.y, this.width, this.height);
        }
    }

    // todo: optimize
    void drawGifFrames(File file, DrawContext context) {
        try {
            ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
            ImageInputStream ciis = ImageIO.createImageInputStream(file);
            reader.setInput(ciis, false);

            int noi = reader.getNumImages(true);
            if(gifIndex >= noi) {
                OpiumClient.LOGGER.warn("gif end: {} >= {}", noi, gifIndex);
                gifIndex = 0;
            }
            BufferedImage image = reader.read(gifIndex);
            if(image != null) {
                TextureUtil.registerBufferedImageTexture(Identifier.of("pulse", "memory/imgdisplay-gif.png"), image);
                Renderer2d.renderTexture(context.getMatrices(), Identifier.of("pulse", "memory/imgdisplay-gif.png"), this.x, this.y, this.width, this.height);
            }
            if(timer.hasReached(delayMS.getValue())) {
                gifIndex++;
                timer.reset();
            }
        } catch (IllegalStateException ignored) {}
        catch (Exception e) {
            OpiumClient.throwException(e);
        }
    }
}
