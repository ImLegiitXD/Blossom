package moshi.blossom.command.impl;

import moshi.blossom.Blossom;
import moshi.blossom.command.Command;
import moshi.blossom.util.ChatUtil;

public class ConfigCom

extends Command {
    public ConfigCom() {
    super("config", new String[] { "cfg", "c" });

    }

    public String run(String[] args) {
        if (args.length == 0)
        return ChatUtil.printInfo("usage: .config <list/save/load/delete> <name>");

        if (args.length == 1) {
            if (!args[0].equals("list")) {
                return ChatUtil.printInfo("usage: .config <list/save/load/delete> <name>");

            }

            return ChatUtil.printInfo(Blossom.INSTANCE.getConfigManager().list());

        }

        if (args.length == 2) {
            switch (args[0]) {
                case "save":
                return ChatUtil.printInfo((String)Blossom.INSTANCE.getConfigManager().save(args[1]).getValue());

                case "load":
                return ChatUtil.printInfo((String)Blossom.INSTANCE.getConfigManager().load(args[1]).getValue());

                case "delete":
                return ChatUtil.printInfo(Blossom.INSTANCE.getConfigManager().delf(args[1]) ? ("Deleted " + args[1]) : "File not found");

            }

        }

        return ChatUtil.printInfo("usage: .config <list/save/load/delete> <name>");

    }

}
