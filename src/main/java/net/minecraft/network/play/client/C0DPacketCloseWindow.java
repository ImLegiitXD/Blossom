package net.minecraft.network.play.client;

import java.io.IOException;
import moshi.blossom.util.network.PacketDir;
import moshi.blossom.util.network.Packets;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0DPacketCloseWindow implements Packet<INetHandlerPlayServer> {

    private int windowId;

    public PacketDir pDir() {
        return PacketDir.CLIENT;

    }

    public Packets pType() {
        return Packets.C_CLOSE_WINDOW;

    }

public C0DPacketCloseWindow() {}

    public C0DPacketCloseWindow(int windowId) {
        this.windowId = windowId;

    }

    public void processPacket(INetHandlerPlayServer handler) {
        handler.processCloseWindow(this);

    }

    public void readPacketData(PacketBuffer buf) throws IOException {
        this.windowId = buf.readByte();

    }

    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeByte(this.windowId);

    }

    public int getWindowId() {
        return this.windowId;

    }

}
