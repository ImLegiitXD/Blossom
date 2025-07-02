package moshi.blossom.user.login;

import fr.litarvan.openauth.microsoft.*;
import fr.litarvan.openauth.microsoft.model.response.MinecraftProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Session;

/**
 * Thread for handling Minecraft account authentication.
 * Supports both offline (cracked) and Microsoft account login.
 */
public final class AltLoginThread extends Thread {
    private static final Minecraft MC = Minecraft.getMinecraft();

    private final String password;
    private final String username;
    private String status;

    /**
     * Creates a new AltLoginThread with credentials
     * @param username The account username/email
     * @param password The account password (empty for offline mode)
     */
    public AltLoginThread(String username, String password) {
        super("Alt Login Thread");
        this.username = username;
        this.password = password;
        this.status = EnumChatFormatting.GRAY + "Waiting...";
    }

    @Override
    public void run() {
        if (this.password.isEmpty()) {
            handleOfflineLogin();
            return;
        }
        handleMicrosoftLogin();
    }

    /**
     * Handles offline (cracked) account login
     */
    private void handleOfflineLogin() {
        MC.session = new Session(
                this.username,
                "",
                "",
                "mojang"
        );
        this.status = EnumChatFormatting.GREEN + "Logged in as " + this.username + " (offline name)";
    }

    /**
     * Handles Microsoft account authentication
     */
    private void handleMicrosoftLogin() {
        this.status = EnumChatFormatting.YELLOW + "Logging in...";
        Session authSession = authenticateMicrosoftAccount();

        if (authSession == null) {
            this.status = EnumChatFormatting.RED + "Login failed!";
        } else {
            this.status = EnumChatFormatting.GREEN + "Logged in as " + authSession.getUsername();
            MC.session = authSession;
        }
    }

    /**
     * Authenticates with Microsoft servers
     * @return New Session if successful, null otherwise
     */
    private Session authenticateMicrosoftAccount() {
        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        try {
            MicrosoftAuthResult result = authenticator.loginWithCredentials(username, password);
            MinecraftProfile profile = result.getProfile();
            return new Session(
                    profile.getName(),
                    profile.getId(),
                    result.getAccessToken(),
                    "microsoft"
            );
        } catch (MicrosoftAuthenticationException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the current login status
     * @return Formatted status message
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Sets the login status
     * @param status New status message
     */
    public void setStatus(String status) {
        this.status = status;
    }
}