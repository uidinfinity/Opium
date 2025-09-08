package we.devs.opium.client;

import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.IEventBus;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.UpdateSelectedSlotS2CPacket;
import org.lwjgl.glfw.GLFW;
import we.devs.opium.client.auth.pulse.OpiumAuth;
import we.devs.opium.client.managers.Managers;
import we.devs.opium.client.managers.impl.WindowManager;
import we.devs.opium.client.systems.config.ConfigLoader;
import we.devs.opium.client.systems.config.impl.ModuleConfigLoader;
import we.devs.opium.client.systems.config.impl.StyleConfigLoader;
import we.devs.opium.client.integration.discord.RPC;
import we.devs.opium.client.systems.events.*;
import we.devs.opium.client.managers.impl.ModuleManager;
import we.devs.opium.client.systems.modules.impl.hud.Notifications;
import we.devs.opium.client.systems.modules.impl.misc.Macro;
import we.devs.opium.client.systems.player.PlayerSystem;
import we.devs.opium.client.systems.player.impl.FriendSystem;
import we.devs.opium.client.systems.player.impl.RageSystem;
import we.devs.opium.client.systems.modules.ClientModule;
import we.devs.opium.client.managers.impl.ThemeManager;
import we.devs.opium.client.systems.modules.impl.setting.ClickGUI;
import we.devs.opium.client.utils.ExceptionHandler;
import we.devs.opium.client.utils.OpiumLogger;
import we.devs.opium.client.utils.QueueUtil;
import we.devs.opium.client.utils.Util;
import we.devs.opium.client.utils.player.ChatUtil;
import we.devs.opium.client.utils.player.RotationUtil;
import we.devs.opium.client.utils.render.RenderUtil;
import we.devs.opium.client.utils.thread.ThreadManager;
import we.devs.opium.client.utils.timer.TimerUtil;

import java.awt.*;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;

public class OpiumClient implements ModInitializer {

	public static final OpiumLogger LOGGER = new OpiumLogger("Opium");
	public static IEventBus Events;
	public static MinecraftClient mc;
	public static OpiumClient INSTANCE;
	public boolean isFirstRun = false;

	public static final boolean debug = true;
	public static final String NAME = "Opium";
	public static final String VERSION = "v1488+cracked";
	public static final String AUTHOR = "_42team";
	public static final Color COLOR = new Color(243, 86, 243);
	public static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

	public ModuleConfigLoader moduleConfigManager;
	public ConfigLoader styleConfigManager;
	public ThemeManager themeManager = new ThemeManager();
	public WindowManager windowManager = new WindowManager();
	public static Path CONFIG;
	public static PlayerSystem friendSystem;
	public static PlayerSystem rageSystem;

	public static MatrixStack lastMatrices = null;

	@Override
	public void onInitialize() {
		OpiumClient.LOGGER.info("Starting opium client");

		OpiumLogger.TimedLogger logger = LOGGER.getTimed();
		TimerUtil globTimer = new TimerUtil();

		Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler.getExceptionHandler());
		Thread.currentThread().setUncaughtExceptionHandler(ExceptionHandler.getExceptionHandler());
		logger.debug("Loaded error handler");

		//OpiumAuth.performAuth();
		logger.debug("Finished auth");
		if(!OpiumAuth.authed) {
			LOGGER.error("Auth failed but didn't exit!!");
			System.exit(-1);
		}

		CONFIG = FabricLoader.getInstance().getConfigDir().getParent().resolve("pulse");
		moduleConfigManager = new ModuleConfigLoader(CONFIG.resolve("config.nbt"));
		styleConfigManager = new StyleConfigLoader(CONFIG.resolve("uiConfig.nbt"));
		themeManager.setTheme("Gruvbox Dark Orange");
		logger.debug("Loaded themes, config");

		mc = MinecraftClient.getInstance();
		INSTANCE = this;

