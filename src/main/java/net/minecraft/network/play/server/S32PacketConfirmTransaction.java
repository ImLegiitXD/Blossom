package net.minecraft.network.play.server;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import moshi.blossom.util.network.PacketDir;
import moshi.blossom.util.network.Packets;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

@Setter
@Getter
public class S32PacketConfirmTransaction implements Packet<INetHandlerPlayClient>
{
    private int windowId;
    private short actionNumber;
    public boolean field_148893_c;

    public PacketDir pDir() {
        return PacketDir.SERVER;
    }

    public Packets pType() {
        return Packets.S_CONFIRM_TRANSACTION;
    }

    public S32PacketConfirmTransaction() {
    }

    public S32PacketConfirmTransaction(int windowIdIn, short actionNumberIn, boolean p_i45182_3_) {
        this.windowId = windowIdIn;
        this.actionNumber = actionNumberIn;
        this.field_148893_c = p_i45182_3_;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleConfirmTransaction(this);
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException {
        if (ViaLoadingBase.getInstance().getTargetVersion().newerThanOrEqualTo(ProtocolVersion.v1_17)) {
            this.windowId = buf.readInt();
        } else {
            this.windowId = buf.readUnsignedByte();
            this.actionNumber = buf.readShort();
            this.field_148893_c = buf.readBoolean();
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException {
        if (ViaLoadingBase.getInstance().getTargetVersion().newerThanOrEqualTo(ProtocolVersion.v1_17)) {
            buf.writeInt(this.windowId);
        } else {
            buf.writeByte(this.windowId);
            buf.writeShort(this.actionNumber);
            buf.writeByte(this.field_148893_c ? 1 : 0);
        }
    }

    public int getWindowId()
    {
        return this.windowId;
    }

    public short getActionNumber()
    {
        return this.actionNumber;
    }

    public boolean func_148888_e()
    {
        return this.field_148893_c;
    }
}