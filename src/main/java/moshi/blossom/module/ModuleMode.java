package moshi.blossom.module;

import java.util.List;
import moshi.blossom.option.Option;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class ModuleMode

{
    private String name;

    protected Minecraft mc = Minecraft.getMinecraft();

    public ModuleMode(String name) {
        this.name = name;

    }

    protected EntityPlayerSP getPlayer() {
        return (Minecraft.getMinecraft()).thePlayer;

    }

    public String getName() {
        return this.name;

    }

    public List<Option> getOptions() {
        return null;

    }

public void onDisable() {}

public void onEnable() {}

}
