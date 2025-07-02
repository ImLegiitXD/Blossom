package moshi.blossom.command.impl;

import moshi.blossom.command.Command;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.Module;
import moshi.blossom.util.ChatUtil;

public class ToggleCom

extends Command {
    public ToggleCom() {
    super("toggle", new String[] { "t", "tog", "togle" });

    }

    public String run(String[] args) {
        Module module;

        if (args.length == 0) {
            return ChatUtil.printWarning("Provide more arguments");

        }

        try {
            module = ModManager.getMod(args[0]);

        } catch (Exception e) {
            return ChatUtil.printWarning("Module not found");

        }

        module.toggle();

        return ChatUtil.print("Toggled " + module.getDisplayName());

    }

}
