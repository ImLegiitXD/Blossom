package moshi.blossom.util.cpackets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;

/**
 * Interface representing a compound packet that can contain multiple
 * Minecraft network packets. Used for bundling related packets together.
 */
public interface CPacket {

    /**
     * Gets the array of packets that make up this compound packet
     * @return Array of network packets
     */
    Packet<?>[] packets();

    /**
     * Gets the local player instance
     * @return The client player entity
     */
    default EntityPlayerSP getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    /**
     * Gets the number of packets in this compound packet
     * @return The packet count
     */
    default int size() {
        return packets().length;
    }

    /**
     * Checks if this compound packet contains any packets
     * @return True if there are no packets, false otherwise
     */
    default boolean isEmpty() {
        return packets().length == 0;
    }
}