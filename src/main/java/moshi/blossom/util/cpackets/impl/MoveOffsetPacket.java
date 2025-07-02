package moshi.blossom.util.cpackets.impl;

import moshi.blossom.util.cpackets.CPacket;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * CPacket implementation that handles player movement with positional offsets.
 * Creates a position packet relative to the player's current coordinates.
 */
public class MoveOffsetPacket implements CPacket {
    public double x;
    public double y;
    public double z;
    public boolean ground;

    /**
     * Constructs a new movement packet with positional offsets
     * @param x X-axis offset from current position
     * @param y Y-axis offset from current position
     * @param z Z-axis offset from current position
     * @param ground Whether the player is on ground
     */
    public MoveOffsetPacket(double x, double y, double z, boolean ground) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.ground = ground;
    }

    /**
     * Generates the network packets for this movement
     * @return Array containing the position packet with applied offsets
     */
    public Packet<?>[] packets() {
        return new Packet[] {
                new C03PacketPlayer.C04PacketPlayerPosition(
                        getPlayer().posX + this.x,
                        getPlayer().posY + this.y,
                        getPlayer().posZ + this.z,
                        this.ground
                )
        };
    }
}