package we.devs.opium.client.auth.pulse;

public interface Authenticator {
    boolean checkHWID(String HWID);
    String getHWID();
    boolean verifyIntegrity();
}
