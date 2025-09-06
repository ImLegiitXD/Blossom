package moshi.blossom.command.impl;

import moshi.blossom.Blossom;
import moshi.blossom.client.UserHelper;
import moshi.blossom.command.Command;
import moshi.blossom.util.ChatUtil;

public class FriendCom

extends Command {
    public FriendCom() {
    super("friend", new String[] { "f" });

    }

    public String run(String[] args) {
        StringBuilder builder;

        if (args.length == 0) return ChatUtil.print("Usage: .friend <add/remove/clear/list> <name>");
        UserHelper user = Blossom.INSTANCE.getUserHelper();
        switch (args[0]) {
            case "list":
            builder = new StringBuilder("Friends:");

            for (String str : user.friendList) {
                builder.append("\n").append(str);
            }
            return ChatUtil.print(builder.toString());

            case "add":
            if (args.length != 2) return ChatUtil.printInfo("Usage: .friend add <name>");
            if (user.friendList.contains(args[1])) return ChatUtil.printInfo("\"" + args[1] + "\" is already on your friend list");
            user.friendList.add(args[1]);
            return ChatUtil.print("User added to your friend list");

            case "remove":
            if (args.length != 2) return ChatUtil.printInfo("Usage: .friend remove <name>");

            if (!user.friendList.contains(args[1])) return ChatUtil.printInfo("\"" + args[1] + "\" is not on your friend list");
            user.friendList.remove(args[1]);
            return ChatUtil.print("User removed from your friend list");

            case "clear":
            user.friendList.clear();
            return ChatUtil.print("Friend list cleared");
        }
        return ChatUtil.print("Usage: .friend <add/remove/clear/list> <name>");
    }
}
