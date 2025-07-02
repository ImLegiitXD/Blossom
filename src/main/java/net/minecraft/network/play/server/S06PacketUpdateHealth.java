package net.minecraft.network.play.server;

import java.io.IOException;
import moshi.blossom.util.network.PacketDir;
import moshi.blossom.util.network.Packets;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S06PacketUpdateHealth implements Packet<INetHandlerPlayClient> {

    private float health;

    public PacketDir pDir() {
        return PacketDir.SERVER;

    }

    private int foodLevel; private float saturationLevel;

    public Packets pType() {
        return Packets.S_UPDATE_HEALTH;

    }

public S06PacketUpdateHealth() {}

    public S06PacketUpdateHealth(float healthIn, int foodLevelIn, float saturationIn) {
        this.health = healthIn;

        this.foodLevel = foodLevelIn;

        this.saturationLevel = saturationIn;

    }

    public void readPacketData(PacketBuffer buf) throws IOException {
        this.health = buf.readFloat();

        this.foodLevel = buf.readVarIntFromBuffer();

        this.saturationLevel = buf.readFloat();

    }

    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeFloat(this.health);

        buf.writeVarIntToBuffer(this.foodLevel);

        buf.writeFloat(this.saturationLevel);

    }

    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleUpdateHealth(this);

    }

    public float getHealth() {
        return this.health;

    }

    public int getFoodLevel() {
        return this.foodLevel;

    }

    public float getSaturationLevel() {
        return this.saturationLevel;

    }

}
