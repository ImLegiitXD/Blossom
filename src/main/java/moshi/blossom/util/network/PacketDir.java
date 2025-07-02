package moshi.blossom.util.network;

/**
 * Enum representing the direction of network packets
 * CLIENT - Packets sent from client to server (C→S)
 * SERVER - Packets sent from server to client (S→C)
 */
public enum PacketDir {
    CLIENT("C→S", "c"),
    SERVER("S→C", "s");

    private final String displayName;
    private final String shortName;

    /**
     * @param displayName Human-readable direction representation
     * @param shortName Short code for the direction
     */
    PacketDir(String displayName, String shortName) {
        this.displayName = displayName;
        this.shortName = shortName;
    }

    /**
     * @return The human-readable direction representation
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return The short code for the direction
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * @return True if this is a client-to-server direction
     */
    public boolean isClientbound() {
        return this == CLIENT;
    }

    /**
     * @return True if this is a server-to-client direction
     */
    public boolean isServerbound() {
        return this == SERVER;
    }
}