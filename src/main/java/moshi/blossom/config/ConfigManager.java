package moshi.blossom.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import moshi.blossom.Blossom;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.ContOption;
import moshi.blossom.util.ChatUtil;
import moshi.blossom.util.Pair;

public class ConfigManager {
    public static final String fileExt = ".cfg";
    public static final String fileSep = "::";
    public File confDir;

    public void init() {
        this.confDir = new File(Blossom.INSTANCE.mainDir, "/configs");
        if (!this.confDir.exists() && this.confDir.mkdir()) {
            System.out.println("Created config dir");
        }
    }

    public void stop() {
        save("last");
    }

    public boolean delf(String name) {
        for (File file : Objects.requireNonNull(this.confDir.listFiles())) {
            if (file.getName().equals(name + ".cfg")) {
                return file.delete();
            }
        }
        return false;
    }

    public Pair<Boolean, String> save(String name) {
        FileWriter writer;
        try {
            writer = newWriter(name);
        } catch (IOException e) {
            return new Pair<>(false, "Something went wrong creating / editing the file");
        }

        try {
            writer.write("BUILD:" + Blossom.INSTANCE.getClientInfo().build + "\n");
            for (Module mod : Blossom.INSTANCE.getModManager().moduleList) {
                writer.write(mod.getName() + "::" + mod.isToggled() + "\n");
                for (Option option : mod.getOptions()) {
                    if (option instanceof ContOption) {
                        for (Option option1 : ((ContOption)option).getOptionList()) {
                            writer.write(mod.getName() + "::" + option.getName() + "::" + option1.getName() + "::" + option1.pureStrValue() + "\n");
                        }
                        continue;
                    }
                    writer.write(mod.getName() + "::" + option.getName() + "::" + option.pureStrValue() + "\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            return new Pair<>(false, "Something went wrong writing to the file");
        }

        return new Pair<>(true, "Config \"" + name + "\" saved");
    }

    public Pair<Boolean, String> load(String name) {
        Scanner scanner;
        try {
            scanner = newReader(name);
        } catch (IOException e) {
            return new Pair<>(false, "Something went wrong reading the file");
        }

        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (i == 0 && !line.equals("BUILD:" + Blossom.INSTANCE.getClientInfo().build)) {
                ChatUtil.printWarning("Config is outdated");
            }
            String[] parts = line.split("::");
            if (parts.length == 2) {
                try {
                    ModManager.getMod(parts[0]).setToggled(Boolean.parseBoolean(parts[1]));
                } catch (IllegalArgumentException e) {
                    return new Pair<>(false, "Illegal argument on line " + i);
                } catch (Exception e) {
                    return new Pair<>(false, "Generic exception on line " + i);
                }
            } else if (parts.length == 3) {
                Module module;
                try {
                    module = ModManager.getMod(parts[0]);
                } catch (IllegalArgumentException e) {
                    return new Pair<>(false, "Illegal argument on line " + i);
                }
                for (Option setting : module.getOptions()) {
                    if (setting.getName().equals(parts[1])) {
                        setting.setByStr(parts[2]);
                    }
                }
            } else if (parts.length == 4) {
                Module module;
                try {
                    module = ModManager.getMod(parts[0]);
                } catch (IllegalArgumentException e) {
                    return new Pair<>(false, "Illegal argument on line " + i);
                }
                for (Option option : module.getOptions()) {
                    if (option.getName().equals(parts[1]) && option instanceof ContOption) {
                        for (Option option1 : ((ContOption)option).getOptionList()) {
                            if (option1.getName().equals(parts[2])) {
                                option1.setByStr(parts[3]);
                            }
                        }
                    }
                }
            }
            i++;
        }
        return new Pair<>(true, "Config " + name + " loaded");
    }

    public String list() {
        StringBuilder builder = new StringBuilder("Configs:");
        for (File file : Objects.requireNonNull(this.confDir.listFiles())) {
            builder.append("\n").append(file.getName());
        }
        return (builder.toString().split("\n").length == 1) ? "You don't have configs" : builder.toString();
    }

    private FileWriter newWriter(String name) throws IOException {
        File file = new File(this.confDir, name + ".cfg");
        if (!file.exists() && file.createNewFile()) {
            System.out.println("Created config file " + name);
        }
        return new FileWriter(file);
    }

    private Scanner newReader(String name) throws IOException {
        File file = new File(this.confDir, name + ".cfg");
        return new Scanner(file);
    }
}