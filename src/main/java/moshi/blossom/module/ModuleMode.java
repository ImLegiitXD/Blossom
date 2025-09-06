package moshi.blossom.module;

import java.util.List;

import lombok.Getter;
import moshi.blossom.option.Option;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

@Getter
public class ModuleMode {
    private final String name;

    protected Minecraft mc = Minecraft.getMinecraft();

    public ModuleMode(String name) {
        this.name = name;
    }

    protected EntityPlayerSP getPlayer() {
        return (Minecraft.getMinecraft()).thePlayer;
    }

    public List<Option> getOptions() {
        return null;
    }

public void onDisable() {}

public void onEnable() {}
}
