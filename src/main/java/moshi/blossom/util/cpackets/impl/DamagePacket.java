package moshi.blossom.util.cpackets.impl;

import moshi.blossom.util.cpackets.CPacket;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * CPacket implementation that simulates player damage by sending vertical movement packets.
 * Used to trigger damage mechanics by moving the player vertically.
 */
public class DamagePacket implements CPacket {
    public double y;

    /**
     * Constructs a damage packet with specified vertical movement
     * @param y Vertical offset for the damage simulation
     */
    public DamagePacket(double y) {
        this.y = y;
    }

    /**
     * Generates the sequence of movement packets needed for damage simulation
     * @return Array containing three position packets:
     *         1. Upward movement
     *         2. Return to original position
     *         3. Ground state confirmation
     */
    public Packet<?>[] packets() {
        return new Packet[] {
                new C03PacketPlayer.C04PacketPlayerPosition(
                        getPlayer().posX,
                        getPlayer().posY + this.y,
                        getPlayer().posZ,
                        false
                ),
                new C03PacketPlayer.C04PacketPlayerPosition(
                        getPlayer().posX,
                        getPlayer().posY,
                        getPlayer().posZ,
                        false
                ),
                new C03PacketPlayer.C04PacketPlayerPosition(
                        getPlayer().posX,
                        getPlayer().posY,
                        getPlayer().posZ,
                        true
                )
        };
    }
}