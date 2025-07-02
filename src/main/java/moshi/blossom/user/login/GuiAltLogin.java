package moshi.blossom.user.login;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Random;
import moshi.blossom.util.MathUtil;
import net.minecraft.client.gui.*;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

/**
 * GUI screen for alternate Minecraft account login functionality.
 * Handles username/password login, clipboard import, and random account generation.
 */
public final class GuiAltLogin extends GuiScreen {
    private final GuiScreen previousScreen;
    private PasswordField password;
    private AltLoginThread loginThread;
    private GuiTextField username;

    // Button IDs
    private static final int BUTTON_LOGIN = 0;
    private static final int BUTTON_BACK = 1;
    private static final int BUTTON_IMPORT = 2;
    private static final int BUTTON_RANDOM = 4;

    public GuiAltLogin(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case BUTTON_BACK:
                mc.displayGuiScreen(previousScreen);
                break;

            case BUTTON_LOGIN:
                handleLogin();
                break;

            case BUTTON_IMPORT:
                importFromClipboard();
                break;

            case BUTTON_RANDOM:
                generateRandomAccount();
                break;
        }
    }

    private void handleLogin() {
        if (username.getText().isEmpty()) {
            if (loginThread != null) {
                loginThread.setStatus(EnumChatFormatting.RED + "Username cannot be blank.");
            }
            return;
        }

        try {
            loginThread = new AltLoginThread(username.getText(), password.getText());
            loginThread.start();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void importFromClipboard() {
        new Thread(() -> {
            try {
                String clipboardData = (String)Toolkit.getDefaultToolkit()
                        .getSystemClipboard()
                        .getData(DataFlavor.stringFlavor);

                if (!clipboardData.contains(":")) {
                    if (loginThread != null) {
                        loginThread.setStatus(EnumChatFormatting.RED + "Clipboard does not contain required text.");
                    }
                    return;
                }

                // Clean and parse the data
                String cleanData = clipboardData.replaceAll("\n", "");
                String[] credentials = cleanData.split(":");

                // Update UI on main thread
                mc.addScheduledTask(() -> {
                    username.setText(credentials[0]);
                    password.setText(credentials[1]);
                });
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void generateRandomAccount() {
        String chars = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder usernameBuilder = new StringBuilder();
        Random rnd = new Random();

        int length = MathUtil.getRandomInRange(8, 16);
        while (usernameBuilder.length() < length) {
            int index = (int)(rnd.nextFloat() * chars.length());
            usernameBuilder.append(chars.charAt(index));
        }

        loginThread = new AltLoginThread(usernameBuilder.toString(), "");
        loginThread.start();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Draw background
        drawDefaultBackground();

        // Draw text fields
        username.drawTextBox();
        password.drawTextBox();

        // Draw title and status
        drawCenteredString(fontRendererObj, "Alt Login", width / 2, 20, -1);
        String status = (loginThread == null) ?
                EnumChatFormatting.YELLOW + "Idle..." :
                loginThread.getStatus();
        drawCenteredString(fontRendererObj, status, width / 2, 29, -1);

        // Draw placeholder text if fields are empty
        if (username.getText().isEmpty()) {
            drawString(fontRendererObj, "Username / E-Mail", width / 2 - 96, 67, -7829368);
        }
        if (password.getText().isEmpty()) {
            drawString(fontRendererObj, "Password", width / 2 - 96, 107, -7829368);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        // Add buttons
        buttonList.add(new GuiButton(BUTTON_LOGIN, width / 2 - 100, height / 4 + 104, "Login"));
        buttonList.add(new GuiButton(BUTTON_BACK, width / 2 - 100, height / 4 + 128, "Back"));
        buttonList.add(new GuiButton(BUTTON_IMPORT, width / 2 - 100, height / 4 + 152, "Import user:pass"));
        buttonList.add(new GuiButton(BUTTON_RANDOM, width / 2 - 100, height / 4 + 176, "Generate Random Account"));

        // Initialize text fields
        username = new GuiTextField(height / 4 + 24, fontRendererObj, width / 2 - 100, 60, 200, 20);
        password = new PasswordField(fontRendererObj, width / 2 - 100, 100, 200, 20);
        username.setFocused(true);

        Keyboard.enableRepeatEvents(true);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        // Handle tab navigation between fields
        if (typedChar == '\t') {
            boolean usernameFocused = username.isFocused();
            boolean passwordFocused = password.isFocused();

            if (!usernameFocused && !passwordFocused) {
                username.setFocused(true);
            } else {
                username.setFocused(passwordFocused);
                password.setFocused(!passwordFocused);
            }
        }

        // Handle enter key as login
        if (typedChar == '\r') {
            actionPerformed(buttonList.get(0));
        }

        // Forward key events to text fields
        username.textboxKeyTyped(typedChar, keyCode);
        password.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        username.mouseClicked(mouseX, mouseY, mouseButton);
        password.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        username.updateCursorCounter();
        password.updateCursorCounter();
    }
}