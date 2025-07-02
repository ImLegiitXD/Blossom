package net.minecraft.network.play.server;

import java.io.IOException;
import moshi.blossom.util.network.PacketDir;
import moshi.blossom.util.network.Packets;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

public class S40PacketDisconnect implements Packet<INetHandlerPlayClient> {

    private IChatComponent reason;

    public PacketDir pDir() {
        return PacketDir.SERVER;

    }

    public Packets pType() {
        return Packets.S_DISCONNECT;

    }

public S40PacketDisconnect() {}

    public S40PacketDisconnect(IChatComponent reasonIn) {
        this.reason = reasonIn;

    }

    public void readPacketData(PacketBuffer buf) throws IOException {
        this.reason = buf.readChatComponent();

    }

    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeChatComponent(this.reason);

    }

    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleDisconnect(this);

    }

    public IChatComponent getReason() {
        return this.reason;

    }

    public void setReason(IChatComponent reason) {
        this.reason = reason;

    }

}
