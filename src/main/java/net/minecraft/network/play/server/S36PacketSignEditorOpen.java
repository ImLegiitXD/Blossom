package net.minecraft.network.play.server;

import java.io.IOException;
import moshi.blossom.util.network.PacketDir;
import moshi.blossom.util.network.Packets;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

public class S36PacketSignEditorOpen implements Packet<INetHandlerPlayClient> {

    private BlockPos signPosition;

    public PacketDir pDir() {
        return PacketDir.SERVER;

    }

    public Packets pType() {
        return Packets.S_SIGN_EDITOR_OPEN;

    }

public S36PacketSignEditorOpen() {}

    public S36PacketSignEditorOpen(BlockPos signPositionIn) {
        this.signPosition = signPositionIn;

    }

    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleSignEditorOpen(this);

    }

    public void readPacketData(PacketBuffer buf) throws IOException {
        this.signPosition = buf.readBlockPos();

    }

    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeBlockPos(this.signPosition);

    }

    public BlockPos getSignPosition() {
        return this.signPosition;

    }

}
