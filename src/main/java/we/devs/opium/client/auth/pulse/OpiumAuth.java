package we.devs.opium.client.auth.pulse;

import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.auth.pulse.impl.LocalAuthenticator;

/**
 * Authenticator
 */
public class OpiumAuth {
    static Authenticator authenticator = new LocalAuthenticator();
    public static boolean authed = true;

    public static void performAuth() {
        String HWID = authenticator.getHWID();
        if(!authenticator.verifyIntegrity()) {
            OpiumClient.LOGGER.error("HWID integrity check failed!");
            System.exit(-402);
        } else if(!authenticator.checkHWID(HWID)) {
            OpiumClient.LOGGER.error("HWID {} does not own the client!", HWID);
            System.exit(-401);
        }

        authed = true;
    }
}
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3
// leaked by 4asik with love <3





