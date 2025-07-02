package net.minecraft.network.play.server;

import java.io.IOException;
import moshi.blossom.util.network.PacketDir;
import moshi.blossom.util.network.Packets;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S19PacketEntityStatus implements Packet<INetHandlerPlayClient> {

    private int entityId;

    public PacketDir pDir() {
        return PacketDir.SERVER;

    }

    private byte logicOpcode;

    public Packets pType() {
        return Packets.S_ENTITY_STATUS;

    }

public S19PacketEntityStatus() {}

    public S19PacketEntityStatus(Entity entityIn, byte opCodeIn) {
        this.entityId = entityIn.getEntityId();

        this.logicOpcode = opCodeIn;

    }

    public void readPacketData(PacketBuffer buf) throws IOException {
        this.entityId = buf.readInt();

        this.logicOpcode = buf.readByte();

    }

    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeInt(this.entityId);

        buf.writeByte(this.logicOpcode);

    }

    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleEntityStatus(this);

    }

    public Entity getEntity(World worldIn) {
        return worldIn.getEntityByID(this.entityId);

    }

    public byte getOpCode() {
        return this.logicOpcode;

    }

}
