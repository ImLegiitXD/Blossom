package moshi.blossom.command.impl;

import moshi.blossom.command.Command;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.Module;
import moshi.blossom.util.ChatUtil;
import org.lwjgl.input.Keyboard;

public class BindCom extends Command {
    public BindCom() {
    super("bind", new String[] { "b", "bin" });
    }

    public String run(String[] args) {
        Module mod;

        if (args.length != 2) {
            return ChatUtil.print("Bad arguments, usage: .bind <moduleName> <key>");
        }

        try {
            mod = ModManager.getMod(args[0]);
        } catch (Exception e) {
            return ChatUtil.print("Module not found");
        }
        mod.setToggleKey(Keyboard.getKeyIndex(args[1].toUpperCase()));
        return ChatUtil.print("Key set to " + args[1]);
    }
}
