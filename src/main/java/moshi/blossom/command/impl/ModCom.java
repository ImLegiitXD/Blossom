package moshi.blossom.command.impl;

import java.util.List;
import moshi.blossom.Blossom;
import moshi.blossom.command.Command;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.ContOption;
import moshi.blossom.util.ChatUtil;

public class ModCom

extends Command
{
    public ModCom() {
    super("module", new String[] { "m", "mod" });
    }

    public String run(String[] args) {
        Module module;
        List<Option> options;

        StringBuilder builder;

        if (args.length == 0) {
            return ChatUtil.printInfo("Provide more arguments");
        }

        if (args[0].equals("list")) {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("Modules: \n");

            for (int i = 0; i < (Blossom.INSTANCE.getModManager()).moduleList.size(); i++) {
                Module mod = (Blossom.INSTANCE.getModManager()).moduleList.get(i);
                stringBuilder.append(mod.getName()).append(" ");
                if (i % 3 == 0 && i != 0) stringBuilder.append("\n");
            }
            return ChatUtil.print(stringBuilder.toString());
        }

        try {
            module = ModManager.getMod(args[0]);

        } catch (Exception e) {
            return ChatUtil.printWarning("Module not found");
        }

        if (args.length < 2) {
            return ChatUtil.printInfo("Provide more arguments [toggle, get, set]");
        }

        switch (args[1].toLowerCase()) {
            case "toggle":
            module.toggle();
            return ChatUtil.print("Toggled " + module.getName());

            case "get":
            options = module.getOptions();
            builder = new StringBuilder();
            if (args.length == 3) {
                ContOption contOption = null;
                for (Option option : options) {
                    if (option instanceof ContOption && option.getName().equalsIgnoreCase(args[2])) contOption = (ContOption)option;
                }  if (contOption == null)
                return ChatUtil.print("Option not found"); builder.append("Options for ").append(module.getName()).append("::").append(contOption.getName()).append(":");

                for (int i = 0; i < contOption.getOptionList().size(); i++) {
                    Option option = contOption.getOptionList().get(i);
                    builder.append("\n").append(option.getName()).append(" -> ").append(option.getChatStatus());
                }
                return ChatUtil.print(builder.toString());
            }

            if (options.isEmpty()) return ChatUtil.print("Module is empty");

            builder.append("Options for ").append(module.getName()).append(":");
            for (Option option : options) {
                builder.append("\n").append(option.getName()).append(" -> ").append(option.getChatStatus());

            }

            return ChatUtil.print(builder.toString());

            case "set":
            if (args.length < 4) return ChatUtil.print("Provide more arguments");

            options = module.getOptions();

            if (args.length == 5) {
                ContOption contOption = null;

                for (Option option : options) {
                    if (option instanceof ContOption && option.getName().equalsIgnoreCase(args[2])) contOption = (ContOption)option;
                }

                if (contOption == null) return ChatUtil.print("Option not found");

                for (Option option : contOption.getOptionList()) {
                    if (option.getName().equalsIgnoreCase(args[3])) {
                        option.setByChatString(args[4]);
                        return ChatUtil.print("Value of " + option.getDisplayName() + " is now " + args[4]);
                    }
                }
            }

            if (options.isEmpty()) return ChatUtil.print("Module is empty");

            for (Option option : options) {
                if (option.getName().equalsIgnoreCase(args[2])) {
                    option.setByChatString(args[3]);
                    return ChatUtil.print("Value of " + option.getDisplayName() + " is now " + args[3]);
                }
            }
            break;
        }
        return super.run(args);
    }
}
