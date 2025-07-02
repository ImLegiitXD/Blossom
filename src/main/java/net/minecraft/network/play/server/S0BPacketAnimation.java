package net.minecraft.network.play.server;

import java.io.IOException;
import moshi.blossom.util.network.PacketDir;
import moshi.blossom.util.network.Packets;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S0BPacketAnimation implements Packet<INetHandlerPlayClient> {

    private int entityId;

    public PacketDir pDir() {
        return PacketDir.SERVER;

    }

    private int type;

    public Packets pType() {
        return Packets.S_ANIMATION;

    }

public S0BPacketAnimation() {}

    public S0BPacketAnimation(Entity ent, int animationType) {
        this.entityId = ent.getEntityId();

        this.type = animationType;

    }

    public void readPacketData(PacketBuffer buf) throws IOException {
        this.entityId = buf.readVarIntFromBuffer();

        this.type = buf.readUnsignedByte();

    }

    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeVarIntToBuffer(this.entityId);

        buf.writeByte(this.type);

    }

    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleAnimation(this);

    }

    public int getEntityID() {
        return this.entityId;

    }

    public int getAnimationType() {
        return this.type;

    }

}
