package moshi.blossom.module.impl.misc;

import moshi.blossom.event.impl.TickEvent;
import moshi.blossom.event.impl.network.DisconnectEvent;
import moshi.blossom.event.impl.network.PacketEvent;
import moshi.blossom.module.Module;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class DoggoBan extends Module {

    private S40PacketDisconnect nextDisconnect;

    private int delay;

    @EventLink
    public Listener<DisconnectEvent> handleDisconnect;

    @EventLink
    public Listener<TickEvent> handleTick;

    @EventLink
    public Listener<PacketEvent> handlePacket;

    public DoggoBan() {
        super("DoggoBan", "Doggo Ban", Category.MISC);

        this.nextDisconnect = null;

        this.delay = 0;

        this.handleDisconnect = event -> event.setCanceled(true);

        this.handleTick = event -> {
            if (delay <= 0 && nextDisconnect != null) {
                mc.getNetHandler().onDisconnect(nextDisconnect.getReason());

                nextDisconnect = null;

            }

            delay--;

        };

        this.handlePacket = event -> {
            if (getPlayer() == null) return;

            if (event.getPacket() instanceof S40PacketDisconnect) {
                handleBanPacket((S40PacketDisconnect) event.getPacket());

                event.setCanceled(true);

            }

        };

    }

    private void handleBanPacket(S40PacketDisconnect disconnect) {
        // Format the ban message
        String banMessage = String.join("\n",
        EnumChatFormatting.RED + "You are temporarily banned for" + EnumChatFormatting.RESET + " 29d 23h 59m 59s " + EnumChatFormatting.RED + "from this server!",
        "",
        EnumChatFormatting.GRAY + "Reason: " + EnumChatFormatting.RESET + "WATCHDOG CHEAT DETECTION " + EnumChatFormatting.GRAY + "[GG-3652521]",
        EnumChatFormatting.GRAY + "Find out more: " + EnumChatFormatting.AQUA + EnumChatFormatting.UNDERLINE + "https://www.hypixel.net/watchdog",
        "",
        EnumChatFormatting.GRAY + "Ban ID: " + EnumChatFormatting.RESET + "#2A991B41",
        EnumChatFormatting.GRAY + "Sharing your Ban ID may affect the processing of your appeal!"
        );

        // Format the in-game notification
        String notification = "[WATCHDOG CHEAT DETECTION] " +
        EnumChatFormatting.RED + EnumChatFormatting.BOLD +
        "A player has been removed " +
        EnumChatFormatting.RED + EnumChatFormatting.BOLD +
        "from your game for hacking or abuse.";

        // Set up the disconnect
        disconnect.setReason(new ChatComponentText(banMessage));

        getPlayer().addChatMessage(new ChatComponentText(notification));

        nextDisconnect = disconnect;

        delay = 20;

    }

    public void onEnable() {
        super.onEnable();

        delay = 0;

        nextDisconnect = null;

    }

}
