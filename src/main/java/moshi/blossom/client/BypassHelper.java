package moshi.blossom.client;

import moshi.blossom.event.impl.network.PacketEvent;
import moshi.blossom.util.network.Packets;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class BypassHelper

{
    public BypassHelper() {
        this.handlePacket = (event -> {
            if (event.getPacket().pType() == Packets.S_CONFIRM_TRANSACTION) lastUID = ((S32PacketConfirmTransaction)event.getPacket()).getActionNumber();
        });
    }

    public static int lastUID = 0;

    @EventLink
    public Listener<PacketEvent> handlePacket;

}
