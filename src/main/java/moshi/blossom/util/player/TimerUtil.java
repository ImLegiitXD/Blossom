package moshi.blossom.util.player;

import static moshi.blossom.util.Util.mc;

public class TimerUtil
{
    public static void setTimer(float ammount) {
        mc.timer.timerSpeed = ammount;
    }
}


