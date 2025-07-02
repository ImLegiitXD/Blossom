package moshi.blossom.command;

public class Command

{
    public String name;

    public String[] aliases;

    public Command(String name, String[] aliases) {
        this.name = name;

        this.aliases = aliases;

    }

    public String run(String[] args) {
        return "Not implemented, contact the dev";

    }

}
