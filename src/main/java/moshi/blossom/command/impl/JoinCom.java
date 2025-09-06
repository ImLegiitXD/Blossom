package moshi.blossom.command.impl;

import java.util.HashMap;
import moshi.blossom.client.JoinHelper;
import moshi.blossom.command.Command;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class JoinCom

extends Command {
    final HashMap<String, Item> hashMap;

    public JoinCom() {
    super("join", new String[] { "j" });
        this.hashMap = new HashMap<>();
    }

    public String run(String[] args) {
        if (args.length < 2) return "?";

        this.hashMap.put("sw", Items.bow);
        this.hashMap.put("skywars", Items.bow);
        this.hashMap.put("teamsw", Items.arrow);
        this.hashMap.put("teamskywars", Items.arrow);
        this.hashMap.put("tsw", Items.arrow);
        this.hashMap.put("bw", Items.bed);
        this.hashMap.put("bedwars", Items.bed);

        String gameName = args[0];

        int lobby = Integer.parseInt(args[1]);

        if (!this.hashMap.containsKey(gameName)) return "?";
        JoinHelper.startJoining(this.hashMap.get(gameName), lobby);
        return "trying to join";
    }
}
