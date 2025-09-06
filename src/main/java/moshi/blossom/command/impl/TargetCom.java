package moshi.blossom.command.impl;

import moshi.blossom.Blossom;
import moshi.blossom.client.UserHelper;
import moshi.blossom.command.Command;
import moshi.blossom.util.ChatUtil;

public class TargetCom

extends Command {
    public TargetCom() {
        super("target", new String[0]);
    }

    public String run(String[] args) {
        StringBuilder builder;
        if (args.length == 0) return ChatUtil.print("Usage: .target <add/remove/clear/list> <name>");

        UserHelper user = Blossom.INSTANCE.getUserHelper();

        switch (args[0]) {
            case "list":
            builder = new StringBuilder("Targets:");
            for (String str : user.targetList) {
                builder.append("\n").append(str);
            }
            return ChatUtil.print(builder.toString());

            case "add":
            if (args.length != 2) return ChatUtil.printInfo("Usage: .target add <name>");
            if (user.targetList.contains(args[1])) return ChatUtil.printInfo("\"" + args[1] + "\" is already on your target list");
            user.targetList.add(args[1]);
            return ChatUtil.print("User added to your target list");

            case "remove":
            if (args.length != 2) return ChatUtil.printInfo("Usage: .target remove <name>");
            if (!user.targetList.contains(args[1])) return ChatUtil.printInfo("\"" + args[1] + "\" is not on your target list");
            user.targetList.remove(args[1]);
            return ChatUtil.print("User removed from your target list");

            case "clear":
            user.targetList.clear();
            return ChatUtil.print("Target list cleared");
        }
        return ChatUtil.print("Usage: .target <add/remove/clear/list> <name>");
    }
}