		Events = new EventBus();
		Events.registerLambdaFactory("we.devs.opium", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));
		Events.subscribe(this);
		OpiumClient.Events.subscribe(ModuleManager.INSTANCE);
		Managers.BLOCK.init();
		logger.debug("Loaded events");

		new Thread(styleConfigManager::load).start();
		new Thread(moduleConfigManager::load).start();
		logger.debug("Loaded config");
		friendSystem = new FriendSystem();
		rageSystem = new RageSystem();
		logger.debug("Loaded player systems");

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			LOGGER.info("Shutting down...");
			ModuleManager.INSTANCE.getItemList().forEach(module -> {
                if (module.shouldDisableOnExit()) {
                    module.setEnabled(false);
                }
            });
			moduleConfigManager.save();
			styleConfigManager.save();
			friendSystem.shutdown();
			rageSystem.shutdown();
			ThreadManager.stopAll();
		}));

		WorldRenderEvents.BEFORE_DEBUG_RENDER.register((end) -> {
			if(Util.nullCheck(mc))  {
				return;
			}

			lastMatrices = end.matrixStack();
			if(OpiumClient.Events.post(new Render3DEvent(end.positionMatrix(), end.projectionMatrix(), end.lightmapTextureManager(), end.gameRenderer(), end.camera(), end.blockOutlines(), end.tickCounter(), end.matrixStack(), end.frustum())).isCancelled()) LOGGER.error("cannot cancel Render3D event");
		});

		RenderUtil.initFont();
		logger.debug("Loaded visuals");

		ThreadManager.fixedPool.submit(RPC::thread_rpc);
//		ThreadManager.fixedPool.submit(SpotifyIntegration::init);

//		((ISession) mc.getSession()).pulse$setUsername("PulseUser69");

//		QueueUtil.onWorldLoad(() -> {
//			Util.sleep(4000);
//			ChatUtil.sendLocalMsg(
//					Text.empty()
//							.append(Text.of("Welcome, "))
//							.append(Text.empty().append(mc.player.getGameProfile().getName()).formatted(Formatting.GOLD))
//							.append(Text.empty().append("!"))
//			);
//		});

		LOGGER.info("Loaded {} {} by {}", NAME, VERSION, AUTHOR);
		globTimer.reset();
	}

	public static void throwException(Throwable t) {
		ExceptionHandler.getExceptionHandler().uncaughtException(Thread.currentThread(), t);
	}

	public void firstRun() {
		isFirstRun = true;
		QueueUtil.onWorldLoad(() -> ChatUtil.info("Default gui key is Right Control, you can bind modules by middle clicking on them."));
	}

	@EventHandler
	public void onKey(KeyEvent event) {
		if(event.getAction() != GLFW.GLFW_PRESS) return;
		if(event.getKey() == -1) return;
		for(ClientModule m : ModuleManager.INSTANCE.getItemList()) {
			if(m.getBind() == event.getKey()) {
				if(mc.currentScreen == null) m.toggle();
				else if(m instanceof ClickGUI) m.toggle();
				else continue;
				if(!(m instanceof ClickGUI || m instanceof Macro)) {
					if(Notifications.notify("Toggle", (m.isEnabled() ? "Enabled " : "Disabled ")+ m.getName(), Notifications.Type.INFO)) return;
					ChatUtil.sendLocalMsg((m.isEnabled() ? "Enabled " : "Disabled ") + m.getName());
				}
			}
		}
	}

	@EventHandler
	void tick(TickEvent.Pre t) {
		Managers.VARIABLE.update();
		Managers.SLOT.tick();
//		RotationUtil.tick();
	}

	@EventHandler
	void packet2C(HandlePacketEvent e) {
		if(e.getPacket() instanceof UpdateSelectedSlotS2CPacket p && Managers.SLOT.update(p)) e.cancel();
	}

	@EventHandler
	void packet2S(SendPacketEvent e) {
		if(e.getPacket() instanceof UpdateSelectedSlotC2SPacket p) Managers.SLOT.update(p);
		if(e.getPacket() instanceof PlayerMoveC2SPacket mp &&
				(mp.getYaw(mc.player.getYaw()) != RotationUtil.keepYaw || mp.getPitch(mc.player.getPitch()) != RotationUtil.keepPitch)
		&& RotationUtil.shouldRotate()) {
			RotationUtil.sendPacket();
		}
	}
}
