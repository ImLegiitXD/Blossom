package moshi.blossom.user;

/**
 * Stores basic client information including name, build, and version.
 * Used to identify and track client instances.
 */
public class ClientInfo {
    /** The name of the client */
    public String name;

    /** The build identifier */
    public String build;

    /** The version number */
    public String version;

    /**
     * Constructs a new ClientInfo instance with the specified details
     * @param name The display name of the client
     * @param build The build identifier
     * @param version The version number
     */
    public ClientInfo(String name, String build, String version) {
        this.name = name;
        this.build = build;
        this.version = version;
    }
}