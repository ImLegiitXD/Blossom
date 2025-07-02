package net.minecraft.network.play.server;

import java.io.IOException;
import moshi.blossom.util.network.PacketDir;
import moshi.blossom.util.network.Packets;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

public class S47PacketPlayerListHeaderFooter implements Packet<INetHandlerPlayClient> {

    private IChatComponent header;

    public PacketDir pDir() {
        return PacketDir.SERVER;

    }

    private IChatComponent footer;

    public Packets pType() {
        return Packets.S_PLAYER_LIST_HEADER_FOOTER;

    }

public S47PacketPlayerListHeaderFooter() {}

    public S47PacketPlayerListHeaderFooter(IChatComponent headerIn) {
        this.header = headerIn;

    }

    public void readPacketData(PacketBuffer buf) throws IOException {
        this.header = buf.readChatComponent();

        this.footer = buf.readChatComponent();

    }

    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeChatComponent(this.header);

        buf.writeChatComponent(this.footer);

    }

    public void processPacket(INetHandlerPlayClient handler) {
        handler.handlePlayerListHeaderFooter(this);

    }

    public IChatComponent getHeader() {
        return this.header;

    }

    public IChatComponent getFooter() {
        return this.footer;

    }

}
