package we.devs.opium.client.integration.discord;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.utils.annotations.Status;

import java.time.Instant;

/**
 * fixme
 */
@Status.Fixme
public class RPC {

    public static Core CORE = null;

    public static void thread_rpc() {
        OpiumClient.LOGGER.info("Loading discord RPC");
        try(CreateParams params = new CreateParams())
        {
            params.setClientID(1262743759012106332L);
            params.setFlags(CreateParams.getDefaultFlags());
            OpiumClient.LOGGER.info("Set parameters");

            try(Core core = new Core(params))
            {
                CORE = core;
                try(Activity activity = new Activity())
                {
                    activity.setDetails("%s %s".formatted(OpiumClient.NAME, OpiumClient.VERSION));
                    activity.setState(":3");

                    // Setting a start time causes an "elapsed" field to appear
                    activity.timestamps().setStart(Instant.now());

                    activity.party().size().setMaxSize(9);
                    activity.party().size().setCurrentSize(6);

                    // Make a "cool" image show up
                    activity.assets().setLargeImage("pulse_icon");

                    // Finally, update the current activity to our activity
                    core.activityManager().updateActivity(activity);
                    OpiumClient.LOGGER.info("Created and updated activity");
                } catch (Exception e) {
                    OpiumClient.LOGGER.error("[stage: ACTIVITY] Error while running discord RPC!! {}: {}", e.getCause(), e.getMessage());
                }
                OpiumClient.LOGGER.info("Finished login, started callback loop.");

                while(true)
                {
                    core.runCallbacks();
                    try
                    {
                        // Sleep a bit to save CPU
                        Thread.sleep(16);
                    }
                    catch(InterruptedException e)
                    {
                        OpiumClient.throwException(e);
                    }
                }
            } catch (Exception e) {
                OpiumClient.LOGGER.error("[stage: CORE] Error while running discord RPC!! {}: {}", e.getCause(), e.getMessage());
            }
        } catch (Exception e) {
            OpiumClient.LOGGER.error("[stage: PARAMS] Error while running discord RPC!! {}: {}", e.getCause(), e.getMessage());
        }
        OpiumClient.LOGGER.error("[stage: ???] Error while running discord RPC!! (stopped?)");
    }

    public static void second() {

    }
}
