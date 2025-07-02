package moshi.blossom.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;

public class Util
{
    public static Minecraft mc = Minecraft.getMinecraft();

    public static EntityPlayerSP getPlayer() {
        return mc.thePlayer;
    }

    public static World getWorld() {
        return (World)mc.theWorld;
    }
}