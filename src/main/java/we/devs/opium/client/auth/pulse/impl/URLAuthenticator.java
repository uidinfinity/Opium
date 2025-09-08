package we.devs.opium.client.auth.pulse.impl;

import com.google.common.hash.Hashing;
import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import we.devs.opium.client.OpiumClient;
import we.devs.opium.client.auth.pulse.Authenticator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class URLAuthenticator implements Authenticator {

    static String[] getHWIDS(String url) throws IOException {
        URL u = new URL(url);
        URLConnection conn = u.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        conn.getInputStream()));
        StringBuffer buffer = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            buffer.append(inputLine);
        in.close();

        String data = buffer.toString();
        return data.split(";");
    }

    String[] hwids = null;

    @Override
    public boolean checkHWID(String HWID) {
        if(hwids == null) {
            try {
                hwids = getHWIDS("https://qweru.xyz/pulse/hwid");
            } catch (Exception e) {
                OpiumClient.throwException(e);
                hwids = new String[]{};
            }
        }

        return Arrays.asList(hwids).contains(HWID);
    }

    @Override
    public String getHWID() {
        StringBuilder hwid = new StringBuilder();

        SystemInfo system = new SystemInfo();
        OperatingSystem os = system.getOperatingSystem();

        String vendor = os.getManufacturer();
        String family = os.getFamily();
        HardwareAbstractionLayer hardware = system.getHardware();
        String id = hardware.getProcessor().getProcessorIdentifier().getProcessorID();
        String identifier = hardware.getProcessor().getProcessorIdentifier().getIdentifier();

        hwid.append(hash(vendor + family + id + identifier));

        hwid.append(":");
        for (GraphicsCard graphicsCard : hardware.getGraphicsCards()) {
            hwid.append(hash(graphicsCard.getDeviceId()));
        }


        OpiumClient.LOGGER.warn("HWID: {}", hwid.toString());
        return hwid.toString();
    }

    // todo
    @Override
    public boolean verifyIntegrity() {
        return true;
    }

    private static String hash(String s) {
        return Hashing.sha256().hashString(s, StandardCharsets.UTF_8).toString();
    }
}
