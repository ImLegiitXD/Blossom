package net.minecraft.network.play.server;

import java.io.IOException;
import lombok.Getter;
import lombok.Setter;
import moshi.blossom.util.network.PacketDir;
import moshi.blossom.util.network.Packets;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

@Getter
@Setter
public class S46PacketSetCompressionLevel implements Packet<INetHandlerPlayClient> {

    private int field_179761_a;

    public PacketDir pDir() {
        return PacketDir.SERVER;

    }

    public Packets pType() {
        return Packets.S_SET_COMPRESSION_LEVEL;

    }

    public void readPacketData(PacketBuffer buf) throws IOException {
        this.field_179761_a = buf.readVarIntFromBuffer();

    }

    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeVarIntToBuffer(this.field_179761_a);

    }

    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleSetCompressionLevel(this);

    }

    public int func_179760_a() {
        return this.field_179761_a;

    }

}
