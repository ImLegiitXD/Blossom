package moshi.blossom.util.player;

import net.minecraft.client.Minecraft;

public class TimerUtil
{
    public static void setTimer(float ammount) {
        /* 8 */     (Minecraft.getMinecraft()).timer.timerSpeed = ammount;
        /*   */   }
}


