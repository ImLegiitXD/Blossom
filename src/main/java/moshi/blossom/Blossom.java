package moshi.blossom;

import java.io.File;
import moshi.blossom.client.*;
import moshi.blossom.command.ComManager;
import moshi.blossom.command.Command;
import moshi.blossom.config.ConfigManager;
import moshi.blossom.event.Event;
import moshi.blossom.event.impl.*;
import moshi.blossom.event.impl.player.ChatEvent;
import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.event.impl.render.RenderEvent;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.Module;
import moshi.blossom.ui.guis.AstolfoClickGui;
import moshi.blossom.user.ClientInfo;
import moshi.blossom.util.ClientRotations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;
import nevalackin.homoBus.bus.impl.EventBus;

public class Blossom {
    // Singleton instance
    public static final Blossom INSTANCE = new Blossom();

    // Client information
    private final ClientInfo clientInfo;
    public final File mainDir;

    // Helper instances
    private final ProtocolHelper protocolHelper;
    private final UserHelper userHelper;
    private final BypassHelper bypassHelper;
    private final AuraHelper auraHelper;
    private final JoinHelper joinHelper;

    // Management systems
    private final EventBus<Event> eventBus;
    private final ModManager modManager;
    private final ComManager comManager;
    private final ConfigManager configManager;

    // Event listeners
    @EventLink(4)
    public final Listener<ChatEvent> handleChat;
    @EventLink(4)
    public final Listener<KeyEvent> handleKey;
    @EventLink
    public final Listener<RenderEvent> handleRender;
    @EventLink(0)
    public final Listener<MotionEvent> handleMotion;

    public Blossom() {
        // Initialize client info
        this.clientInfo = new ClientInfo("Blossom", "250701", "3.0"); // original: 230209 build
        this.mainDir = new File(Minecraft.getMinecraft().mcDataDir, "Blossom");

        // Initialize helpers
        this.protocolHelper = new ProtocolHelper();
        this.userHelper = new UserHelper();
        this.bypassHelper = new BypassHelper();
        this.auraHelper = new AuraHelper();
        this.joinHelper = new JoinHelper();

        // Initialize managers
        this.eventBus = new EventBus<>();
        this.modManager = new ModManager();
        this.comManager = new ComManager();
        this.configManager = new ConfigManager();

        // Initialize event handlers
        this.handleChat = this::handleChatEvent;
        this.handleKey = this::handleKeyEvent;
        this.handleRender = ClientRotations::update;
        this.handleMotion = ClientRotations::update;
    }

    // Event handlers
    private void handleChatEvent(ChatEvent event) {
        if (!event.getMessage().startsWith(".")) {
            return;
        }

        event.cancelEvent();
        String cleanStr = event.getMessage().substring(1);
        String[] parts = cleanStr.split(" ");

        for (Command command : getComManager().commandList) {
            boolean found = false;
            for (String alias : command.aliases) {
                if (alias.equals(parts[0])) {
                    found = true;
                    break;
                }
            }

            if (found || command.name.equals(parts[0])) {
                String[] args = new String[parts.length - 1];
                System.arraycopy(parts, 1, args, 0, args.length);
                command.run(args);
            }
        }
    }

    private void handleKeyEvent(KeyEvent event) {
        if (event.getKeyPressed() == 54) {
            Minecraft.getMinecraft().displayGuiScreen(new AstolfoClickGui());
            return;
        }

        for (Module module : getModManager().moduleList) {
            if (module.getToggleKey() == event.getKeyPressed()) {
                module.toggle();
            }
        }
    }

    // Client lifecycle methods
    public void startClient() {
        this.protocolHelper.initViaMCP();

        // Subscribe event listeners
        this.eventBus.subscribe(this);
        this.eventBus.subscribe(this.protocolHelper);
        this.eventBus.subscribe(this.auraHelper);
        this.eventBus.subscribe(this.bypassHelper);
        this.eventBus.subscribe(this.joinHelper);

        // Initialize managers
        this.modManager.init();
        this.comManager.init();

        // Create directory if needed
        if (!this.mainDir.exists() && this.mainDir.mkdir()) {
            System.out.println("Created main dir");
        }

        this.configManager.init();
        Module.Category.setup();
    }

    public void stopClient() {
        // Unsubscribe event listeners
        this.eventBus.unsubscribe(this);
        this.eventBus.unsubscribe(this.protocolHelper);
        this.eventBus.unsubscribe(this.auraHelper);
        this.eventBus.unsubscribe(this.bypassHelper);
        this.eventBus.unsubscribe(this.joinHelper);
    }

    // Getters
    public JoinHelper getJoinHelper() {
        return this.joinHelper;
    }

    public UserHelper getUserHelper() {
        return this.userHelper;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public ComManager getComManager() {
        return this.comManager;
    }

    public ModManager getModManager() {
        return this.modManager;
    }

    public EventBus<Event> getEventBus() {
        return this.eventBus;
    }

    public ClientInfo getClientInfo() {
        return this.clientInfo;
    }
}
