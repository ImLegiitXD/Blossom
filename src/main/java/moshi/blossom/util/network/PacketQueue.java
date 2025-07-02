package moshi.blossom.util.network;

import java.util.ArrayList;
import net.minecraft.network.Packet;

/**
 * A queue for managing and sending Minecraft network packets with
 * support for both normal and silent sending modes.
 */
public class PacketQueue extends ArrayList<Packet<?>> {

    /**
     * Sends and removes a packet silently from the queue
     * @param index The index of the packet to send
     * @return The sent packet
     */
    public Packet<?> sendSilent(int index) {
        Packet<?> packet = remove(index);
        PacketUtil.sendSilent(packet);
        return packet;
    }

    /**
     * Sends and removes a packet from the queue normally
     * @param index The index of the packet to send
     * @return The sent packet
     */
    public Packet<?> send(int index) {
        Packet<?> packet = remove(index);
        PacketUtil.send(packet);
        return packet;
    }

    /**
     * Sends the last packet in the queue normally
     */
    public void sendLast() {
        if (!isEmpty()) {
            send(size() - 1);
        }
    }

    /**
     * Sends the last packet in the queue silently
     */
    public void sendLastSilent() {
        if (!isEmpty()) {
            sendSilent(size() - 1);
        }
    }

    /**
     * Adds a packet to the queue
     * @param packet The packet to add
     */
    public void addPacket(Packet<?> packet) {
        add(packet);
    }

    /**
     * Sends all packets in the queue normally and clears the queue
     */
    public void sendAll() {
        forEach(PacketUtil::send);
        clear();
    }

    /**
     * Sends all packets in the queue silently and clears the queue
     */
    public void sendAllSilent() {
        forEach(PacketUtil::sendSilent);
        clear();
    }
}