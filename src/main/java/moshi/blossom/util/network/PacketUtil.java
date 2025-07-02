package moshi.blossom.util.network;

import moshi.blossom.util.Util;
import moshi.blossom.util.cpackets.CPacket;
import net.minecraft.network.Packet;

public class PacketUtil extends Util {

    /**
     * Sends a single packet normally
     */
    public static void send(Packet<?> packet) {
        mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }

    /**
     * Sends a single packet silently (without event)
     */
    public static void sendSilent(Packet<?> packet) {
        mc.getNetHandler().getNetworkManager().sendSilentPacket(packet);
    }

    /**
     * Sends multiple packets normally
     */
    public static void send(Packet<?>... packets) {
        for (Packet<?> packet : packets) {
            send(packet);
        }
    }

    /**
     * Sends multiple packets silently
     */
    public static void sendSilent(Packet<?>... packets) {
        for (Packet<?> packet : packets) {
            sendSilent(packet);
        }
    }

    /**
     * Sends all packets from a CPacket normally
     */
    public static void send(CPacket cPacket) {
        for (Packet<?> packet : cPacket.packets()) {
            send(packet);
        }
    }

    /**
     * Sends all packets from a CPacket silently
     */
    public static void sendSilent(CPacket cPacket) {
        for (Packet<?> packet : cPacket.packets()) {
            sendSilent(packet);
        }
    }

    /**
     * Sends multiple CPackets normally
     */
    public static void send(CPacket... cPackets) {
        for (CPacket cPacket : cPackets) {
            send(cPacket);
        }
    }

    /**
     * Sends multiple CPackets silently
     */
    public static void sendSilent(CPacket... cPackets) {
        for (CPacket cPacket : cPackets) {
            sendSilent(cPacket);
        }
    }

    /**
     * Sends a packet repeatedly
     * @param rep Number of times to repeat
     */
    public static void send(Packet<?> packet, int rep) {
        for (int i = 0; i < rep; i++) {
            send(packet);
        }
    }

    /**
     * Sends a packet silently repeatedly
     * @param rep Number of times to repeat
     */
    public static void sendSilent(Packet<?> packet, int rep) {
        for (int i = 0; i < rep; i++) {
            sendSilent(packet);
        }
    }
}