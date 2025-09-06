package moshi.blossom;

import java.io.File;

import lombok.Getter;
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

@Getter
public class Blossom {
    public static final Blossom INSTANCE = new Blossom();

    private final ClientInfo clientInfo;
    public final File mainDir;

    private final ProtocolHelper protocolHelper;
    private final UserHelper userHelper;
    private final BypassHelper bypassHelper;
    private final AuraHelper auraHelper;
    private final JoinHelper joinHelper;

    private final EventBus<Event> eventBus;
    private final ModManager modManager;
    private final ComManager comManager;
    private final ConfigManager configManager;

    @EventLink(4)
    public final Listener<ChatEvent> handleChat;
    @EventLink(4)
    public final Listener<KeyEvent> handleKey;
    @EventLink
    public final Listener<RenderEvent> handleRender;
    @EventLink(0)
    public final Listener<MotionEvent> handleMotion;

    public Blossom() {
        this.clientInfo = new ClientInfo("Blossom", "250701", "3.0"); // original: 230209 build
        this.mainDir = new File(Minecraft.getMinecraft().mcDataDir, "Blossom");

        this.protocolHelper = new ProtocolHelper();
        this.userHelper = new UserHelper();
        this.bypassHelper = new BypassHelper();
        this.auraHelper = new AuraHelper();
        this.joinHelper = new JoinHelper();

        this.eventBus = new EventBus<>();
        this.modManager = new ModManager();
        this.comManager = new ComManager();
        this.configManager = new ConfigManager();

        this.handleChat = this::handleChatEvent;
        this.handleKey = this::handleKeyEvent;
        this.handleRender = ClientRotations::update;
        this.handleMotion = ClientRotations::update;
    }

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

    public void startClient() {
        this.protocolHelper.initViaMCP();

        this.eventBus.subscribe(this);
        this.eventBus.subscribe(this.protocolHelper);
        this.eventBus.subscribe(this.auraHelper);
        this.eventBus.subscribe(this.bypassHelper);
        this.eventBus.subscribe(this.joinHelper);

        this.modManager.init();
        this.comManager.init();

        if (!this.mainDir.exists() && this.mainDir.mkdir()) {
            System.out.println("Created main dir");
        }

        this.configManager.init();
        Module.Category.setup();
    }

    public void stopClient() {
        this.eventBus.unsubscribe(this);
        this.eventBus.unsubscribe(this.protocolHelper);
        this.eventBus.unsubscribe(this.auraHelper);
        this.eventBus.unsubscribe(this.bypassHelper);
        this.eventBus.unsubscribe(this.joinHelper);
    }
}
